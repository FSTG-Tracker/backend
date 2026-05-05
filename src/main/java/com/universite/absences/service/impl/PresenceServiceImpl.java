package com.universite.absences.service.impl;

import com.universite.absences.dto.request.PresenceIARequest;
import com.universite.absences.dto.response.PresenceIAResponse;
import com.universite.absences.entity.Absence;
import com.universite.absences.entity.Etudiant;
import com.universite.absences.entity.PresenceLog;
import com.universite.absences.entity.Seance;
import com.universite.absences.entity.enums.SourcePresence;
import com.universite.absences.entity.enums.StatutAbsence;
import com.universite.absences.exception.EntityNotFoundException;
import com.universite.absences.exception.SeanceInactiveException;
import com.universite.absences.repository.AbsenceRepository;
import com.universite.absences.repository.EtudiantRepository;
import com.universite.absences.repository.PresenceLogRepository;
import com.universite.absences.repository.SeanceRepository;
import com.universite.absences.service.interfaces.PresenceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PresenceServiceImpl implements PresenceService {

    private final SeanceRepository seanceRepository;
    private final EtudiantRepository etudiantRepository;
    private final AbsenceRepository absenceRepository;
    private final PresenceLogRepository presenceLogRepository;

    @Override
    @Transactional
    public PresenceIAResponse enregistrerPresenceIA(PresenceIARequest request) {
        log.info("Traitement présence IA étudiant {} séance {}", request.getEtudiantId(), request.getSeanceId());

        Seance seance = seanceRepository.findById(request.getSeanceId())
                .orElseThrow(() -> new EntityNotFoundException("Séance introuvable"));

        Etudiant etudiant = etudiantRepository.findById(request.getEtudiantId())
                .orElseThrow(() -> new EntityNotFoundException("Étudiant introuvable"));

        LocalDateTime ts = request.getTimestamp();
        if (!seance.getActive() || ts.isBefore(seance.getDateDebut()) || ts.isAfter(seance.getDateFin())) {
            throw new SeanceInactiveException("La séance n'est pas active à ce timestamp.");
        }

        Optional<Absence> existingAbsence = absenceRepository.findByEtudiantIdAndSeanceId(etudiant.getId(), seance.getId());
        
        if (existingAbsence.isPresent()) {
            Absence absence = existingAbsence.get();
            if (absence.getStatut() != StatutAbsence.PRESENT) {
                absence.setStatut(StatutAbsence.PRESENT);
                absenceRepository.save(absence);
            }
        } else {
            Absence absence = new Absence();
            absence.setEtudiant(etudiant);
            absence.setSeance(seance);
            absence.setStatut(StatutAbsence.PRESENT);
            absenceRepository.save(absence);
        }

        PresenceLog logEntry = new PresenceLog();
        logEntry.setEtudiant(etudiant);
        logEntry.setSeance(seance);
        logEntry.setTimestamp(ts);
        logEntry.setSource(SourcePresence.AI);
        logEntry.setConfidenceScore(request.getConfidenceScore());
        
        logEntry = presenceLogRepository.save(logEntry);

        return PresenceIAResponse.builder()
                .logId(logEntry.getId())
                .statut(StatutAbsence.PRESENT)
                .message("Présence enregistrée avec succès via IA")
                .build();
    }
}
