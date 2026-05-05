package com.universite.absences.mapper;

import com.universite.absences.dto.request.EtudiantRequest;
import com.universite.absences.dto.response.EtudiantResponse;
import com.universite.absences.entity.Etudiant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EtudiantMapper {
    Etudiant toEntity(EtudiantRequest request);
    EtudiantResponse toResponse(Etudiant entity);
}
