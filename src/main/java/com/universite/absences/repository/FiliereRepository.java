package com.universite.absences.repository;
import com.universite.absences.entity.Filiere;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FiliereRepository extends JpaRepository<Filiere, Long> {
    
    @Override
    @EntityGraph(attributePaths = {"departement"})
    Page<Filiere> findAll(Pageable pageable);
}
