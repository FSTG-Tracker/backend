package com.universite.absences.service.impl;

import com.universite.absences.client.FaceRecognitionClient;
import com.universite.absences.dto.external.FaceRecognizeBatchResponse;
import com.universite.absences.dto.external.FaceRecognizeResponse;
import com.universite.absences.service.interfaces.AiIntegrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class AiIntegrationServiceImpl implements AiIntegrationService {

    private final FaceRecognitionClient faceRecognitionClient;

    @Override
    public boolean checkHealth() {
        return faceRecognitionClient.isHealthy();
    }

    @Override
    public FaceRecognizeResponse recognizeFace(MultipartFile image) {
        if (image == null || image.isEmpty()) {
            throw new IllegalArgumentException("L'image est obligatoire");
        }
        log.debug("Appel reconnaissance faciale, taille={} octets", image.getSize());
        return faceRecognitionClient.recognize(image);
    }

    @Override
    public FaceRecognizeBatchResponse recognizeFacesInClassroom(MultipartFile image) {
        if (image == null || image.isEmpty()) {
            throw new IllegalArgumentException("L'image est obligatoire");
        }
        log.debug("Appel reconnaissance faciale batch, taille={} octets", image.getSize());
        return faceRecognitionClient.recognizeBatch(image);
    }
}
