package com.universite.absences.dto.response;

import lombok.Data;

@Data
public class AiEnrollResponse {
    private Long etudiant_id;
    private boolean enrolled;
    private Long embedding_id;
}
