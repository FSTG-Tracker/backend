package com.universite.absences.service.impl;

import com.universite.absences.dto.request.EtudiantRequest;
import com.universite.absences.dto.response.EtudiantResponse;
import com.universite.absences.entity.Etudiant;
import com.universite.absences.exception.EntityNotFoundException;
import com.universite.absences.mapper.EtudiantMapper;
import com.universite.absences.repository.EtudiantRepository;
import com.universite.absences.service.interfaces.EtudiantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EtudiantServiceImpl implements EtudiantService {

    private final EtudiantRepository etudiantRepository;
    private final EtudiantMapper etudiantMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Page<EtudiantResponse> getAllEtudiants(Pageable pageable) {
        return etudiantRepository.findAll(pageable).map(etudiantMapper::toResponse);
    }

    @Override
    public EtudiantResponse createEtudiant(EtudiantRequest request) {
        Etudiant etudiant = etudiantMapper.toEntity(request);
        etudiant.setPassword(passwordEncoder.encode(request.getPassword()));
        return etudiantMapper.toResponse(etudiantRepository.save(etudiant));
    }

    @Override
    public EtudiantResponse getEtudiantById(Long id) {
        return etudiantRepository.findById(id)
                .map(etudiantMapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Etudiant introuvable"));
    }
}
