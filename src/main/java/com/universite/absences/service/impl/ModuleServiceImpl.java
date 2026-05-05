package com.universite.absences.service.impl;

import com.universite.absences.dto.request.ModuleRequest;
import com.universite.absences.dto.response.ModuleResponse;
import com.universite.absences.entity.Filiere;
import com.universite.absences.entity.Module;
import com.universite.absences.exception.EntityNotFoundException;
import com.universite.absences.mapper.ModuleMapper;
import com.universite.absences.repository.FiliereRepository;
import com.universite.absences.repository.ModuleRepository;
import com.universite.absences.service.interfaces.ModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ModuleServiceImpl implements ModuleService {

    private final ModuleRepository repository;
    private final FiliereRepository filiereRepository;
    private final ModuleMapper mapper;

    @Override
    public Page<ModuleResponse> getAll(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    @Override
    public ModuleResponse getById(Long id) {
        return repository.findById(id).map(mapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Module introuvable"));
    }

    @Override
    public ModuleResponse create(ModuleRequest request) {
        Filiere filiere = filiereRepository.findById(request.getFiliereId())
                .orElseThrow(() -> new EntityNotFoundException("Filière introuvable"));
        Module entity = mapper.toEntity(request);
        entity.setFiliere(filiere);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public ModuleResponse update(Long id, ModuleRequest request) {
        Module entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Module introuvable"));
        Filiere filiere = filiereRepository.findById(request.getFiliereId())
                .orElseThrow(() -> new EntityNotFoundException("Filière introuvable"));
        
        entity.setNom(request.getNom());
        entity.setFiliere(filiere);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) throw new EntityNotFoundException("Module introuvable");
        repository.deleteById(id);
    }
}
