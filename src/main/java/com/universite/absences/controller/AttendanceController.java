package com.universite.absences.controller;

import com.universite.absences.dto.response.AttendanceScanResponse;
import com.universite.absences.dto.response.ClassroomScanResponse;
import com.universite.absences.dto.response.SeanceAttendanceStatusResponse;
import com.universite.absences.service.interfaces.AiIntegrationService;
import com.universite.absences.service.interfaces.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;
    private final AiIntegrationService aiIntegrationService;

    @GetMapping("/ai/health")
    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSEUR')")
    public ResponseEntity<Map<String, Object>> checkAiHealth() {
        boolean healthy = aiIntegrationService.checkHealth();
        return ResponseEntity.ok(Map.of(
                "aiServiceAvailable", healthy,
                "status", healthy ? "UP" : "DOWN"));
    }

    /** Scan individuel : un visage dans l'image */
    @PostMapping(value = "/scan", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSEUR')")
    public ResponseEntity<AttendanceScanResponse> scanAndMarkAttendance(
            @RequestParam("seanceId") Long seanceId,
            @RequestParam("image") MultipartFile image) {
        return ResponseEntity.ok(attendanceService.markAttendanceFromImage(seanceId, image));
    }

    /** Scan de classe : tous les visages d'une photo de séance */
    @PostMapping(value = "/scan/classroom", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSEUR')")
    public ResponseEntity<ClassroomScanResponse> scanClassroomAndMarkAttendance(
            @RequestParam("seanceId") Long seanceId,
            @RequestParam("image") MultipartFile image) {
        return ResponseEntity.ok(attendanceService.markAttendanceFromClassroomImage(seanceId, image));
    }

    /** Statut de présence de tous les étudiants de la filière liée à la séance */
    @GetMapping("/seance/{seanceId}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSEUR')")
    public ResponseEntity<SeanceAttendanceStatusResponse> getSeanceAttendanceStatus(
            @PathVariable Long seanceId) {
        return ResponseEntity.ok(attendanceService.getSeanceAttendanceStatus(seanceId));
    }

    /** Même statut, filtré par filière (classe) */
    @GetMapping("/filiere/{filiereId}/seance/{seanceId}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSEUR')")
    public ResponseEntity<SeanceAttendanceStatusResponse> getFiliereAttendanceStatus(
            @PathVariable Long filiereId,
            @PathVariable Long seanceId) {
        return ResponseEntity.ok(attendanceService.getFiliereAttendanceStatusForSeance(filiereId, seanceId));
    }
}
