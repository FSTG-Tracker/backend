package com.universite.absences.util;

import com.universite.absences.entity.Etudiant;
import com.universite.absences.repository.EtudiantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Résout le nom renvoyé par le microservice IA vers un {@link Etudiant} en base.
 * Convention IA : dossier / student_name au format « Prénom Nom » (ex. « Mohammed Elomri »).
 */
@Component
@RequiredArgsConstructor
public class StudentNameMatcher {

    private final EtudiantRepository etudiantRepository;

    public Optional<Etudiant> resolve(String studentName) {
        if (studentName == null || studentName.isBlank() || "Unknown".equalsIgnoreCase(studentName.trim())) {
            return Optional.empty();
        }

        String normalized = studentName.trim();

        Optional<Etudiant> byCne = etudiantRepository.findByCneIgnoreCase(normalized);
        if (byCne.isPresent()) {
            return byCne;
        }

        Optional<Etudiant> byFullName = etudiantRepository.findByPrenomNomIgnoreCase(normalized);
        if (byFullName.isPresent()) {
            return byFullName;
        }

        String[] parts = normalized.split("\\s+");
        if (parts.length >= 2) {
            String prenom = String.join(" ", java.util.Arrays.copyOf(parts, parts.length - 1));
            String nom = parts[parts.length - 1];
            return etudiantRepository.findByPrenomIgnoreCaseAndNomIgnoreCase(prenom, nom);
        }

        return Optional.empty();
    }
}
