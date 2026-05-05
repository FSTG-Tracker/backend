package com.universite.absences.dto.response;

import lombok.Data;

@Data
public class ModuleResponse {
    private Long id;
    private String nom;
    private Long filiereId;
    private String filiereNom;
}
