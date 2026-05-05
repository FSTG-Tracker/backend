package com.universite.absences.mapper;

import com.universite.absences.dto.request.SalleRequest;
import com.universite.absences.dto.response.SalleResponse;
import com.universite.absences.entity.Salle;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SalleMapper {
    Salle toEntity(SalleRequest request);
    SalleResponse toResponse(Salle entity);
}
