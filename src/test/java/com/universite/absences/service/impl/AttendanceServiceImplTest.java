package com.universite.absences.service.impl;

import com.universite.absences.dto.external.FaceRecognizeResponse;
import com.universite.absences.dto.response.AttendanceScanResponse;
import com.universite.absences.dto.response.PresenceIAResponse;
import com.universite.absences.entity.Etudiant;
import com.universite.absences.entity.Filiere;
import com.universite.absences.entity.Seance;
import com.universite.absences.entity.enums.StatutAbsence;
import com.universite.absences.exception.EntityNotFoundException;
import com.universite.absences.repository.AbsenceRepository;
import com.universite.absences.repository.EtudiantRepository;
import com.universite.absences.repository.PresenceLogRepository;
import com.universite.absences.repository.SeanceRepository;
import com.universite.absences.service.interfaces.AiIntegrationService;
import com.universite.absences.service.interfaces.PresenceService;
import com.universite.absences.util.StudentNameMatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class AttendanceServiceImplTest {

    @Mock private AiIntegrationService aiIntegrationService;
    @Mock private PresenceService presenceService;
    @Mock private StudentNameMatcher studentNameMatcher;
    @Mock private SeanceRepository seanceRepository;
    @Mock private EtudiantRepository etudiantRepository;
    @Mock private AbsenceRepository absenceRepository;
    @Mock private PresenceLogRepository presenceLogRepository;

    @InjectMocks
    private AttendanceServiceImpl attendanceService;

    private Seance activeSeance;

    @BeforeEach
    void setUp() {
        Filiere filiere = new Filiere();
        filiere.setId(1L);
        filiere.setNom("SMI");
        com.universite.absences.entity.Module module = new com.universite.absences.entity.Module();
        module.setNom("Java");
        module.setFiliere(filiere);
        activeSeance = new Seance();
        activeSeance.setId(5L);
        activeSeance.setModule(module);
        activeSeance.setActive(true);
        activeSeance.setDateDebut(LocalDateTime.now().minusHours(1));
        activeSeance.setDateFin(LocalDateTime.now().plusHours(2));

        when(seanceRepository.findWithModuleAndFiliereById(anyLong())).thenReturn(Optional.of(activeSeance));
    }

    @Test
    void markAttendanceFromImage_whenNotRecognized_doesNotCallPresenceService() {
        MultipartFile image = new MockMultipartFile("image", "face.jpg", "image/jpeg", new byte[]{1});
        FaceRecognizeResponse aiResponse = FaceRecognizeResponse.builder()
                .studentName("Unknown")
                .similarity(0.3)
                .recognized(false)
                .build();

        when(aiIntegrationService.recognizeFace(image)).thenReturn(aiResponse);

        AttendanceScanResponse result = attendanceService.markAttendanceFromImage(1L, image);

        assertFalse(result.isAttendanceMarked());
        assertNull(result.getPresence());
        verifyNoInteractions(presenceService);
    }

    @Test
    void markAttendanceFromImage_whenRecognized_marksPresence() {
        MultipartFile image = new MockMultipartFile("image", "face.jpg", "image/jpeg", new byte[]{1});
        FaceRecognizeResponse aiResponse = FaceRecognizeResponse.builder()
                .studentName("Yassine Berrada")
                .similarity(0.92)
                .recognized(true)
                .build();
        Etudiant etudiant = new Etudiant();
        etudiant.setId(10L);
        PresenceIAResponse presenceResponse = PresenceIAResponse.builder()
                .logId(99L)
                .statut(StatutAbsence.PRESENT)
                .message("ok")
                .build();

        when(aiIntegrationService.recognizeFace(image)).thenReturn(aiResponse);
        when(studentNameMatcher.resolve("Yassine Berrada")).thenReturn(Optional.of(etudiant));
        when(etudiantRepository.existsByIdAndFiliereId(10L, 1L)).thenReturn(true);
        when(presenceService.enregistrerPresenceIA(any())).thenReturn(presenceResponse);

        AttendanceScanResponse result = attendanceService.markAttendanceFromImage(5L, image);

        assertTrue(result.isAttendanceMarked());
        assertEquals(99L, result.getPresence().getLogId());
        verify(presenceService).enregistrerPresenceIA(any());
    }

    @Test
    void markAttendanceFromImage_whenStudentNotInDb_throws() {
        MultipartFile image = new MockMultipartFile("image", "face.jpg", "image/jpeg", new byte[]{1});
        FaceRecognizeResponse aiResponse = FaceRecognizeResponse.builder()
                .studentName("Inconnu Test")
                .similarity(0.95)
                .recognized(true)
                .build();

        when(aiIntegrationService.recognizeFace(image)).thenReturn(aiResponse);
        when(studentNameMatcher.resolve("Inconnu Test")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> attendanceService.markAttendanceFromImage(1L, image));
    }
}
