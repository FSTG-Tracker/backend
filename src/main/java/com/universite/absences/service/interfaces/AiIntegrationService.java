package com.universite.absences.service.interfaces;

import com.universite.absences.dto.external.FaceRecognizeBatchResponse;
import com.universite.absences.dto.external.FaceRecognizeResponse;
import org.springframework.web.multipart.MultipartFile;

/**
 * Façade applicative vers le microservice de reconnaissance faciale (FastAPI).
 */
public interface AiIntegrationService {

    boolean checkHealth();

    FaceRecognizeResponse recognizeFace(MultipartFile image);

    FaceRecognizeBatchResponse recognizeFacesInClassroom(MultipartFile image);
}
