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
public class SeanceAttendanceStatusResponse {

    private Long seanceId;
    private String moduleNom;
    private Long filiereId;
    private String filiereNom;
    private int totalStudents;
    private int presentCount;
    private int absentCount;
    private int justifieCount;

    @Builder.Default
    private List<StudentAttendanceStatusItem> students = new ArrayList<>();
}
