package com.universite.absences.dto.response;

import lombok.Data;

@Data
public class FiliereResponse {
    private Long id;
    private String nom;
    private Long departementId;
    private String departementNom;
}
