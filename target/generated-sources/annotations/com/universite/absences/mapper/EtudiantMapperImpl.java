package com.universite.absences.mapper;

import com.universite.absences.dto.request.EtudiantRequest;
import com.universite.absences.dto.response.EtudiantResponse;
import com.universite.absences.entity.Etudiant;
import com.universite.absences.entity.Filiere;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-15T10:15:24+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.11 (Eclipse Adoptium)"
)
@Component
public class EtudiantMapperImpl implements EtudiantMapper {

    @Override
    public Etudiant toEntity(EtudiantRequest request) {
        if ( request == null ) {
            return null;
        }

        Etudiant etudiant = new Etudiant();

        etudiant.setFiliere( etudiantRequestToFiliere( request ) );
        etudiant.setNom( request.getNom() );
        etudiant.setPrenom( request.getPrenom() );
        etudiant.setEmail( request.getEmail() );
        etudiant.setPassword( request.getPassword() );
        etudiant.setCne( request.getCne() );

        return etudiant;
    }

    @Override
    public EtudiantResponse toResponse(Etudiant entity) {
        if ( entity == null ) {
            return null;
        }

        EtudiantResponse etudiantResponse = new EtudiantResponse();

        etudiantResponse.setFiliereId( entityFiliereId( entity ) );
        etudiantResponse.setFiliereNom( entityFiliereNom( entity ) );
        etudiantResponse.setId( entity.getId() );
        etudiantResponse.setNom( entity.getNom() );
        etudiantResponse.setPrenom( entity.getPrenom() );
        etudiantResponse.setEmail( entity.getEmail() );
        etudiantResponse.setCne( entity.getCne() );

        return etudiantResponse;
    }

    protected Filiere etudiantRequestToFiliere(EtudiantRequest etudiantRequest) {
        if ( etudiantRequest == null ) {
            return null;
        }

        Filiere filiere = new Filiere();

        filiere.setId( etudiantRequest.getFiliereId() );

        return filiere;
    }

    private Long entityFiliereId(Etudiant etudiant) {
        if ( etudiant == null ) {
            return null;
        }
        Filiere filiere = etudiant.getFiliere();
        if ( filiere == null ) {
            return null;
        }
        Long id = filiere.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String entityFiliereNom(Etudiant etudiant) {
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
}
