package com.universite.absences.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EtudiantRequest {
    @NotBlank
    private String nom;
    @NotBlank
    private String prenom;
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String cne;
    private Long filiereId;
}
