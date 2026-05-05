package com.universite.absences.dto.response;

import lombok.Data;
import java.util.List;

@Data
public class AiDetectionResult {
    private Long etudiant_id;
    private double confidence;
    private List<Integer> bbox;
    private String status;
}
