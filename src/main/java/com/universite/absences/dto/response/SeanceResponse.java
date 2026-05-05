package com.universite.absences.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SeanceResponse {
    private Long id;
    private Long moduleId;
    private String moduleNom;
    private Long professeurId;
    private String professeurNom;
    private Long salleId;
    private String salleNom;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    private Boolean active;
}
