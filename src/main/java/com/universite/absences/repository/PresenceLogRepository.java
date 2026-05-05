package com.universite.absences.repository;

import com.universite.absences.entity.PresenceLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PresenceLogRepository extends JpaRepository<PresenceLog, Long> {
}
