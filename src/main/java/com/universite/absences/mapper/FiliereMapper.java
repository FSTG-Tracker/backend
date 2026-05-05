package com.universite.absences.mapper;

import com.universite.absences.dto.request.FiliereRequest;
import com.universite.absences.dto.response.FiliereResponse;
import com.universite.absences.entity.Filiere;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FiliereMapper {
    
    @Mapping(target = "departement.id", source = "departementId")
    Filiere toEntity(FiliereRequest request);
    
    @Mapping(target = "departementId", source = "departement.id")
    @Mapping(target = "departementNom", source = "departement.nom")
    FiliereResponse toResponse(Filiere entity);
}
