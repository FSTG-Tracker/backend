package com.universite.absences.mapper;

import com.universite.absences.dto.request.FiliereRequest;
import com.universite.absences.dto.response.FiliereResponse;
import com.universite.absences.entity.Departement;
import com.universite.absences.entity.Filiere;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-15T10:15:24+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.11 (Eclipse Adoptium)"
)
@Component
public class FiliereMapperImpl implements FiliereMapper {

    @Override
    public Filiere toEntity(FiliereRequest request) {
        if ( request == null ) {
            return null;
        }

        Filiere filiere = new Filiere();

        filiere.setDepartement( filiereRequestToDepartement( request ) );
        filiere.setNom( request.getNom() );

        return filiere;
    }

    @Override
    public FiliereResponse toResponse(Filiere entity) {
        if ( entity == null ) {
            return null;
        }

        FiliereResponse filiereResponse = new FiliereResponse();

        filiereResponse.setDepartementId( entityDepartementId( entity ) );
        filiereResponse.setDepartementNom( entityDepartementNom( entity ) );
        filiereResponse.setId( entity.getId() );
        filiereResponse.setNom( entity.getNom() );

        return filiereResponse;
    }

    protected Departement filiereRequestToDepartement(FiliereRequest filiereRequest) {
        if ( filiereRequest == null ) {
            return null;
        }

        Departement departement = new Departement();

        departement.setId( filiereRequest.getDepartementId() );

        return departement;
    }

    private Long entityDepartementId(Filiere filiere) {
        if ( filiere == null ) {
            return null;
        }
        Departement departement = filiere.getDepartement();
        if ( departement == null ) {
            return null;
        }
        Long id = departement.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String entityDepartementNom(Filiere filiere) {
        if ( filiere == null ) {
            return null;
        }
        Departement departement = filiere.getDepartement();
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
