package com.universite.absences.dto.response;

import com.universite.absences.entity.enums.StatutAbsence;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassroomScanResultItem {

    private Long etudiantId;
    private String nom;
    private String prenom;
    private String studentNameFromAi;
    private double similarity;
    private boolean recognized;
    private boolean attendanceMarked;
    private StatutAbsence statut;
    private String message;
}
