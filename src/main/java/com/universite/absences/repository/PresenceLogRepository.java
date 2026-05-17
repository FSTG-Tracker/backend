package com.universite.absences.repository;

import com.universite.absences.entity.PresenceLog;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PresenceLogRepository extends JpaRepository<PresenceLog, Long> {

    @EntityGraph(attributePaths = "etudiant")
    List<PresenceLog> findBySeanceIdOrderByTimestampDesc(Long seanceId);
}
