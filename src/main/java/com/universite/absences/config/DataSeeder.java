package com.universite.absences.config;

import com.universite.absences.entity.Admin;
import com.universite.absences.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByEmail("admin@uca.ma").isEmpty()) {
            Admin admin = new Admin();
            admin.setNom("Admin");
            admin.setPrenom("Super");
            admin.setEmail("admin@uca.ma");
            admin.setPassword(passwordEncoder.encode("admin1234"));
            
            userRepository.save(admin);
            
            System.out.println("=================================================");
            System.out.println("✅ Compte administrateur par défaut créé !");
            System.out.println("Email        : admin@uca.ma");
            System.out.println("Mot de passe : admin1234");
            System.out.println("=================================================");
        }
    }
}
