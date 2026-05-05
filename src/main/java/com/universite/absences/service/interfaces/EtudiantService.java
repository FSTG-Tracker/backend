package com.universite.absences.service.interfaces;

import com.universite.absences.dto.request.EtudiantRequest;
import com.universite.absences.dto.response.EtudiantResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EtudiantService {
    Page<EtudiantResponse> getAllEtudiants(Pageable pageable);
    EtudiantResponse createEtudiant(EtudiantRequest request);
    EtudiantResponse getEtudiantById(Long id);
}
