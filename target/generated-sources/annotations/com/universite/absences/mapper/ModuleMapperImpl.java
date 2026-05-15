package com.universite.absences.mapper;

import com.universite.absences.dto.request.ModuleRequest;
import com.universite.absences.dto.response.ModuleResponse;
import com.universite.absences.entity.Filiere;
import com.universite.absences.entity.Module;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-15T10:15:24+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.11 (Eclipse Adoptium)"
)
@Component
public class ModuleMapperImpl implements ModuleMapper {

    @Override
    public Module toEntity(ModuleRequest request) {
        if ( request == null ) {
            return null;
        }

        Module module = new Module();

        module.setFiliere( moduleRequestToFiliere( request ) );
        module.setNom( request.getNom() );

        return module;
    }

    @Override
    public ModuleResponse toResponse(Module entity) {
        if ( entity == null ) {
            return null;
        }

        ModuleResponse moduleResponse = new ModuleResponse();

        moduleResponse.setFiliereId( entityFiliereId( entity ) );
        moduleResponse.setFiliereNom( entityFiliereNom( entity ) );
        moduleResponse.setId( entity.getId() );
        moduleResponse.setNom( entity.getNom() );

        return moduleResponse;
    }

    protected Filiere moduleRequestToFiliere(ModuleRequest moduleRequest) {
        if ( moduleRequest == null ) {
            return null;
        }

        Filiere filiere = new Filiere();

        filiere.setId( moduleRequest.getFiliereId() );

        return filiere;
    }

    private Long entityFiliereId(Module module) {
        if ( module == null ) {
            return null;
        }
        Filiere filiere = module.getFiliere();
        if ( filiere == null ) {
            return null;
        }
        Long id = filiere.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String entityFiliereNom(Module module) {
        if ( module == null ) {
            return null;
        }
        Filiere filiere = module.getFiliere();
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
