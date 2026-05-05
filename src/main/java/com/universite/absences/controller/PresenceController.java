package com.universite.absences.controller;

import com.universite.absences.dto.request.PresenceIARequest;
import com.universite.absences.dto.response.PresenceIAResponse;
import com.universite.absences.service.interfaces.PresenceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/presence")
@RequiredArgsConstructor
public class PresenceController {

    private final PresenceService presenceService;

    @PostMapping
    public ResponseEntity<PresenceIAResponse> enregistrerPresenceIA(
            @Valid @RequestBody PresenceIARequest request) {
        return ResponseEntity.ok(presenceService.enregistrerPresenceIA(request));
    }

    @PostMapping(value = "/scanner", consumes = org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<com.universite.absences.dto.response.AiRecognitionResponse> scannerClasse(
            @RequestParam("seanceId") Long seanceId,
            @RequestParam("image") org.springframework.web.multipart.MultipartFile image,
            @org.springframework.beans.factory.annotation.Autowired com.universite.absences.service.interfaces.AiIntegrationService aiService) {
        
        // 1. Envoyer à l'IA
        com.universite.absences.dto.response.AiRecognitionResponse aiResponse = aiService.recognizeFaces(seanceId, image);
        
        // 2. Pour chaque étudiant reconnu, marquer présent via le service métier
        if (aiResponse.getDetections() != null) {
            for (com.universite.absences.dto.response.AiDetectionResult detection : aiResponse.getDetections()) {
                if ("RECOGNIZED".equals(detection.getStatus()) && detection.getEtudiant_id() != null) {
                    PresenceIARequest presenceRequest = new PresenceIARequest();
                    presenceRequest.setSeanceId(seanceId);
                    presenceRequest.setEtudiantId(detection.getEtudiant_id());
                    presenceRequest.setConfidenceScore(detection.getConfidence());
                    presenceRequest.setTimestamp(java.time.LocalDateTime.now());
                    
                    presenceService.enregistrerPresenceIA(presenceRequest);
                }
            }
        }
        
        return ResponseEntity.ok(aiResponse);
    }
}
