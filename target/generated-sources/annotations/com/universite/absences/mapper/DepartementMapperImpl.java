package com.universite.absences.mapper;

import com.universite.absences.dto.request.DepartementRequest;
import com.universite.absences.dto.response.DepartementResponse;
import com.universite.absences.entity.Departement;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-15T10:15:24+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.11 (Eclipse Adoptium)"
)
@Component
public class DepartementMapperImpl implements DepartementMapper {

    @Override
    public Departement toEntity(DepartementRequest request) {
        if ( request == null ) {
            return null;
        }

        Departement departement = new Departement();

        departement.setNom( request.getNom() );
        departement.setDescription( request.getDescription() );
        departement.setChefDepartement( request.getChefDepartement() );

        return departement;
    }

    @Override
    public DepartementResponse toResponse(Departement entity) {
        if ( entity == null ) {
            return null;
        }

        DepartementResponse departementResponse = new DepartementResponse();

        departementResponse.setId( entity.getId() );
        departementResponse.setNom( entity.getNom() );
        departementResponse.setDescription( entity.getDescription() );
        departementResponse.setChefDepartement( entity.getChefDepartement() );

        return departementResponse;
    }
}
