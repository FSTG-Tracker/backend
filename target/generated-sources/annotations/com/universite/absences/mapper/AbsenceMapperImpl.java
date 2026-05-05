package com.universite.absences.mapper;

import com.universite.absences.dto.request.AbsenceRequest;
import com.universite.absences.dto.response.AbsenceResponse;
import com.universite.absences.entity.Absence;
import com.universite.absences.entity.Etudiant;
import com.universite.absences.entity.Module;
import com.universite.absences.entity.Seance;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-05T22:33:43+0100",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.46.0.v20260407-0427, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class AbsenceMapperImpl implements AbsenceMapper {

    @Override
    public Absence toEntity(AbsenceRequest request) {
        if ( request == null ) {
            return null;
        }

        Absence absence = new Absence();

        absence.setJustification( request.getJustification() );
        absence.setStatut( request.getStatut() );

        return absence;
    }

    @Override
    public AbsenceResponse toResponse(Absence entity) {
        if ( entity == null ) {
            return null;
        }

        AbsenceResponse absenceResponse = new AbsenceResponse();

        absenceResponse.setEtudiantId( entityEtudiantId( entity ) );
        absenceResponse.setEtudiantNom( entityEtudiantNom( entity ) );
        absenceResponse.setEtudiantPrenom( entityEtudiantPrenom( entity ) );
        absenceResponse.setSeanceId( entitySeanceId( entity ) );
        absenceResponse.setModuleNom( entitySeanceModuleNom( entity ) );
        absenceResponse.setDateSeance( entitySeanceDateDebut( entity ) );
        absenceResponse.setId( entity.getId() );
        absenceResponse.setJustification( entity.getJustification() );
        absenceResponse.setStatut( entity.getStatut() );

        return absenceResponse;
    }

    private Long entityEtudiantId(Absence absence) {
        if ( absence == null ) {
            return null;
        }
        Etudiant etudiant = absence.getEtudiant();
        if ( etudiant == null ) {
            return null;
        }
        Long id = etudiant.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String entityEtudiantNom(Absence absence) {
        if ( absence == null ) {
            return null;
        }
        Etudiant etudiant = absence.getEtudiant();
        if ( etudiant == null ) {
            return null;
        }
        String nom = etudiant.getNom();
        if ( nom == null ) {
            return null;
        }
        return nom;
    }

    private String entityEtudiantPrenom(Absence absence) {
        if ( absence == null ) {
            return null;
        }
        Etudiant etudiant = absence.getEtudiant();
        if ( etudiant == null ) {
            return null;
        }
        String prenom = etudiant.getPrenom();
        if ( prenom == null ) {
            return null;
        }
        return prenom;
    }

    private Long entitySeanceId(Absence absence) {
        if ( absence == null ) {
            return null;
        }
        Seance seance = absence.getSeance();
        if ( seance == null ) {
            return null;
        }
        Long id = seance.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String entitySeanceModuleNom(Absence absence) {
        if ( absence == null ) {
            return null;
        }
        Seance seance = absence.getSeance();
        if ( seance == null ) {
            return null;
        }
        Module module = seance.getModule();
        if ( module == null ) {
            return null;
        }
        String nom = module.getNom();
        if ( nom == null ) {
            return null;
        }
        return nom;
    }

    private LocalDateTime entitySeanceDateDebut(Absence absence) {
        if ( absence == null ) {
            return null;
        }
        Seance seance = absence.getSeance();
        if ( seance == null ) {
            return null;
        }
        LocalDateTime dateDebut = seance.getDateDebut();
        if ( dateDebut == null ) {
            return null;
        }
        return dateDebut;
    }
}
