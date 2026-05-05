package com.universite.absences.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "etudiants")
@Getter
@Setter
public class Etudiant extends User {

    @Column(unique = true, nullable = false)
    private String cne;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filiere_id")
    private Filiere filiere;

    @OneToMany(mappedBy = "etudiant")
    private List<Absence> absences;

    @OneToMany(mappedBy = "etudiant")
    private List<PresenceLog> presenceLogs;

    @Override
    public String getRole() {
        return "ETUDIANT";
    }
}
