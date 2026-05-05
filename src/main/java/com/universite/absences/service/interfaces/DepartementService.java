package com.universite.absences.service.interfaces;

import com.universite.absences.dto.request.DepartementRequest;
import com.universite.absences.dto.response.DepartementResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DepartementService {
    Page<DepartementResponse> getAll(Pageable pageable);
    DepartementResponse getById(Long id);
    DepartementResponse create(DepartementRequest request);
    DepartementResponse update(Long id, DepartementRequest request);
    void delete(Long id);
}
