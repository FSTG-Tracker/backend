package com.universite.absences.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "filieres")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Filiere {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departement_id", nullable = false)
    private Departement departement;

    @OneToMany(mappedBy = "filiere")
    private List<Etudiant> etudiants;

    @OneToMany(mappedBy = "filiere")
    private List<Module> modules;
}
