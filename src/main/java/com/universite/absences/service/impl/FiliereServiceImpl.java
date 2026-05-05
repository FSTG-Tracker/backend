package com.universite.absences.service.impl;

import com.universite.absences.dto.request.FiliereRequest;
import com.universite.absences.dto.response.FiliereResponse;
import com.universite.absences.entity.Departement;
import com.universite.absences.entity.Filiere;
import com.universite.absences.exception.EntityNotFoundException;
import com.universite.absences.mapper.FiliereMapper;
import com.universite.absences.repository.DepartementRepository;
import com.universite.absences.repository.FiliereRepository;
import com.universite.absences.service.interfaces.FiliereService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FiliereServiceImpl implements FiliereService {

    private final FiliereRepository repository;
    private final DepartementRepository departementRepository;
    private final FiliereMapper mapper;

    @Override
    public Page<FiliereResponse> getAll(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    @Override
    public FiliereResponse getById(Long id) {
        return repository.findById(id).map(mapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Filière introuvable"));
    }

    @Override
    public FiliereResponse create(FiliereRequest request) {
        Departement dep = departementRepository.findById(request.getDepartementId())
                .orElseThrow(() -> new EntityNotFoundException("Département introuvable"));
        Filiere entity = mapper.toEntity(request);
        entity.setDepartement(dep);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public FiliereResponse update(Long id, FiliereRequest request) {
        Filiere entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Filière introuvable"));
        Departement dep = departementRepository.findById(request.getDepartementId())
                .orElseThrow(() -> new EntityNotFoundException("Département introuvable"));
        
        entity.setNom(request.getNom());
        entity.setDepartement(dep);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) throw new EntityNotFoundException("Filière introuvable");
        repository.deleteById(id);
    }
}
