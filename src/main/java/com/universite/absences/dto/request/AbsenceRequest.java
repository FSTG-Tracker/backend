package com.universite.absences.dto.request;

import com.universite.absences.entity.enums.StatutAbsence;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AbsenceRequest {
    @NotNull
    private Long etudiantId;
    @NotNull
    private Long seanceId;
    @NotNull
    private StatutAbsence statut;
    private String justification;
}
