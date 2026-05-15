package com.universite.absences.mapper;

import com.universite.absences.dto.request.SalleRequest;
import com.universite.absences.dto.response.SalleResponse;
import com.universite.absences.entity.Salle;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-15T10:15:24+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.11 (Eclipse Adoptium)"
)
@Component
public class SalleMapperImpl implements SalleMapper {

    @Override
    public Salle toEntity(SalleRequest request) {
        if ( request == null ) {
            return null;
        }

        Salle salle = new Salle();

        salle.setNom( request.getNom() );
        salle.setCapacite( request.getCapacite() );
        salle.setType( request.getType() );

        return salle;
    }

    @Override
    public SalleResponse toResponse(Salle entity) {
        if ( entity == null ) {
            return null;
        }

        SalleResponse salleResponse = new SalleResponse();

        salleResponse.setId( entity.getId() );
        salleResponse.setNom( entity.getNom() );
        salleResponse.setCapacite( entity.getCapacite() );
        salleResponse.setType( entity.getType() );

        return salleResponse;
    }
}
