package com.universite.absences.repository;
import com.universite.absences.entity.Absence;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AbsenceRepository extends JpaRepository<Absence, Long> {
    
    @Override
    @EntityGraph(attributePaths = {"etudiant", "seance", "seance.module"})
    Page<Absence> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"etudiant", "seance", "seance.module"})
    List<Absence> findByEtudiantId(Long etudiantId);

    @EntityGraph(attributePaths = {"etudiant", "seance", "seance.module"})
    List<Absence> findBySeanceId(Long seanceId);

    @EntityGraph(attributePaths = {"etudiant", "seance", "seance.module"})
    Optional<Absence> findByEtudiantIdAndSeanceId(Long etudiantId, Long seanceId);
}
