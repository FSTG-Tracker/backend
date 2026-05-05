package com.universite.absences.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SeanceRequest {
    @NotNull
    private Long moduleId;
    
    @NotNull
    private Long professeurId;
    
    @NotNull
    private Long salleId;
    
    @NotNull
    private LocalDateTime dateDebut;
    
    @NotNull
    private LocalDateTime dateFin;
    
    private Boolean active = true;
}
