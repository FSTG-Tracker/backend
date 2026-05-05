package com.universite.absences.service.impl;

import com.universite.absences.dto.request.SeanceRequest;
import com.universite.absences.dto.response.SeanceResponse;
import com.universite.absences.entity.Module;
import com.universite.absences.entity.Professeur;
import com.universite.absences.entity.Salle;
import com.universite.absences.entity.Seance;
import com.universite.absences.exception.EntityNotFoundException;
import com.universite.absences.mapper.SeanceMapper;
import com.universite.absences.repository.ModuleRepository;
import com.universite.absences.repository.SalleRepository;
import com.universite.absences.repository.SeanceRepository;
import com.universite.absences.repository.UserRepository; // Contains User, cast to Professeur needed
import com.universite.absences.service.interfaces.SeanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SeanceServiceImpl implements SeanceService {

    private final SeanceRepository repository;
    private final ModuleRepository moduleRepository;
    private final SalleRepository salleRepository;
    private final UserRepository userRepository;
    private final SeanceMapper mapper;

    @Override
    public Page<SeanceResponse> getAll(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    @Override
    public SeanceResponse getById(Long id) {
        return repository.findById(id).map(mapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Séance introuvable"));
    }

    @Override
    public SeanceResponse create(SeanceRequest request) {
        Seance entity = mapper.toEntity(request);
        entity.setModule(moduleRepository.findById(request.getModuleId())
                .orElseThrow(() -> new EntityNotFoundException("Module introuvable")));
        entity.setSalle(salleRepository.findById(request.getSalleId())
                .orElseThrow(() -> new EntityNotFoundException("Salle introuvable")));
        entity.setProfesseur((Professeur) userRepository.findById(request.getProfesseurId())
                .orElseThrow(() -> new EntityNotFoundException("Professeur introuvable")));
        
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public SeanceResponse update(Long id, SeanceRequest request) {
        Seance entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Séance introuvable"));
        
        entity.setModule(moduleRepository.findById(request.getModuleId())
                .orElseThrow(() -> new EntityNotFoundException("Module introuvable")));
        entity.setSalle(salleRepository.findById(request.getSalleId())
                .orElseThrow(() -> new EntityNotFoundException("Salle introuvable")));
        entity.setProfesseur((Professeur) userRepository.findById(request.getProfesseurId())
                .orElseThrow(() -> new EntityNotFoundException("Professeur introuvable")));
        
        entity.setDateDebut(request.getDateDebut());
        entity.setDateFin(request.getDateFin());
        entity.setActive(request.getActive());
        
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) throw new EntityNotFoundException("Séance introuvable");
        repository.deleteById(id);
    }
}
