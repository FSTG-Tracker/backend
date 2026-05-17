package com.universite.absences.client;

import com.universite.absences.config.AiServiceProperties;
import com.universite.absences.dto.external.FaceRecognizeBatchResponse;
import com.universite.absences.dto.external.FaceRecognizeResponse;
import com.universite.absences.exception.AiServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.util.retry.Retry;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class FaceRecognitionClient {

    private final WebClient faceRecognitionWebClient;
    private final AiServiceProperties properties;

    public boolean isHealthy() {
        try {
            Map<?, ?> body = faceRecognitionWebClient.get()
                    .uri("/health")
                    .retrieve()
                    .bodyToMono(Map.class)
                    .retryWhen(retrySpec())
                    .block(properties.getReadTimeout());
            return body != null && "ok".equalsIgnoreCase(String.valueOf(body.get("status")));
        } catch (Exception ex) {
            log.warn("Face recognition service health check failed: {}", ex.getMessage());
            return false;
        }
    }

    public FaceRecognizeBatchResponse recognizeBatch(MultipartFile image) {
        try {
            FaceRecognizeBatchResponse response = faceRecognitionWebClient.post()
                    .uri("/recognize/batch")
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(BodyInserters.fromMultipartData(buildMultipartBody(image)))
                    .retrieve()
                    .bodyToMono(FaceRecognizeBatchResponse.class)
                    .retryWhen(retrySpec())
                    .block(properties.getReadTimeout());

            if (response == null) {
                throw new AiServiceException("Réponse vide du service de reconnaissance faciale (batch)");
            }
            return response;
        } catch (WebClientResponseException ex) {
            throw mapHttpError(ex);
        } catch (WebClientRequestException ex) {
            throw new AiServiceException("Service de reconnaissance faciale injoignable", ex);
        } catch (IOException ex) {
            throw new AiServiceException("Impossible de lire l'image envoyée", ex);
        } catch (AiServiceException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new AiServiceException("Échec de l'appel batch au service de reconnaissance faciale", ex);
        }
    }

    public FaceRecognizeResponse recognize(MultipartFile image) {
        try {
            FaceRecognizeResponse response = faceRecognitionWebClient.post()
                    .uri("/recognize")
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(BodyInserters.fromMultipartData(buildMultipartBody(image)))
                    .retrieve()
                    .bodyToMono(FaceRecognizeResponse.class)
                    .retryWhen(retrySpec())
                    .block(properties.getReadTimeout());

            if (response == null) {
                throw new AiServiceException("Réponse vide du service de reconnaissance faciale");
            }
            return response;
        } catch (WebClientResponseException ex) {
            throw mapHttpError(ex);
        } catch (WebClientRequestException ex) {
            throw new AiServiceException("Service de reconnaissance faciale injoignable", ex);
        } catch (IOException ex) {
            throw new AiServiceException("Impossible de lire l'image envoyée", ex);
        } catch (AiServiceException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new AiServiceException("Échec de l'appel au service de reconnaissance faciale", ex);
        }
    }

    private org.springframework.util.MultiValueMap<String, org.springframework.http.HttpEntity<?>> buildMultipartBody(
            MultipartFile image) throws IOException {
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("image", toResource(image))
                .filename(resolveFilename(image))
                .contentType(MediaType.parseMediaType(
                        image.getContentType() != null ? image.getContentType() : MediaType.IMAGE_JPEG_VALUE));
        return builder.build();
    }

    private ByteArrayResource toResource(MultipartFile file) throws IOException {
        return new ByteArrayResource(file.getBytes()) {
            @Override
            public String getFilename() {
                return resolveFilename(file);
            }
        };
    }

    private String resolveFilename(MultipartFile file) {
        return file.getOriginalFilename() != null ? file.getOriginalFilename() : "capture.jpg";
    }

    private Retry retrySpec() {
        return Retry.backoff(properties.getMaxRetries(), properties.getRetryDelay())
                .maxBackoff(properties.getRetryDelay().multipliedBy(4))
                .filter(this::isRetryable)
                .doBeforeRetry(signal -> log.warn(
                        "Nouvelle tentative d'appel au service IA ({}/{}): {}",
                        signal.totalRetries() + 1,
                        properties.getMaxRetries(),
                        signal.failure().getMessage()));
    }

    private boolean isRetryable(Throwable throwable) {
        if (throwable instanceof WebClientRequestException) {
            return true;
        }
        if (throwable instanceof WebClientResponseException responseEx) {
            return responseEx.getStatusCode().is5xxServerError()
                    || responseEx.getStatusCode() == HttpStatus.REQUEST_TIMEOUT
                    || responseEx.getStatusCode() == HttpStatus.TOO_MANY_REQUESTS;
        }
        return false;
    }

    private AiServiceException mapHttpError(WebClientResponseException ex) {
        String detail = ex.getResponseBodyAsString();
        log.error("Face recognition API error {}: {}", ex.getStatusCode(), detail);
        return switch (ex.getStatusCode().value()) {
            case 404 -> new AiServiceException("Aucun visage détecté dans l'image");
            case 400 -> new AiServiceException("Image invalide ou requête incorrecte");
            default -> new AiServiceException(
                    "Le service de reconnaissance faciale a renvoyé une erreur: " + ex.getStatusCode());
        };
    }
}
