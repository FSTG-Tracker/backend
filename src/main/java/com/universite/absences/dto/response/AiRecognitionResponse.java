package com.universite.absences.dto.response;

import lombok.Data;
import java.util.List;

@Data
public class AiRecognitionResponse {
    private Long seance_id;
    private List<AiDetectionResult> detections;
    private double duration_ms;
    private String status;
}
