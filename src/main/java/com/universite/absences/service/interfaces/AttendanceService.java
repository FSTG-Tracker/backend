package com.universite.absences.service.interfaces;

import com.universite.absences.dto.response.AttendanceScanResponse;
import com.universite.absences.dto.response.ClassroomScanResponse;
import com.universite.absences.dto.response.SeanceAttendanceStatusResponse;
import org.springframework.web.multipart.MultipartFile;

/**
 * Orchestration présence : microservice IA → résolution étudiant → {@link PresenceService}.
 */
public interface AttendanceService {

    AttendanceScanResponse markAttendanceFromImage(Long seanceId, MultipartFile image);

    ClassroomScanResponse markAttendanceFromClassroomImage(Long seanceId, MultipartFile image);

    SeanceAttendanceStatusResponse getSeanceAttendanceStatus(Long seanceId);

    SeanceAttendanceStatusResponse getFiliereAttendanceStatusForSeance(Long filiereId, Long seanceId);
}
