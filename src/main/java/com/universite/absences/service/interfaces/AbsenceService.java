package com.universite.absences.service.interfaces;

import com.universite.absences.dto.request.AbsenceRequest;
import com.universite.absences.dto.response.AbsenceResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AbsenceService {
    Page<AbsenceResponse> getAll(Pageable pageable);
    AbsenceResponse getById(Long id);
    List<AbsenceResponse> getByEtudiant(Long etudiantId);
    AbsenceResponse create(AbsenceRequest request);
    AbsenceResponse update(Long id, AbsenceRequest request);
    void delete(Long id);
    AbsenceResponse justifier(Long id, String justification);
}
