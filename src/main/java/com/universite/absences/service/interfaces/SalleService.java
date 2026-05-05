package com.universite.absences.service.interfaces;

import com.universite.absences.dto.request.SalleRequest;
import com.universite.absences.dto.response.SalleResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SalleService {
    Page<SalleResponse> getAll(Pageable pageable);
    SalleResponse getById(Long id);
    SalleResponse create(SalleRequest request);
    SalleResponse update(Long id, SalleRequest request);
    void delete(Long id);
}
