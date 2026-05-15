package com.universite.absences.controller;

import com.universite.absences.BaseIntegrationTest;
import com.universite.absences.dto.request.*;
import com.universite.absences.entity.enums.TypeSalle;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class SeanceControllerIntegrationTest extends BaseIntegrationTest {

    @Test
    void shouldCreateSeance() {
        // 1. Create Departement
        DepartementRequest deptReq = new DepartementRequest();
        deptReq.setNom("Science Dept");
        Integer deptId = given().header("Authorization", getAuthHeader()).contentType(ContentType.JSON).body(deptReq).when().post("/api/departements").then().extract().path("id");

        // 2. Create Filiere
        FiliereRequest filReq = new FiliereRequest();
        filReq.setNom("Filiere 1");
        filReq.setDepartementId(deptId.longValue());
        Integer filId = given().header("Authorization", getAuthHeader()).contentType(ContentType.JSON).body(filReq).when().post("/api/filieres").then().extract().path("id");

        // 3. Create Module
        ModuleRequest modReq = new ModuleRequest();
        modReq.setNom("Java Module");
        modReq.setFiliereId(filId.longValue());
        Integer modId = given().header("Authorization", getAuthHeader()).contentType(ContentType.JSON).body(modReq).when().post("/api/modules").then().extract().path("id");

        // 4. Create Professeur
        ProfesseurRequest profReq = new ProfesseurRequest();
        profReq.setNom("Prof");
        profReq.setPrenom("X");
        profReq.setEmail("prof@test.com");
        profReq.setDepartementId(deptId.longValue());
        Integer profId = given().header("Authorization", getAuthHeader()).contentType(ContentType.JSON).body(profReq).when().post("/api/professeurs").then().extract().path("id");

        // 5. Create Salle
        SalleRequest salleReq = new SalleRequest();
        salleReq.setNom("Salle A");
        salleReq.setCapacite(30);
        salleReq.setType(TypeSalle.COURS);
        Integer salleId = given().header("Authorization", getAuthHeader()).contentType(ContentType.JSON).body(salleReq).when().post("/api/salles").then().extract().path("id");

        // 6. Create Seance
        SeanceRequest seanceReq = new SeanceRequest();
        seanceReq.setModuleId(modId.longValue());
        seanceReq.setProfesseurId(profId.longValue());
        seanceReq.setSalleId(salleId.longValue());
        seanceReq.setDateDebut(LocalDateTime.now().plusHours(1));
        seanceReq.setDateFin(LocalDateTime.now().plusHours(3));

        given()
                .header("Authorization", getAuthHeader())
                .contentType(ContentType.JSON)
                .body(seanceReq)
                .when()
                .post("/api/seances")
                .then()
                .statusCode(200);
    }
}
