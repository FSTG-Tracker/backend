package com.universite.absences.mapper;

import com.universite.absences.dto.request.AbsenceRequest;
import com.universite.absences.dto.response.AbsenceResponse;
import com.universite.absences.entity.Absence;
import com.universite.absences.entity.Etudiant;
import com.universite.absences.entity.Filiere;
import com.universite.absences.entity.Module;
import com.universite.absences.entity.Seance;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-15T10:15:24+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.11 (Eclipse Adoptium)"
)
@Component
public class AbsenceMapperImpl implements AbsenceMapper {

    @Override
    public Absence toEntity(AbsenceRequest request) {
        if ( request == null ) {
            return null;
        }

        Absence absence = new Absence();

        absence.setStatut( request.getStatut() );
        absence.setJustification( request.getJustification() );

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
        absenceResponse.setEtudiantCne( entityEtudiantCne( entity ) );
        absenceResponse.setSeanceId( entitySeanceId( entity ) );
        absenceResponse.setModuleNom( entitySeanceModuleNom( entity ) );
        absenceResponse.setFiliereNom( entityEtudiantFiliereNom( entity ) );
        absenceResponse.setDateSeance( entitySeanceDateDebut( entity ) );
        absenceResponse.setId( entity.getId() );
        absenceResponse.setStatut( entity.getStatut() );
        absenceResponse.setJustification( entity.getJustification() );

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

    private String entityEtudiantCne(Absence absence) {
        if ( absence == null ) {
            return null;
        }
        Etudiant etudiant = absence.getEtudiant();
        if ( etudiant == null ) {
            return null;
        }
        String cne = etudiant.getCne();
        if ( cne == null ) {
            return null;
        }
        return cne;
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

    private String entityEtudiantFiliereNom(Absence absence) {
        if ( absence == null ) {
            return null;
        }
        Etudiant etudiant = absence.getEtudiant();
        if ( etudiant == null ) {
            return null;
        }
        Filiere filiere = etudiant.getFiliere();
        if ( filiere == null ) {
            return null;
        }
        String nom = filiere.getNom();
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
