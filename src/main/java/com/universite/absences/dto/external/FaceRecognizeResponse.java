package com.universite.absences.dto.external;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO aligné sur la réponse JSON du microservice FastAPI POST /recognize.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FaceRecognizeResponse {

    @JsonProperty("student_name")
    private String studentName;

    private double similarity;

    private boolean recognized;
}
