package com.universite.absences.service.interfaces;

import com.universite.absences.dto.request.FiliereRequest;
import com.universite.absences.dto.response.FiliereResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FiliereService {
    Page<FiliereResponse> getAll(Pageable pageable);
    FiliereResponse getById(Long id);
    FiliereResponse create(FiliereRequest request);
    FiliereResponse update(Long id, FiliereRequest request);
    void delete(Long id);
}
