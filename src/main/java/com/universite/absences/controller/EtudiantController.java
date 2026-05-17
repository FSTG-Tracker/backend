package com.universite.absences.controller;

import com.universite.absences.dto.request.EtudiantRequest;
import com.universite.absences.dto.response.EtudiantResponse;
import com.universite.absences.exception.AiServiceException;
import com.universite.absences.service.interfaces.EtudiantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("/api/etudiants")
@RequiredArgsConstructor
public class EtudiantController {

    private final EtudiantService etudiantService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSEUR')")
    public ResponseEntity<Page<EtudiantResponse>> getAllEtudiants(Pageable pageable) {
        return ResponseEntity.ok(etudiantService.getAllEtudiants(pageable));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EtudiantResponse> createEtudiant(@Valid @RequestBody EtudiantRequest request) {
        return ResponseEntity.ok(etudiantService.createEtudiant(request));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSEUR') or (hasRole('ETUDIANT') and principal.id == #id)")
    public ResponseEntity<EtudiantResponse> getEtudiantById(@PathVariable Long id) {
        return ResponseEntity.ok(etudiantService.getEtudiantById(id));
    }

    @PostMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> enrollEtudiantFace(
            @PathVariable Long id,
            @RequestParam("image") MultipartFile image
    ) {
        throw new AiServiceException(
                "L'enrôlement facial se fait via Face-Recognition/register_students.py (microservice indépendant). Étudiant id=" + id);
    }

}
