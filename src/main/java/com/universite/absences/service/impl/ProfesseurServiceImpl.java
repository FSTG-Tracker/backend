package com.universite.absences.service.impl;

import com.universite.absences.dto.request.ProfesseurRequest;
import com.universite.absences.dto.response.ProfesseurResponse;
import com.universite.absences.entity.Departement;
import com.universite.absences.entity.Professeur;
import com.universite.absences.exception.EntityNotFoundException;
import com.universite.absences.mapper.ProfesseurMapper;
import com.universite.absences.repository.DepartementRepository;
import com.universite.absences.repository.ProfesseurRepository;
import com.universite.absences.service.interfaces.ProfesseurService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfesseurServiceImpl implements ProfesseurService {

    private final ProfesseurRepository professeurRepository;
    private final DepartementRepository departementRepository;
    private final ProfesseurMapper professeurMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Page<ProfesseurResponse> getAll(Pageable pageable) {
        return professeurRepository.findAll(pageable).map(professeurMapper::toResponse);
    }

    @Override
    public ProfesseurResponse getById(Long id) {
        return professeurRepository.findById(id)
                .map(professeurMapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Professeur introuvable avec l'id : " + id));
    }

    @Override
    public ProfesseurResponse create(ProfesseurRequest request) {
        Professeur professeur = professeurMapper.toEntity(request);
        String pwd = request.getPassword() != null && !request.getPassword().isBlank()
                ? request.getPassword() : "prof1234";
        professeur.setPassword(passwordEncoder.encode(pwd));

        if (request.getDepartementId() != null) {
            Departement dept = departementRepository.findById(request.getDepartementId())
                    .orElseThrow(() -> new EntityNotFoundException("Département introuvable"));
            professeur.setDepartement(dept);
        }

        return professeurMapper.toResponse(professeurRepository.save(professeur));
    }

    @Override
    public ProfesseurResponse update(Long id, ProfesseurRequest request) {
        Professeur professeur = professeurRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Professeur introuvable avec l'id : " + id));

        professeur.setNom(request.getNom());
        professeur.setPrenom(request.getPrenom());
        professeur.setEmail(request.getEmail());

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            professeur.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        if (request.getDepartementId() != null) {
            Departement dept = departementRepository.findById(request.getDepartementId())
                    .orElseThrow(() -> new EntityNotFoundException("Département introuvable"));
            professeur.setDepartement(dept);
        }

        return professeurMapper.toResponse(professeurRepository.save(professeur));
    }

    @Override
    public void delete(Long id) {
        if (!professeurRepository.existsById(id)) {
            throw new EntityNotFoundException("Professeur introuvable avec l'id : " + id);
        }
        professeurRepository.deleteById(id);
    }
}
