package com.universite.absences.service.interfaces;

import com.universite.absences.dto.request.SeanceRequest;
import com.universite.absences.dto.response.SeanceResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SeanceService {
    Page<SeanceResponse> getAll(Pageable pageable);
    SeanceResponse getById(Long id);
    SeanceResponse create(SeanceRequest request);
    SeanceResponse update(Long id, SeanceRequest request);
    void delete(Long id);
}
