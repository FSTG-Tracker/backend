package com.universite.absences.controller;

import com.universite.absences.BaseIntegrationTest;
import com.universite.absences.dto.request.DepartementRequest;
import com.universite.absences.dto.request.FiliereRequest;
import com.universite.absences.dto.request.ModuleRequest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class ModuleControllerIntegrationTest extends BaseIntegrationTest {

    @Test
    void shouldCreateModule() {
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

        // 3. Create Module
        ModuleRequest moduleReq = new ModuleRequest();
        moduleReq.setNom("Java");
        moduleReq.setFiliereId(filiereId.longValue());

        given()
                .header("Authorization", getAuthHeader())
                .contentType(ContentType.JSON)
                .body(moduleReq)
                .when()
                .post("/api/modules")
                .then()
                .statusCode(200)
                .body("nom", is("Java"));
    }
}
