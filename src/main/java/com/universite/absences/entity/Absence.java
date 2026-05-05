package com.universite.absences.entity;

import com.universite.absences.entity.enums.StatutAbsence;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "absences", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"etudiant_id", "seance_id"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Absence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "etudiant_id", nullable = false)
    private Etudiant etudiant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seance_id", nullable = false)
    private Seance seance;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutAbsence statut;

    private String justification;
}
