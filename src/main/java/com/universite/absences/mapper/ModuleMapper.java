package com.universite.absences.mapper;

import com.universite.absences.dto.request.ModuleRequest;
import com.universite.absences.dto.response.ModuleResponse;
import com.universite.absences.entity.Module;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ModuleMapper {
    
    @Mapping(target = "filiere.id", source = "filiereId")
    Module toEntity(ModuleRequest request);
    
    @Mapping(target = "filiereId", source = "filiere.id")
    @Mapping(target = "filiereNom", source = "filiere.nom")
    ModuleResponse toResponse(Module entity);
}
