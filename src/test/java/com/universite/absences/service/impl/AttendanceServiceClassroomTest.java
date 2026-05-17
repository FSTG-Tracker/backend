package com.universite.absences.service.impl;

import com.universite.absences.dto.external.FaceDetectionDto;
import com.universite.absences.dto.external.FaceRecognizeBatchResponse;
import com.universite.absences.dto.response.ClassroomScanResponse;
import com.universite.absences.dto.response.PresenceIAResponse;
import com.universite.absences.entity.*;
import com.universite.absences.entity.enums.StatutAbsence;
import com.universite.absences.repository.AbsenceRepository;
import com.universite.absences.repository.EtudiantRepository;
import com.universite.absences.repository.PresenceLogRepository;
import com.universite.absences.repository.SeanceRepository;
import com.universite.absences.service.interfaces.AiIntegrationService;
import com.universite.absences.service.interfaces.PresenceService;
import com.universite.absences.util.StudentNameMatcher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AttendanceServiceClassroomTest {

    @Mock private AiIntegrationService aiIntegrationService;
    @Mock private PresenceService presenceService;
    @Mock private StudentNameMatcher studentNameMatcher;
    @Mock private SeanceRepository seanceRepository;
    @Mock private EtudiantRepository etudiantRepository;
    @Mock private AbsenceRepository absenceRepository;
    @Mock private PresenceLogRepository presenceLogRepository;

    @InjectMocks
    private AttendanceServiceImpl attendanceService;

    @Test
    void markAttendanceFromClassroomImage_marksMultipleStudents() {
        Filiere filiere = new Filiere();
        filiere.setId(1L);
        filiere.setNom("SMI");
        com.universite.absences.entity.Module module = new com.universite.absences.entity.Module();
        module.setNom("Java");
        module.setFiliere(filiere);
        Seance seance = new Seance();
        seance.setId(3L);
        seance.setModule(module);
        seance.setActive(true);
        seance.setDateDebut(LocalDateTime.now().minusHours(1));
        seance.setDateFin(LocalDateTime.now().plusHours(2));

        Etudiant e1 = new Etudiant();
        e1.setId(10L);
        e1.setNom("Elomri");
        e1.setPrenom("Mohammed");
        Etudiant e2 = new Etudiant();
        e2.setId(11L);
        e2.setNom("Berrada");
        e2.setPrenom("Yassine");

        when(seanceRepository.findWithModuleAndFiliereById(3L)).thenReturn(Optional.of(seance));
        when(etudiantRepository.findByFiliereIdOrderByNomAscPrenomAsc(1L)).thenReturn(List.of(e1, e2));
        when(aiIntegrationService.recognizeFacesInClassroom(any())).thenReturn(
                FaceRecognizeBatchResponse.builder()
                        .facesDetected(2)
                        .detections(List.of(
                                FaceDetectionDto.builder().studentName("Mohammed Elomri").similarity(0.9).recognized(true).build(),
                                FaceDetectionDto.builder().studentName("Yassine Berrada").similarity(0.85).recognized(true).build()
                        ))
                        .build());
        when(studentNameMatcher.resolve("Mohammed Elomri")).thenReturn(Optional.of(e1));
        when(studentNameMatcher.resolve("Yassine Berrada")).thenReturn(Optional.of(e2));
        when(absenceRepository.findByEtudiantIdAndSeanceId(anyLong(), eq(3L))).thenReturn(Optional.empty());
        when(presenceService.enregistrerPresenceIA(any())).thenReturn(
                PresenceIAResponse.builder().logId(1L).statut(StatutAbsence.PRESENT).message("ok").build());

        ClassroomScanResponse response = attendanceService.markAttendanceFromClassroomImage(
                3L, new MockMultipartFile("image", "class.jpg", "image/jpeg", new byte[]{1}));

        assertEquals(2, response.getFacesDetected());
        assertEquals(2, response.getMarkedPresentCount());
        verify(presenceService, times(2)).enregistrerPresenceIA(any());
    }
}
