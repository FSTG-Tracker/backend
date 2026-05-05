package com.universite.absences.mapper;

import com.universite.absences.dto.request.EtudiantRequest;
import com.universite.absences.dto.response.EtudiantResponse;
import com.universite.absences.entity.Etudiant;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-05T22:33:43+0100",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.46.0.v20260407-0427, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class EtudiantMapperImpl implements EtudiantMapper {

    @Override
    public Etudiant toEntity(EtudiantRequest request) {
        if ( request == null ) {
            return null;
        }

        Etudiant etudiant = new Etudiant();

        etudiant.setEmail( request.getEmail() );
        etudiant.setNom( request.getNom() );
        etudiant.setPassword( request.getPassword() );
        etudiant.setPrenom( request.getPrenom() );
        etudiant.setCne( request.getCne() );

        return etudiant;
    }

    @Override
    public EtudiantResponse toResponse(Etudiant entity) {
        if ( entity == null ) {
            return null;
        }

        EtudiantResponse etudiantResponse = new EtudiantResponse();

        etudiantResponse.setCne( entity.getCne() );
        etudiantResponse.setEmail( entity.getEmail() );
        etudiantResponse.setId( entity.getId() );
        etudiantResponse.setNom( entity.getNom() );
        etudiantResponse.setPrenom( entity.getPrenom() );

        return etudiantResponse;
    }
}
