package com.universite.absences.mapper;

import com.universite.absences.dto.request.DepartementRequest;
import com.universite.absences.dto.response.DepartementResponse;
import com.universite.absences.entity.Departement;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-05T22:33:43+0100",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.46.0.v20260407-0427, environment: Java 21.0.10 (Eclipse Adoptium)"
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

        return departementResponse;
    }
}
