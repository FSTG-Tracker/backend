package com.universite.absences.mapper;

import com.universite.absences.dto.request.ProfesseurRequest;
import com.universite.absences.dto.response.ProfesseurResponse;
import com.universite.absences.entity.Professeur;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProfesseurMapper {
    Professeur toEntity(ProfesseurRequest request);

    @Mapping(source = "departement.id", target = "departementId")
    @Mapping(source = "departement.nom", target = "departementNom")
    ProfesseurResponse toResponse(Professeur entity);
}
