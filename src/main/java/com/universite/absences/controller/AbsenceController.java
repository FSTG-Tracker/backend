package com.universite.absences.controller;

import com.universite.absences.dto.request.AbsenceRequest;
import com.universite.absences.dto.response.AbsenceResponse;
import com.universite.absences.service.interfaces.AbsenceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/absences")
@RequiredArgsConstructor
public class AbsenceController {

    private final AbsenceService absenceService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSEUR')")
    public ResponseEntity<Page<AbsenceResponse>> getAll(Pageable pageable) {
        return ResponseEntity.ok(absenceService.getAll(pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSEUR') or hasRole('ETUDIANT')")
    public ResponseEntity<AbsenceResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(absenceService.getById(id));
    }

    @GetMapping("/etudiant/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSEUR') or (hasRole('ETUDIANT') and principal.id == #id)")
    public ResponseEntity<List<AbsenceResponse>> getByEtudiant(@PathVariable Long id) {
        return ResponseEntity.ok(absenceService.getByEtudiant(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSEUR')")
    public ResponseEntity<AbsenceResponse> create(@Valid @RequestBody AbsenceRequest request) {
        return ResponseEntity.ok(absenceService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSEUR')")
    public ResponseEntity<AbsenceResponse> update(@PathVariable Long id, @Valid @RequestBody AbsenceRequest request) {
        return ResponseEntity.ok(absenceService.update(id, request));
    }

    @PatchMapping("/{id}/justifier")
    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSEUR')")
    public ResponseEntity<AbsenceResponse> justifier(@PathVariable Long id, @RequestBody String justification) {
        return ResponseEntity.ok(absenceService.justifier(id, justification));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        absenceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
