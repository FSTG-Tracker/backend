package com.universite.absences.mapper;

import com.universite.absences.dto.request.ProfesseurRequest;
import com.universite.absences.dto.response.ProfesseurResponse;
import com.universite.absences.entity.Departement;
import com.universite.absences.entity.Professeur;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-15T10:15:24+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.11 (Eclipse Adoptium)"
)
@Component
public class ProfesseurMapperImpl implements ProfesseurMapper {

    @Override
    public Professeur toEntity(ProfesseurRequest request) {
        if ( request == null ) {
            return null;
        }

        Professeur professeur = new Professeur();

        professeur.setNom( request.getNom() );
        professeur.setPrenom( request.getPrenom() );
        professeur.setEmail( request.getEmail() );
        professeur.setPassword( request.getPassword() );

        return professeur;
    }

    @Override
    public ProfesseurResponse toResponse(Professeur entity) {
        if ( entity == null ) {
            return null;
        }

        ProfesseurResponse professeurResponse = new ProfesseurResponse();

        professeurResponse.setDepartementId( entityDepartementId( entity ) );
        professeurResponse.setDepartementNom( entityDepartementNom( entity ) );
        professeurResponse.setId( entity.getId() );
        professeurResponse.setNom( entity.getNom() );
        professeurResponse.setPrenom( entity.getPrenom() );
        professeurResponse.setEmail( entity.getEmail() );

        return professeurResponse;
    }

    private Long entityDepartementId(Professeur professeur) {
        if ( professeur == null ) {
            return null;
        }
        Departement departement = professeur.getDepartement();
        if ( departement == null ) {
            return null;
        }
        Long id = departement.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String entityDepartementNom(Professeur professeur) {
        if ( professeur == null ) {
            return null;
        }
        Departement departement = professeur.getDepartement();
        if ( departement == null ) {
            return null;
        }
        String nom = departement.getNom();
        if ( nom == null ) {
            return null;
        }
        return nom;
    }
}
