package com.universite.absences.repository;

import com.universite.absences.entity.Professeur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfesseurRepository extends JpaRepository<Professeur, Long> {
    boolean existsByEmail(String email);
}
