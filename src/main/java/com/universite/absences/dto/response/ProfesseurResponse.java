package com.universite.absences.dto.response;

import lombok.Data;

@Data
public class ProfesseurResponse {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private Long departementId;
    private String departementNom;
}
