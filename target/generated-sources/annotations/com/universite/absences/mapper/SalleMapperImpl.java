package com.universite.absences.mapper;

import com.universite.absences.dto.request.SalleRequest;
import com.universite.absences.dto.response.SalleResponse;
import com.universite.absences.entity.Salle;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-05T22:33:43+0100",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.46.0.v20260407-0427, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class SalleMapperImpl implements SalleMapper {

    @Override
    public Salle toEntity(SalleRequest request) {
        if ( request == null ) {
            return null;
        }

        Salle salle = new Salle();

        salle.setCapacite( request.getCapacite() );
        salle.setNom( request.getNom() );
        salle.setType( request.getType() );

        return salle;
    }

    @Override
    public SalleResponse toResponse(Salle entity) {
        if ( entity == null ) {
            return null;
        }

        SalleResponse salleResponse = new SalleResponse();

        salleResponse.setCapacite( entity.getCapacite() );
        salleResponse.setId( entity.getId() );
        salleResponse.setNom( entity.getNom() );
        salleResponse.setType( entity.getType() );

        return salleResponse;
    }
}
