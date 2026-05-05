package com.universite.absences.service.interfaces;

import com.universite.absences.dto.response.AiEnrollResponse;
import com.universite.absences.dto.response.AiRecognitionResponse;
import org.springframework.web.multipart.MultipartFile;

public interface AiIntegrationService {
    AiEnrollResponse enrollStudentFace(Long etudiantId, MultipartFile image);
    AiRecognitionResponse recognizeFaces(Long seanceId, MultipartFile image);
}
