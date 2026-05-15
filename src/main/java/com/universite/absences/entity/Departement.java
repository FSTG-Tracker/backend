package com.universite.absences.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "departements")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Departement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nom;

    @Column
    private String description;

    @Column(name = "chef_departement")
    private String chefDepartement;

    @OneToMany(mappedBy = "departement")
    private List<Filiere> filieres;

    @OneToMany(mappedBy = "departement")
    private List<Professeur> professeurs;
}
