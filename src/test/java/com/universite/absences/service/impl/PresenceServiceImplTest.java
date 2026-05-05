package com.universite.absences.service.impl;

import com.universite.absences.dto.request.PresenceIARequest;
import com.universite.absences.dto.response.PresenceIAResponse;
import com.universite.absences.entity.Absence;
import com.universite.absences.entity.Etudiant;
import com.universite.absences.entity.PresenceLog;
import com.universite.absences.entity.Seance;
import com.universite.absences.entity.enums.StatutAbsence;
import com.universite.absences.exception.SeanceInactiveException;
import com.universite.absences.repository.AbsenceRepository;
import com.universite.absences.repository.EtudiantRepository;
import com.universite.absences.repository.PresenceLogRepository;
import com.universite.absences.repository.SeanceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PresenceServiceImplTest {

    @Mock private SeanceRepository seanceRepository;
    @Mock private EtudiantRepository etudiantRepository;
    @Mock private AbsenceRepository absenceRepository;
    @Mock private PresenceLogRepository presenceLogRepository;

    @InjectMocks
    private PresenceServiceImpl presenceService;

    private Seance seance;
    private Etudiant etudiant;
    private PresenceIARequest request;

    @BeforeEach
    void setUp() {
        seance = new Seance();
        seance.setId(1L);
        seance.setActive(true);
        seance.setDateDebut(LocalDateTime.now().minusHours(1));
        seance.setDateFin(LocalDateTime.now().plusHours(1));

        etudiant = new Etudiant();
        etudiant.setId(1L);

        request = PresenceIARequest.builder()
                .etudiantId(1L)
                .seanceId(1L)
                .timestamp(LocalDateTime.now())
                .confidenceScore(0.95)
                .build();
    }

    @Test
    void testEnregistrerPresenceIA_CasNominal() {
        when(seanceRepository.findById(1L)).thenReturn(Optional.of(seance));
        when(etudiantRepository.findById(1L)).thenReturn(Optional.of(etudiant));
        when(absenceRepository.findByEtudiantIdAndSeanceId(1L, 1L)).thenReturn(Optional.empty());

        PresenceLog savedLog = new PresenceLog();
        savedLog.setId(100L);
        when(presenceLogRepository.save(any(PresenceLog.class))).thenReturn(savedLog);

        PresenceIAResponse response = presenceService.enregistrerPresenceIA(request);

        assertNotNull(response);
        assertEquals(100L, response.getLogId());
        verify(absenceRepository).save(any(Absence.class));
        verify(presenceLogRepository).save(any(PresenceLog.class));
    }

    @Test
    void testEnregistrerPresenceIA_SeanceInactive() {
        request.setTimestamp(LocalDateTime.now().plusHours(2));
        when(seanceRepository.findById(1L)).thenReturn(Optional.of(seance));
        when(etudiantRepository.findById(1L)).thenReturn(Optional.of(etudiant));

        assertThrows(SeanceInactiveException.class, () -> presenceService.enregistrerPresenceIA(request));
    }
}
