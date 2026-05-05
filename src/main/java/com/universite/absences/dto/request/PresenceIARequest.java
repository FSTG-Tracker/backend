package com.universite.absences.dto.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PresenceIARequest {

    @NotNull(message = "L'ID de l'étudiant est obligatoire")
    @Positive
    private Long etudiantId;

    @NotNull(message = "L'ID de la séance est obligatoire")
    @Positive
    private Long seanceId;

    @NotNull(message = "Le timestamp est obligatoire")
    private LocalDateTime timestamp;

    @NotNull(message = "Le score de confiance est obligatoire")
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "1.0")
    private Double confidenceScore;
}
