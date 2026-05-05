package com.universite.absences.service.impl;

import com.universite.absences.dto.request.DepartementRequest;
import com.universite.absences.dto.response.DepartementResponse;
import com.universite.absences.entity.Departement;
import com.universite.absences.exception.EntityNotFoundException;
import com.universite.absences.mapper.DepartementMapper;
import com.universite.absences.repository.DepartementRepository;
import com.universite.absences.service.interfaces.DepartementService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DepartementServiceImpl implements DepartementService {

    private final DepartementRepository repository;
    private final DepartementMapper mapper;

    @Override
    public Page<DepartementResponse> getAll(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    @Override
    public DepartementResponse getById(Long id) {
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Département introuvable"));
    }

    @Override
    public DepartementResponse create(DepartementRequest request) {
        Departement entity = mapper.toEntity(request);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public DepartementResponse update(Long id, DepartementRequest request) {
        Departement entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Département introuvable"));
        entity.setNom(request.getNom());
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) throw new EntityNotFoundException("Département introuvable");
        repository.deleteById(id);
    }
}
