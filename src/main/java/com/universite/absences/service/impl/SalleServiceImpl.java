package com.universite.absences.service.impl;

import com.universite.absences.dto.request.SalleRequest;
import com.universite.absences.dto.response.SalleResponse;
import com.universite.absences.entity.Salle;
import com.universite.absences.exception.EntityNotFoundException;
import com.universite.absences.mapper.SalleMapper;
import com.universite.absences.repository.SalleRepository;
import com.universite.absences.service.interfaces.SalleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SalleServiceImpl implements SalleService {

    private final SalleRepository repository;
    private final SalleMapper mapper;

    @Override
    public Page<SalleResponse> getAll(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    @Override
    public SalleResponse getById(Long id) {
        return repository.findById(id).map(mapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Salle introuvable"));
    }

    @Override
    public SalleResponse create(SalleRequest request) {
        Salle entity = mapper.toEntity(request);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public SalleResponse update(Long id, SalleRequest request) {
        Salle entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Salle introuvable"));
        
        entity.setNom(request.getNom());
        entity.setCapacite(request.getCapacite());
        entity.setType(request.getType());
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) throw new EntityNotFoundException("Salle introuvable");
        repository.deleteById(id);
    }
}
