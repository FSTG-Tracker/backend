package com.universite.absences.config;

import com.universite.absences.entity.*;
import com.universite.absences.entity.Module;
import com.universite.absences.entity.enums.StatutAbsence;
import com.universite.absences.entity.enums.TypeSalle;
import com.universite.absences.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    public static final String MOHAMMED_ELOMRI_EMAIL = "mohammed.elomri@etu.uca.ma";
    public static final String IA_DEMO_MODULE_NAME = "Démo présence IA";

    private final UserRepository userRepository;
    private final DepartementRepository departementRepository;
    private final FiliereRepository filiereRepository;
    private final ModuleRepository moduleRepository;
    private final SalleRepository salleRepository;
    private final SeanceRepository seanceRepository;
    private final AbsenceRepository absenceRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) {
        boolean freshDatabase = userRepository.count() == 0;
        if (freshDatabase) {
            seedAll();
        } else {
            refreshFaceRecognitionTestData();
        }
    }

    private void seedAll() {
        System.out.println("🌱 Initialisation des données statiques...");

        Departement mathInfo = new Departement(null, "Mathématiques et Informatique",
                "Département de Mathématiques et Informatique", "Pr. Alami", null, null);
        Departement physique = new Departement(null, "Physique", "Département de Physique", "Pr. Benani", null, null);
        departementRepository.saveAll(Arrays.asList(mathInfo, physique));

        Filiere smi = new Filiere(null, "SMI", mathInfo, null, null);
        Filiere sma = new Filiere(null, "SMA", mathInfo, null, null);
        Filiere smp = new Filiere(null, "SMP", physique, null, null);
        filiereRepository.saveAll(Arrays.asList(smi, sma, smp));

        Salle amphi1 = new Salle(null, "Amphi 1", 200, TypeSalle.AMPHI, null);
        Salle salle10 = new Salle(null, "Salle 10", 40, TypeSalle.COURS, null);
        Salle labo1 = new Salle(null, "Labo Info 1", 30, TypeSalle.LABORATOIRE, null);
        salleRepository.saveAll(Arrays.asList(amphi1, salle10, labo1));

        Admin admin = new Admin();
        admin.setNom("Admin");
        admin.setPrenom("Super");
        admin.setEmail("admin@uca.ma");
        admin.setPassword(passwordEncoder.encode("admin1234"));
        userRepository.save(admin);

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

        Etudiant e1 = buildEtudiant("Berrada", "Yassine", "yassine.berrada@etu.uca.ma", "G123456789", smi);
        Etudiant e2 = buildEtudiant("Mansouri", "Sanaa", "sanaa.mansouri@etu.uca.ma", "G987654321", smi);
        Etudiant mohammed = buildEtudiant("Elomri", "Mohammed", MOHAMMED_ELOMRI_EMAIL, "G323456789", smi);
        userRepository.saveAll(Arrays.asList(e1, e2, mohammed));

        Module java = new Module(null, "Programmation Java", smi);
        Module web = new Module(null, "Technologies Web", smi);
        Module analyse = new Module(null, "Analyse Mathématique", sma);
        Module iaDemo = new Module(null, IA_DEMO_MODULE_NAME, smi);
        moduleRepository.saveAll(Arrays.asList(java, web, analyse, iaDemo));

        LocalDateTime now = LocalDateTime.now();
        Seance s1 = new Seance(null, java, prof1, amphi1, now.minusHours(1), now.plusHours(3), true, null, null);
        Seance s2 = new Seance(null, web, prof1, labo1, now.plusDays(1), now.plusDays(1).plusHours(2), true, null, null);
        Seance sIaDemo = new Seance(null, iaDemo, prof1, salle10, now.minusHours(1), now.plusHours(4), true, null, null);
        seanceRepository.saveAll(Arrays.asList(s1, s2, sIaDemo));

        absenceRepository.saveAll(Arrays.asList(
                new Absence(null, e1, s1, StatutAbsence.ABSENT, null),
                new Absence(null, e2, s1, StatutAbsence.PRESENT, null),
                new Absence(null, mohammed, sIaDemo, StatutAbsence.ABSENT, null)
        ));

        printCredentials(sIaDemo.getId(), mohammed.getId());
    }

    /**
     * À chaque démarrage : fenêtre horaire « maintenant » + séance dédiée au test IA.
     * Corrige le 422 « La séance n'est pas active à ce timestamp » après un ancien seed.
     */
    private void refreshFaceRecognitionTestData() {
        Etudiant mohammed = userRepository.findByEmail(MOHAMMED_ELOMRI_EMAIL)
                .filter(Etudiant.class::isInstance)
                .map(Etudiant.class::cast)
                .orElseGet(this::createMohammedIfPossible);

        if (mohammed == null) {
            System.out.println("⚠️  Mohammed Elomri introuvable — relancez avec une base vide ou créez l'étudiant manuellement.");
            return;
        }

        LocalDateTime now = LocalDateTime.now();
        Seance iaSeance = resolveIaTestSeance();
        if (iaSeance == null) {
            System.out.println("⚠️  Aucune séance en base — supprimez les tables ou lancez un premier seed complet.");
            return;
        }

        applyActiveWindow(iaSeance, now);
        // Rétrocompat : curl avec seanceId=1
        seanceRepository.findById(1L).ifPresent(s -> {
            if (!s.getId().equals(iaSeance.getId())) {
                applyActiveWindow(s, now);
            }
        });

        absenceRepository.findByEtudiantIdAndSeanceId(mohammed.getId(), iaSeance.getId())
                .orElseGet(() -> absenceRepository.save(
                        new Absence(null, mohammed, iaSeance, StatutAbsence.ABSENT, null)));

        printCredentials(iaSeance.getId(), mohammed.getId());
    }

    private Seance resolveIaTestSeance() {
        return seanceRepository.findFirstByModule_Nom(IA_DEMO_MODULE_NAME)
                .or(() -> seanceRepository.findById(1L))
                .or(() -> seanceRepository.findAll().stream().findFirst())
                .orElse(null);
    }

    private void applyActiveWindow(Seance seance, LocalDateTime now) {
        seance.setDateDebut(now.minusHours(1));
        seance.setDateFin(now.plusHours(4));
        seance.setActive(true);
        seanceRepository.save(seance);
    }

    private Etudiant createMohammedIfPossible() {
        if (filiereRepository.count() == 0) {
            return null;
        }
        Filiere smi = filiereRepository.findAll().stream()
                .filter(f -> "SMI".equals(f.getNom()))
                .findFirst()
                .orElse(filiereRepository.findAll().getFirst());

        Etudiant mohammed = buildEtudiant("Elomri", "Mohammed", MOHAMMED_ELOMRI_EMAIL, "G323456789", smi);
        return (Etudiant) userRepository.save(mohammed);
    }

    private Etudiant buildEtudiant(String nom, String prenom, String email, String cne, Filiere filiere) {
        Etudiant etudiant = new Etudiant();
        etudiant.setNom(nom);
        etudiant.setPrenom(prenom);
        etudiant.setEmail(email);
        etudiant.setPassword(passwordEncoder.encode("student1234"));
        etudiant.setCne(cne);
        etudiant.setFiliere(filiere);
        return etudiant;
    }

    private void printCredentials(Long seanceId, Long etudiantId) {
        System.out.println("=================================================");
        System.out.println("✅ Données de test présence IA");
        System.out.println("Admin      : admin@uca.ma / admin1234");
        System.out.println("Professeur : tazi@uca.ma / prof1234");
        System.out.println("Étudiant   : " + MOHAMMED_ELOMRI_EMAIL + " / student1234");
        System.out.println("Nom IA     : Mohammed Elomri  (aligné Face-Recognition)");
        System.out.println("Séance IA  : id=" + seanceId + "  (active maintenant → +4h)");
        System.out.println("Étudiant id: " + etudiantId);
        System.out.println("Test curl  :");
        System.out.println("  curl -X POST http://localhost:8086/api/attendance/scan \\");
        System.out.println("    -H \"Authorization: Bearer <token>\" \\");
        System.out.println("    -F \"seanceId=" + seanceId + "\" \\");
        System.out.println("    -F \"image=@backend/src/main/resources/static/images/photo.jpg\"");
        System.out.println("=================================================");
    }
}
