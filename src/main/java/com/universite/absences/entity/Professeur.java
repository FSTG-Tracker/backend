package com.universite.absences.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "professeurs")
@Getter
@Setter
public class Professeur extends User {
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departement_id")
    private Departement departement;

    @OneToMany(mappedBy = "professeur")
    private List<Seance> seances;

    @Override
    public String getRole() {
        return "PROFESSEUR";
    }
}
