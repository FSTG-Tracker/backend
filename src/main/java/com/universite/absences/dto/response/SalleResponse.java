package com.universite.absences.dto.response;

import com.universite.absences.entity.enums.TypeSalle;
import lombok.Data;

@Data
public class SalleResponse {
    private Long id;
    private String nom;
    private Integer capacite;
    private TypeSalle type;
}
