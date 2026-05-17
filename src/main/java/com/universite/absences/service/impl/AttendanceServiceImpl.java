package com.universite.absences.service.impl;

import com.universite.absences.dto.external.FaceDetectionDto;
import com.universite.absences.dto.external.FaceRecognizeBatchResponse;
import com.universite.absences.dto.external.FaceRecognizeResponse;
import com.universite.absences.dto.request.PresenceIARequest;
import com.universite.absences.dto.response.*;
import com.universite.absences.entity.Absence;
import com.universite.absences.entity.Etudiant;
import com.universite.absences.entity.Filiere;
import com.universite.absences.entity.PresenceLog;
import com.universite.absences.entity.Seance;
import com.universite.absences.entity.enums.StatutAbsence;
import com.universite.absences.exception.EntityNotFoundException;
import com.universite.absences.exception.SeanceInactiveException;
import com.universite.absences.repository.AbsenceRepository;
import com.universite.absences.repository.EtudiantRepository;
import com.universite.absences.repository.PresenceLogRepository;
import com.universite.absences.repository.SeanceRepository;
import com.universite.absences.service.interfaces.AiIntegrationService;
import com.universite.absences.service.interfaces.AttendanceService;
import com.universite.absences.service.interfaces.PresenceService;
import com.universite.absences.util.StudentNameMatcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final AiIntegrationService aiIntegrationService;
    private final PresenceService presenceService;
    private final StudentNameMatcher studentNameMatcher;
    private final SeanceRepository seanceRepository;
    private final EtudiantRepository etudiantRepository;
    private final AbsenceRepository absenceRepository;
    private final PresenceLogRepository presenceLogRepository;

    @Override
    @Transactional
    public AttendanceScanResponse markAttendanceFromImage(Long seanceId, MultipartFile image) {
        FaceRecognizeResponse recognition = aiIntegrationService.recognizeFace(image);

        if (!recognition.isRecognized()) {
            log.info("Visage non reconnu pour la séance {} (similarité={})", seanceId, recognition.getSimilarity());
            return AttendanceScanResponse.builder()
                    .recognition(recognition)
                    .attendanceMarked(false)
                    .message("Aucun étudiant reconnu avec un score suffisant")
                    .build();
        }

        SeanceContext ctx = loadSeanceContext(seanceId);
        Etudiant etudiant = resolveStudentInFiliere(recognition.getStudentName(), ctx.filiereId());

        PresenceIAResponse presence = markPresent(etudiant, seanceId, recognition.getSimilarity());

        log.info("Présence marquée via IA : étudiant {} séance {}", etudiant.getId(), seanceId);

        return AttendanceScanResponse.builder()
                .recognition(recognition)
                .presence(presence)
                .attendanceMarked(true)
                .message("Présence enregistrée pour " + recognition.getStudentName())
                .build();
    }

    @Override
    @Transactional
    public ClassroomScanResponse markAttendanceFromClassroomImage(Long seanceId, MultipartFile image) {
        SeanceContext ctx = loadSeanceContext(seanceId);
        assertSeanceActive(ctx.seance());

        FaceRecognizeBatchResponse batch = aiIntegrationService.recognizeFacesInClassroom(image);
        List<Etudiant> classStudents = etudiantRepository.findByFiliereIdOrderByNomAscPrenomAsc(ctx.filiereId());
        Set<Long> classStudentIds = classStudents.stream().map(Etudiant::getId).collect(Collectors.toSet());

        List<ClassroomScanResultItem> results = new ArrayList<>();
        Set<Long> markedInThisScan = new HashSet<>();
        int recognizedCount = 0;
        int markedPresentCount = 0;
        int alreadyPresentCount = 0;

        for (FaceDetectionDto detection : batch.getDetections()) {
            ClassroomScanResultItem item = processDetection(
                    detection, seanceId, ctx.filiereId(), classStudentIds, markedInThisScan);
            results.add(item);

            if (detection.isRecognized()) {
                recognizedCount++;
            }
            if (item.isAttendanceMarked()) {
                markedPresentCount++;
            } else if (item.getMessage() != null && item.getMessage().contains("déjà enregistrée")) {
                alreadyPresentCount++;
            }
        }

        String message = String.format(
                "%d visage(s) détecté(s), %d reconnu(s), %d présence(s) marquée(s) sur %d étudiant(s) de la filière",
                batch.getFacesDetected(), recognizedCount, markedPresentCount, classStudents.size());

        return ClassroomScanResponse.builder()
                .seanceId(seanceId)
                .filiereId(ctx.filiereId())
                .filiereNom(ctx.filiereNom())
                .totalStudentsInClass(classStudents.size())
                .facesDetected(batch.getFacesDetected())
                .recognizedCount(recognizedCount)
                .markedPresentCount(markedPresentCount)
                .alreadyPresentCount(alreadyPresentCount)
                .results(results)
                .message(message)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public SeanceAttendanceStatusResponse getSeanceAttendanceStatus(Long seanceId) {
        SeanceContext ctx = loadSeanceContext(seanceId);
        return buildAttendanceStatus(ctx);
    }

    @Override
    @Transactional(readOnly = true)
    public SeanceAttendanceStatusResponse getFiliereAttendanceStatusForSeance(Long filiereId, Long seanceId) {
        SeanceContext ctx = loadSeanceContext(seanceId);
        if (!ctx.filiereId().equals(filiereId)) {
            throw new IllegalArgumentException(
                    "La séance n'appartient pas à la filière demandée (filière séance=" + ctx.filiereId() + ")");
        }
        return buildAttendanceStatus(ctx);
    }

    private ClassroomScanResultItem processDetection(
            FaceDetectionDto detection,
            Long seanceId,
            Long filiereId,
            Set<Long> classStudentIds,
            Set<Long> markedInThisScan) {

        if (!detection.isRecognized()) {
            return ClassroomScanResultItem.builder()
                    .studentNameFromAi(detection.getStudentName())
                    .similarity(detection.getSimilarity())
                    .recognized(false)
                    .attendanceMarked(false)
                    .message("Visage non reconnu (score insuffisant)")
                    .build();
        }

        Optional<Etudiant> etudiantOpt = studentNameMatcher.resolve(detection.getStudentName());
        if (etudiantOpt.isEmpty()) {
            return ClassroomScanResultItem.builder()
                    .studentNameFromAi(detection.getStudentName())
                    .similarity(detection.getSimilarity())
                    .recognized(true)
                    .attendanceMarked(false)
                    .message("Étudiant inconnu en base: " + detection.getStudentName())
                    .build();
        }

        Etudiant etudiant = etudiantOpt.get();
        if (!classStudentIds.contains(etudiant.getId())) {
            return ClassroomScanResultItem.builder()
                    .etudiantId(etudiant.getId())
                    .nom(etudiant.getNom())
                    .prenom(etudiant.getPrenom())
                    .studentNameFromAi(detection.getStudentName())
                    .similarity(detection.getSimilarity())
                    .recognized(true)
                    .attendanceMarked(false)
                    .message("Étudiant hors de la filière de cette séance")
                    .build();
        }

        if (markedInThisScan.contains(etudiant.getId())) {
            return ClassroomScanResultItem.builder()
                    .etudiantId(etudiant.getId())
                    .nom(etudiant.getNom())
                    .prenom(etudiant.getPrenom())
                    .studentNameFromAi(detection.getStudentName())
                    .similarity(detection.getSimilarity())
                    .recognized(true)
                    .attendanceMarked(false)
                    .statut(StatutAbsence.PRESENT)
                    .message("Doublon ignoré (même étudiant détecté plusieurs fois)")
                    .build();
        }

        Optional<Absence> existing = absenceRepository.findByEtudiantIdAndSeanceId(etudiant.getId(), seanceId);
        if (existing.isPresent() && existing.get().getStatut() == StatutAbsence.PRESENT) {
            markedInThisScan.add(etudiant.getId());
            return ClassroomScanResultItem.builder()
                    .etudiantId(etudiant.getId())
                    .nom(etudiant.getNom())
                    .prenom(etudiant.getPrenom())
                    .studentNameFromAi(detection.getStudentName())
                    .similarity(detection.getSimilarity())
                    .recognized(true)
                    .attendanceMarked(false)
                    .statut(StatutAbsence.PRESENT)
                    .message("Présence déjà enregistrée")
                    .build();
        }

        markPresent(etudiant, seanceId, detection.getSimilarity());
        markedInThisScan.add(etudiant.getId());

        return ClassroomScanResultItem.builder()
                .etudiantId(etudiant.getId())
                .nom(etudiant.getNom())
                .prenom(etudiant.getPrenom())
                .studentNameFromAi(detection.getStudentName())
                .similarity(detection.getSimilarity())
                .recognized(true)
                .attendanceMarked(true)
                .statut(StatutAbsence.PRESENT)
                .message("Présence enregistrée")
                .build();
    }

    private PresenceIAResponse markPresent(Etudiant etudiant, Long seanceId, double confidence) {
        PresenceIARequest request = PresenceIARequest.builder()
                .etudiantId(etudiant.getId())
                .seanceId(seanceId)
                .timestamp(LocalDateTime.now())
                .confidenceScore(confidence)
                .build();
        return presenceService.enregistrerPresenceIA(request);
    }

    private Etudiant resolveStudentInFiliere(String studentName, Long filiereId) {
        Etudiant etudiant = studentNameMatcher.resolve(studentName)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Étudiant introuvable pour le nom IA: " + studentName));

        if (!etudiantRepository.existsByIdAndFiliereId(etudiant.getId(), filiereId)) {
            throw new IllegalArgumentException("L'étudiant n'appartient pas à la filière de cette séance");
        }
        return etudiant;
    }

    private SeanceAttendanceStatusResponse buildAttendanceStatus(SeanceContext ctx) {
        Long seanceId = ctx.seance().getId();
        List<Etudiant> students = etudiantRepository.findByFiliereIdOrderByNomAscPrenomAsc(ctx.filiereId());
        Map<Long, Absence> absencesByStudent = absenceRepository.findBySeanceId(seanceId).stream()
                .collect(Collectors.toMap(a -> a.getEtudiant().getId(), a -> a, (a, b) -> a));

        Map<Long, PresenceLog> latestLogByStudent = new HashMap<>();
        for (PresenceLog log : presenceLogRepository.findBySeanceIdOrderByTimestampDesc(seanceId)) {
            latestLogByStudent.putIfAbsent(log.getEtudiant().getId(), log);
        }

        int present = 0;
        int absent = 0;
        int justifie = 0;
        List<StudentAttendanceStatusItem> items = new ArrayList<>();

        for (Etudiant etudiant : students) {
            Absence absence = absencesByStudent.get(etudiant.getId());
            StatutAbsence statut = absence != null ? absence.getStatut() : StatutAbsence.ABSENT;
            PresenceLog lastLog = latestLogByStudent.get(etudiant.getId());

            switch (statut) {
                case PRESENT -> present++;
                case JUSTIFIE -> justifie++;
                default -> absent++;
            }

            items.add(StudentAttendanceStatusItem.builder()
                    .etudiantId(etudiant.getId())
                    .nom(etudiant.getNom())
                    .prenom(etudiant.getPrenom())
                    .cne(etudiant.getCne())
                    .statut(statut)
                    .scannedByAi(lastLog != null)
                    .lastScanAt(lastLog != null ? lastLog.getTimestamp() : null)
                    .lastConfidenceScore(lastLog != null ? lastLog.getConfidenceScore() : null)
                    .build());
        }

        return SeanceAttendanceStatusResponse.builder()
                .seanceId(seanceId)
                .moduleNom(ctx.moduleNom())
                .filiereId(ctx.filiereId())
                .filiereNom(ctx.filiereNom())
                .totalStudents(students.size())
                .presentCount(present)
                .absentCount(absent)
                .justifieCount(justifie)
                .students(items)
                .build();
    }

    private SeanceContext loadSeanceContext(Long seanceId) {
        Seance seance = seanceRepository.findWithModuleAndFiliereById(seanceId)
                .orElseThrow(() -> new EntityNotFoundException("Séance introuvable"));

        Filiere filiere = seance.getModule().getFiliere();
        if (filiere == null) {
            throw new EntityNotFoundException("Filière introuvable pour la séance " + seanceId);
        }

        return new SeanceContext(
                seance,
                filiere.getId(),
                filiere.getNom(),
                seance.getModule().getNom());
    }

    private void assertSeanceActive(Seance seance) {
        LocalDateTime now = LocalDateTime.now();
        if (!Boolean.TRUE.equals(seance.getActive())
                || now.isBefore(seance.getDateDebut())
                || now.isAfter(seance.getDateFin())) {
            throw new SeanceInactiveException("La séance n'est pas active à ce timestamp.");
        }
    }

    private record SeanceContext(Seance seance, Long filiereId, String filiereNom, String moduleNom) {}
}
