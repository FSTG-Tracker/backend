package com.universite.absences.service.interfaces;

import com.universite.absences.dto.request.ModuleRequest;
import com.universite.absences.dto.response.ModuleResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ModuleService {
    Page<ModuleResponse> getAll(Pageable pageable);
    ModuleResponse getById(Long id);
    ModuleResponse create(ModuleRequest request);
    ModuleResponse update(Long id, ModuleRequest request);
    void delete(Long id);
}
