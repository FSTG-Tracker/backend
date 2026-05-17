package com.universite.absences.dto.external;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FaceDetectionDto {

    @JsonProperty("student_name")
    private String studentName;

    private double similarity;

    private boolean recognized;
}
