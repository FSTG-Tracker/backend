package com.universite.absences.repository;
import com.universite.absences.entity.Seance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeanceRepository extends JpaRepository<Seance, Long> {
    
    @Override
    @EntityGraph(attributePaths = {"module", "professeur", "salle"})
    Page<Seance> findAll(Pageable pageable);
}
