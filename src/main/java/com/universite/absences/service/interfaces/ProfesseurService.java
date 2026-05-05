package com.universite.absences.service.interfaces;

import com.universite.absences.dto.request.ProfesseurRequest;
import com.universite.absences.dto.response.ProfesseurResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProfesseurService {
    Page<ProfesseurResponse> getAll(Pageable pageable);
    ProfesseurResponse getById(Long id);
    ProfesseurResponse create(ProfesseurRequest request);
    ProfesseurResponse update(Long id, ProfesseurRequest request);
    void delete(Long id);
}
