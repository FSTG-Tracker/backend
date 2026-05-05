package com.universite.absences.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FiliereRequest {
    @NotBlank
    private String nom;
    
    @NotNull
    private Long departementId;
}
