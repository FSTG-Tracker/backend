package com.universite.absences.mapper;

import com.universite.absences.dto.request.AbsenceRequest;
import com.universite.absences.dto.response.AbsenceResponse;
import com.universite.absences.entity.Absence;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AbsenceMapper {
    Absence toEntity(AbsenceRequest request);

    @Mapping(source = "etudiant.id", target = "etudiantId")
    @Mapping(source = "etudiant.nom", target = "etudiantNom")
    @Mapping(source = "etudiant.prenom", target = "etudiantPrenom")
    @Mapping(source = "etudiant.cne", target = "etudiantCne")
    @Mapping(source = "seance.id", target = "seanceId")
    @Mapping(source = "seance.module.nom", target = "moduleNom")
    @Mapping(source = "etudiant.filiere.nom", target = "filiereNom")
    @Mapping(source = "seance.dateDebut", target = "dateSeance")
    AbsenceResponse toResponse(Absence entity);
}
