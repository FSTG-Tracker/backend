package com.universite.absences;

import com.universite.absences.entity.Admin;
import com.universite.absences.security.JwtService;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public abstract class BaseIntegrationTest {

    @LocalServerPort
    protected int port;

    @Autowired
    protected JwtService jwtService;

    @Autowired
    protected com.universite.absences.repository.UserRepository userRepository;

    @Autowired
    protected org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    protected String adminToken;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
        
        String adminEmail = "admin-global@test.com";
        if (userRepository.findByEmail(adminEmail).isEmpty()) {
            Admin admin = new Admin();
            admin.setEmail(adminEmail);
            admin.setNom("Admin");
            admin.setPrenom("Global");
            admin.setPassword(passwordEncoder.encode("password"));
            userRepository.save(admin);
            adminToken = jwtService.generateToken(admin);
        } else {
            adminToken = jwtService.generateToken(userRepository.findByEmail(adminEmail).get());
        }
    }

    protected String getAuthHeader() {
        return "Bearer " + adminToken;
    }
}
