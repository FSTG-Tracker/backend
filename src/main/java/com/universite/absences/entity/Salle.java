package com.universite.absences.entity;

import com.universite.absences.entity.enums.TypeSalle;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "salles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Salle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nom;

    @Column(nullable = false)
    private Integer capacite;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeSalle type;

    @OneToMany(mappedBy = "salle")
    private List<Seance> seances;
}
