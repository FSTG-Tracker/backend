package com.universite.absences.dto.response;

import lombok.Data;

@Data
public class EtudiantResponse {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String cne;
}
