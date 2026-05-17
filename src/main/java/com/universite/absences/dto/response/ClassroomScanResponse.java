package com.universite.absences.dto.response;

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
public class ClassroomScanResponse {

    private Long seanceId;
    private Long filiereId;
    private String filiereNom;
    private int totalStudentsInClass;
    private int facesDetected;
    private int recognizedCount;
    private int markedPresentCount;
    private int alreadyPresentCount;

    @Builder.Default
    private List<ClassroomScanResultItem> results = new ArrayList<>();

    private String message;
}
