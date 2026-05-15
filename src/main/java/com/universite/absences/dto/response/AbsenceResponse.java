package com.universite.absences.dto.response;

import com.universite.absences.entity.enums.StatutAbsence;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AbsenceResponse {
    private Long id;
    private Long etudiantId;
    private String etudiantNom;
    private String etudiantPrenom;
    private String etudiantCne;
    private Long seanceId;
    private String moduleNom;
    private String filiereNom;
    private LocalDateTime dateSeance;
    private StatutAbsence statut;
    private String justification;
}
