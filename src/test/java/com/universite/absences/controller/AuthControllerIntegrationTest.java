package com.universite.absences.controller;

import com.universite.absences.BaseIntegrationTest;
import com.universite.absences.dto.request.AuthRequest;
import com.universite.absences.entity.Admin;
import com.universite.absences.repository.UserRepository;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class AuthControllerIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void shouldLoginSuccessfully() {
        // Prepare data
        String email = "test-auth@test.com";
        String password = "password123";
        
        Admin admin = new Admin();
        admin.setEmail(email);
        admin.setNom("Admin");
        admin.setPrenom("Test");
        admin.setPassword(passwordEncoder.encode(password));
        userRepository.save(admin);

        AuthRequest request = AuthRequest.builder()
                .email(email)
                .password(password)
                .build();

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/api/auth/login")
                .then()
                .statusCode(200)
                .body("token", notNullValue());
    }
}
