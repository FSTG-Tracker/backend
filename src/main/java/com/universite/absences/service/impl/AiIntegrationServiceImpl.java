package com.universite.absences.service.impl;

import com.universite.absences.dto.response.AiEnrollResponse;
import com.universite.absences.dto.response.AiRecognitionResponse;
import com.universite.absences.service.interfaces.AiIntegrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AiIntegrationServiceImpl implements AiIntegrationService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${application.ai-service.url}")
    private String aiServiceUrl;

    @Value("${application.ai-service.api-key}")
    private String aiApiKey;

    @Override
    public AiEnrollResponse enrollStudentFace(Long etudiantId, MultipartFile image) {
        String url = aiServiceUrl + "/enroll";
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set("X-API-KEY", aiApiKey);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("etudiant_id", etudiantId);
        body.add("image", createMultipartResource(image));

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        log.info("Sending enrollment request to AI service for etudiant {}", etudiantId);
        ResponseEntity<AiEnrollResponse> response = restTemplate.postForEntity(url, requestEntity, AiEnrollResponse.class);
        
        return response.getBody();
    }

    @Override
    public AiRecognitionResponse recognizeFaces(Long seanceId, MultipartFile image) {
        String url = aiServiceUrl + "/recognize";
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set("X-API-KEY", aiApiKey);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("seance_id", seanceId);
        body.add("image", createMultipartResource(image));

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        log.info("Sending recognition request to AI service for seance {}", seanceId);
        ResponseEntity<AiRecognitionResponse> response = restTemplate.postForEntity(url, requestEntity, AiRecognitionResponse.class);
        
        return response.getBody();
    }

    private ByteArrayResource createMultipartResource(MultipartFile file) {
        try {
            return new ByteArrayResource(file.getBytes()) {
                @Override
                public String getFilename() {
                    return file.getOriginalFilename() != null ? file.getOriginalFilename() : "image.jpg";
                }
            };
        } catch (IOException e) {
            throw new RuntimeException("Failed to read multipart file", e);
        }
    }
}
