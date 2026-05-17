package com.universite.absences.dto.response;

import com.universite.absences.dto.external.FaceRecognizeResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceScanResponse {

    private FaceRecognizeResponse recognition;
    private PresenceIAResponse presence;
    private boolean attendanceMarked;
    private String message;
}
