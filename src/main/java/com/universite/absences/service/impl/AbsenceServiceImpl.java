package com.universite.absences.service.impl;

import com.universite.absences.dto.request.AbsenceRequest;
import com.universite.absences.dto.response.AbsenceResponse;
import com.universite.absences.entity.Absence;
import com.universite.absences.entity.enums.StatutAbsence;
import com.universite.absences.exception.EntityNotFoundException;
import com.universite.absences.mapper.AbsenceMapper;
import com.universite.absences.repository.AbsenceRepository;
import com.universite.absences.repository.EtudiantRepository;
import com.universite.absences.repository.SeanceRepository;
import com.universite.absences.service.interfaces.AbsenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AbsenceServiceImpl implements AbsenceService {

    private final AbsenceRepository absenceRepository;
    private final EtudiantRepository etudiantRepository;
    private final SeanceRepository seanceRepository;
    private final AbsenceMapper absenceMapper;

    @Override
    public Page<AbsenceResponse> getAll(Pageable pageable) {
        return absenceRepository.findAll(pageable).map(absenceMapper::toResponse);
    }

    @Override
    public AbsenceResponse getById(Long id) {
        return absenceRepository.findById(id)
                .map(absenceMapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Absence non trouvée"));
    }

    @Override
    public List<AbsenceResponse> getByEtudiant(Long etudiantId) {
        return absenceRepository.findByEtudiantId(etudiantId).stream()
                .map(absenceMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AbsenceResponse create(AbsenceRequest request) {
        Absence absence = absenceMapper.toEntity(request);
        absence.setEtudiant(etudiantRepository.findById(request.getEtudiantId())
                .orElseThrow(() -> new EntityNotFoundException("Étudiant non trouvé")));
        absence.setSeance(seanceRepository.findById(request.getSeanceId())
                .orElseThrow(() -> new EntityNotFoundException("Séance non trouvée")));
        return absenceMapper.toResponse(absenceRepository.save(absence));
    }

    @Override
    @Transactional
    public AbsenceResponse update(Long id, AbsenceRequest request) {
        Absence absence = absenceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Absence non trouvée"));
        absence.setStatut(request.getStatut());
        absence.setJustification(request.getJustification());
        return absenceMapper.toResponse(absenceRepository.save(absence));
    }

    @Override
    public void delete(Long id) {
        absenceRepository.deleteById(id);
    }

    @Override
    @Transactional
    public AbsenceResponse justifier(Long id, String justification) {
        Absence absence = absenceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Absence non trouvée"));
        absence.setStatut(StatutAbsence.JUSTIFIE);
        absence.setJustification(justification);
        return absenceMapper.toResponse(absenceRepository.save(absence));
    }
}
