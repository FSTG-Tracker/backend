package com.universite.absences.dto.response;

import com.universite.absences.entity.enums.StatutAbsence;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PresenceIAResponse {
    private Long logId;
    private StatutAbsence statut;
    private String message;
}
