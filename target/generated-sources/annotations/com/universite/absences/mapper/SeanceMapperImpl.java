package com.universite.absences.mapper;

import com.universite.absences.dto.request.SeanceRequest;
import com.universite.absences.dto.response.SeanceResponse;
import com.universite.absences.entity.Module;
import com.universite.absences.entity.Professeur;
import com.universite.absences.entity.Salle;
import com.universite.absences.entity.Seance;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-17T14:22:52+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.11 (Eclipse Adoptium)"
)
@Component
public class SeanceMapperImpl implements SeanceMapper {

    @Override
    public Seance toEntity(SeanceRequest request) {
        if ( request == null ) {
            return null;
        }

        Seance seance = new Seance();

        seance.setModule( seanceRequestToModule( request ) );
        seance.setProfesseur( seanceRequestToProfesseur( request ) );
        seance.setSalle( seanceRequestToSalle( request ) );
        seance.setDateDebut( request.getDateDebut() );
        seance.setDateFin( request.getDateFin() );
        seance.setActive( request.getActive() );

        return seance;
    }

    @Override
    public SeanceResponse toResponse(Seance entity) {
        if ( entity == null ) {
            return null;
        }

        SeanceResponse seanceResponse = new SeanceResponse();

        seanceResponse.setModuleId( entityModuleId( entity ) );
        seanceResponse.setModuleNom( entityModuleNom( entity ) );
        seanceResponse.setProfesseurId( entityProfesseurId( entity ) );
        seanceResponse.setProfesseurNom( entityProfesseurNom( entity ) );
        seanceResponse.setSalleId( entitySalleId( entity ) );
        seanceResponse.setSalleNom( entitySalleNom( entity ) );
        seanceResponse.setId( entity.getId() );
        seanceResponse.setDateDebut( entity.getDateDebut() );
        seanceResponse.setDateFin( entity.getDateFin() );
        seanceResponse.setActive( entity.getActive() );

        return seanceResponse;
    }

    protected Module seanceRequestToModule(SeanceRequest seanceRequest) {
        if ( seanceRequest == null ) {
            return null;
        }

        Module module = new Module();

        module.setId( seanceRequest.getModuleId() );

        return module;
    }

    protected Professeur seanceRequestToProfesseur(SeanceRequest seanceRequest) {
        if ( seanceRequest == null ) {
            return null;
        }

        Professeur professeur = new Professeur();

        professeur.setId( seanceRequest.getProfesseurId() );

        return professeur;
    }

    protected Salle seanceRequestToSalle(SeanceRequest seanceRequest) {
        if ( seanceRequest == null ) {
            return null;
        }

        Salle salle = new Salle();

        salle.setId( seanceRequest.getSalleId() );

        return salle;
    }

    private Long entityModuleId(Seance seance) {
        if ( seance == null ) {
            return null;
        }
        Module module = seance.getModule();
        if ( module == null ) {
            return null;
        }
        Long id = module.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String entityModuleNom(Seance seance) {
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

    private Long entityProfesseurId(Seance seance) {
        if ( seance == null ) {
            return null;
        }
        Professeur professeur = seance.getProfesseur();
        if ( professeur == null ) {
            return null;
        }
        Long id = professeur.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String entityProfesseurNom(Seance seance) {
        if ( seance == null ) {
            return null;
        }
        Professeur professeur = seance.getProfesseur();
        if ( professeur == null ) {
            return null;
        }
        String nom = professeur.getNom();
        if ( nom == null ) {
            return null;
        }
        return nom;
    }

    private Long entitySalleId(Seance seance) {
        if ( seance == null ) {
            return null;
        }
        Salle salle = seance.getSalle();
        if ( salle == null ) {
            return null;
        }
        Long id = salle.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String entitySalleNom(Seance seance) {
        if ( seance == null ) {
            return null;
        }
        Salle salle = seance.getSalle();
        if ( salle == null ) {
            return null;
        }
        String nom = salle.getNom();
        if ( nom == null ) {
            return null;
        }
        return nom;
    }
}
