package com.universite.absences.mapper;

import com.universite.absences.dto.request.SeanceRequest;
import com.universite.absences.dto.response.SeanceResponse;
import com.universite.absences.entity.Seance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SeanceMapper {

    @Mapping(target = "module.id", source = "moduleId")
    @Mapping(target = "professeur.id", source = "professeurId")
    @Mapping(target = "salle.id", source = "salleId")
    Seance toEntity(SeanceRequest request);
    
    @Mapping(target = "moduleId", source = "module.id")
    @Mapping(target = "moduleNom", source = "module.nom")
    @Mapping(target = "professeurId", source = "professeur.id")
    @Mapping(target = "professeurNom", source = "professeur.nom")
    @Mapping(target = "salleId", source = "salle.id")
    @Mapping(target = "salleNom", source = "salle.nom")
    SeanceResponse toResponse(Seance entity);
}
