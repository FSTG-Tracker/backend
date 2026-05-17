package com.universite.absences.dto.external;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FaceRecognizeBatchResponse {

    @JsonProperty("faces_detected")
    private int facesDetected;

    @Builder.Default
    private List<FaceDetectionDto> detections = new ArrayList<>();
}
