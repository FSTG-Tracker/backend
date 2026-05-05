package com.universite.absences.mapper;

import com.universite.absences.dto.request.DepartementRequest;
import com.universite.absences.dto.response.DepartementResponse;
import com.universite.absences.entity.Departement;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DepartementMapper {
    Departement toEntity(DepartementRequest request);
    DepartementResponse toResponse(Departement entity);
}
