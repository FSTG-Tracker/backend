package com.universite.absences.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DepartementRequest {
    @NotBlank
    private String nom;
    private String description;
    private String chefDepartement;
}
