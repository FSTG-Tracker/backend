package com.universite.absences.repository;

import com.universite.absences.entity.Etudiant;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {

    List<Etudiant> findByFiliereId(Long filiereId);

    Optional<Etudiant> findByCneIgnoreCase(String cne);

    Optional<Etudiant> findByPrenomIgnoreCaseAndNomIgnoreCase(String prenom, String nom);

    @Query("SELECT e FROM Etudiant e WHERE LOWER(CONCAT(e.prenom, ' ', e.nom)) = LOWER(:fullName)")
    Optional<Etudiant> findByPrenomNomIgnoreCase(@Param("fullName") String fullName);

    @EntityGraph(attributePaths = "filiere")
    List<Etudiant> findByFiliereIdOrderByNomAscPrenomAsc(Long filiereId);

    boolean existsByIdAndFiliereId(Long id, Long filiereId);
}
