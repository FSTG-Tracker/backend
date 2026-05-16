package com.universite.absences.config;

import com.universite.absences.entity.*;
import com.universite.absences.entity.Module;
import com.universite.absences.entity.enums.StatutAbsence;
import com.universite.absences.entity.enums.TypeSalle;
import com.universite.absences.repository.*;
import lombok.RequiredArgsConstructor;
import com.universite.absences.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final DepartementRepository departementRepository;
    private final FiliereRepository filiereRepository;
    private final ModuleRepository moduleRepository;
    private final SalleRepository salleRepository;
    private final SeanceRepository seanceRepository;
    private final AbsenceRepository absenceRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() > 0) {
            System.out.println("🌱 Données déjà présentes, saut du seeding.");
            return;
        }

        System.out.println("🌱 Initialisation des données statiques...");

        // 1. Departements
        Departement mathInfo = new Departement(null, "Mathématiques et Informatique", "Département de Mathématiques et Informatique", "Pr. Alami", null, null);
        Departement physique = new Departement(null, "Physique", "Département de Physique", "Pr. Benani", null, null);
        departementRepository.saveAll(Arrays.asList(mathInfo, physique));

        // 2. Filieres
        Filiere smi = new Filiere(null, "SMI", mathInfo, null, null);
        Filiere sma = new Filiere(null, "SMA", mathInfo, null, null);
        Filiere smp = new Filiere(null, "SMP", physique, null, null);
        filiereRepository.saveAll(Arrays.asList(smi, sma, smp));

        // 3. Salles
        Salle amphi1 = new Salle(null, "Amphi 1", 200, TypeSalle.AMPHI, null);
        Salle salle10 = new Salle(null, "Salle 10", 40, TypeSalle.COURS, null);
        Salle labo1 = new Salle(null, "Labo Info 1", 30, TypeSalle.LABORATOIRE, null);
        salleRepository.saveAll(Arrays.asList(amphi1, salle10, labo1));

        // 4. Admin
        Admin admin = new Admin();
        admin.setNom("Admin");
        admin.setPrenom("Super");
        admin.setEmail("admin@uca.ma");
        admin.setPassword(passwordEncoder.encode("admin1234"));
        userRepository.save(admin);

        // 5. Professeurs
        Professeur prof1 = new Professeur();
        prof1.setNom("Tazi");
        prof1.setPrenom("Mohammed");
        prof1.setEmail("tazi@uca.ma");
        prof1.setPassword(passwordEncoder.encode("prof1234"));
        prof1.setDepartement(mathInfo);
        
        Professeur prof2 = new Professeur();
        prof2.setNom("Idrissi");
        prof2.setPrenom("Ahmed");
        prof2.setEmail("idrissi@uca.ma");
        prof2.setPassword(passwordEncoder.encode("prof1234"));
        prof2.setDepartement(mathInfo);
        userRepository.saveAll(Arrays.asList(prof1, prof2));

        // 6. Etudiants
        Etudiant e1 = new Etudiant();
        e1.setNom("Berrada");
        e1.setPrenom("Yassine");
        e1.setEmail("yassine.berrada@etu.uca.ma");
        e1.setPassword(passwordEncoder.encode("student1234"));
        e1.setCne("G123456789");
        e1.setFiliere(smi);

        Etudiant e2 = new Etudiant();
        e2.setNom("Mansouri");
        e2.setPrenom("Sanaa");
        e2.setEmail("sanaa.mansouri@etu.uca.ma");
        e2.setPassword(passwordEncoder.encode("student1234"));
        e2.setCne("G987654321");
        e2.setFiliere(smi);
        userRepository.saveAll(Arrays.asList(e1, e2));

        // 7. Modules
        Module java = new Module(null, "Programmation Java", smi);
        Module web = new Module(null, "Technologies Web", smi);
        Module analyse = new Module(null, "Analyse Mathématique", sma);
        moduleRepository.saveAll(Arrays.asList(java, web, analyse));

        // 8. Seances
        Seance s1 = new Seance(null, java, prof1, amphi1, LocalDateTime.now().minusHours(2), LocalDateTime.now().minusHours(1), true, null, null);
        Seance s2 = new Seance(null, web, prof1, labo1, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(1).plusHours(2), true, null, null);
        seanceRepository.saveAll(Arrays.asList(s1, s2));

        // 9. Absences
        Absence a1 = new Absence(null, e1, s1, StatutAbsence.ABSENT, null);
        Absence a2 = new Absence(null, e2, s1, StatutAbsence.PRESENT, null);
        absenceRepository.saveAll(Arrays.asList(a1, a2));

        System.out.println("=================================================");
        System.out.println("✅ Données statiques initialisées avec succès !");
        System.out.println("Admin      : admin@uca.ma / admin1234");
        System.out.println("Professeur : tazi@uca.ma / prof1234");
        System.out.println("Étudiant   : yassine.berrada@etu.uca.ma / student1234");
        System.out.println("=================================================");
    }
}
