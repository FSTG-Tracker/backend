package com.universite.absences.controller;

import com.universite.absences.dto.request.PresenceIARequest;
import com.universite.absences.dto.response.ClassroomScanResponse;
import com.universite.absences.dto.response.PresenceIAResponse;
import com.universite.absences.service.interfaces.AttendanceService;
import com.universite.absences.service.interfaces.PresenceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/presence")
@RequiredArgsConstructor
public class PresenceController {

    private final PresenceService presenceService;
    private final AttendanceService attendanceService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSEUR')")
    public ResponseEntity<PresenceIAResponse> enregistrerPresenceIA(
            @Valid @RequestBody PresenceIARequest request) {
        return ResponseEntity.ok(presenceService.enregistrerPresenceIA(request));
    }

    @PostMapping(value = "/scanner", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSEUR')")
    public ResponseEntity<ClassroomScanResponse> scannerClasse(
            @RequestParam("seanceId") Long seanceId,
            @RequestParam("image") MultipartFile image) {
        return ResponseEntity.ok(attendanceService.markAttendanceFromClassroomImage(seanceId, image));
    }
}
