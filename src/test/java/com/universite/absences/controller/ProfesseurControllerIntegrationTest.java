package com.universite.absences.controller;

import com.universite.absences.BaseIntegrationTest;
import com.universite.absences.dto.request.DepartementRequest;
import com.universite.absences.dto.request.ProfesseurRequest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class ProfesseurControllerIntegrationTest extends BaseIntegrationTest {

    @Test
    void shouldCreateProfesseur() {
        // 1. Create Departement
        DepartementRequest deptReq = new DepartementRequest();
        deptReq.setNom("Informatique");
        Integer deptId = given()
                .header("Authorization", getAuthHeader())
                .contentType(ContentType.JSON)
                .body(deptReq)
                .when()
                .post("/api/departements")
                .then()
                .statusCode(200)
                .extract().path("id");

        // 2. Create Professeur
        ProfesseurRequest profReq = new ProfesseurRequest();
        profReq.setNom("Martin");
        profReq.setPrenom("Jean");
        profReq.setEmail("martin@test.com");
        profReq.setPassword("password123");
        profReq.setDepartementId(deptId.longValue());

        given()
                .header("Authorization", getAuthHeader())
                .contentType(ContentType.JSON)
                .body(profReq)
                .when()
                .post("/api/professeurs")
                .then()
                .statusCode(200)
                .body("nom", is("Martin"))
                .body("email", is("martin@test.com"));
    }
}
