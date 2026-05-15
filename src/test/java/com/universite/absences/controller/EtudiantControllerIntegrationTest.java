package com.universite.absences.controller;

import com.universite.absences.BaseIntegrationTest;
import com.universite.absences.dto.request.DepartementRequest;
import com.universite.absences.dto.request.EtudiantRequest;
import com.universite.absences.dto.request.FiliereRequest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class EtudiantControllerIntegrationTest extends BaseIntegrationTest {

    @Test
    void shouldCreateEtudiant() {
        // 1. Create Departement
        DepartementRequest deptReq = new DepartementRequest();
        deptReq.setNom("Sciences");
        Integer deptId = given()
                .header("Authorization", getAuthHeader())
                .contentType(ContentType.JSON)
                .body(deptReq)
                .when()
                .post("/api/departements")
                .then()
                .statusCode(200)
                .extract().path("id");

        // 2. Create Filiere
        FiliereRequest filiereReq = new FiliereRequest();
        filiereReq.setNom("SMI");
        filiereReq.setDepartementId(deptId.longValue());
        Integer filiereId = given()
                .header("Authorization", getAuthHeader())
                .contentType(ContentType.JSON)
                .body(filiereReq)
                .when()
                .post("/api/filieres")
                .then()
                .statusCode(200)
                .extract().path("id");

        // 3. Create Etudiant
        EtudiantRequest etudiantReq = new EtudiantRequest();
        etudiantReq.setNom("Simo");
        etudiantReq.setPrenom("Test");
        etudiantReq.setEmail("simo@test.com");
        etudiantReq.setPassword("password123");
        etudiantReq.setCne("D123456");
        etudiantReq.setFiliereId(filiereId.longValue());

        given()
                .header("Authorization", getAuthHeader())
                .contentType(ContentType.JSON)
                .body(etudiantReq)
                .when()
                .post("/api/etudiants")
                .then()
                .statusCode(200)
                .body("nom", is("Simo"))
                .body("email", is("simo@test.com"));
    }
}
