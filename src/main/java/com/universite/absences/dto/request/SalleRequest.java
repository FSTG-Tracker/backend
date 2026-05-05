package com.universite.absences.dto.request;

import com.universite.absences.entity.enums.TypeSalle;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SalleRequest {
    @NotBlank
    private String nom;
    
    @NotNull
    private Integer capacite;
    
    @NotNull
    private TypeSalle type;
}
