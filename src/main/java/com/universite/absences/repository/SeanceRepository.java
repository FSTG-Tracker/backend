package com.universite.absences.repository;
import com.universite.absences.entity.Seance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SeanceRepository extends JpaRepository<Seance, Long> {

    @EntityGraph(attributePaths = "module")
    Optional<Seance> findFirstByModule_Nom(String nom);

    @EntityGraph(attributePaths = {"module", "module.filiere"})
    Optional<Seance> findWithModuleAndFiliereById(Long id);

    @Override
    @EntityGraph(attributePaths = {"module", "professeur", "salle"})
    Page<Seance> findAll(Pageable pageable);
}
