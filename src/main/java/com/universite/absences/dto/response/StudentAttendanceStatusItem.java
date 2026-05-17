package com.universite.absences.dto.response;

import com.universite.absences.entity.enums.StatutAbsence;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentAttendanceStatusItem {

    private Long etudiantId;
    private String nom;
    private String prenom;
    private String cne;
    private StatutAbsence statut;
    private boolean scannedByAi;
    private LocalDateTime lastScanAt;
    private Double lastConfidenceScore;
}
