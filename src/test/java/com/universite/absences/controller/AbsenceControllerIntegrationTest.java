package com.universite.absences.controller;

import com.universite.absences.BaseIntegrationTest;
import com.universite.absences.dto.request.*;
import com.universite.absences.entity.enums.StatutAbsence;
import com.universite.absences.entity.enums.TypeSalle;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class AbsenceControllerIntegrationTest extends BaseIntegrationTest {

    @Test
    void shouldCreateAbsence() {
        // Setup dependencies
        DepartementRequest deptReq = new DepartementRequest();
        deptReq.setNom("Dept");
        Integer deptId = given().header("Authorization", getAuthHeader()).contentType(ContentType.JSON).body(deptReq).when().post("/api/departements").then().extract().path("id");

        FiliereRequest filReq = new FiliereRequest();
        filReq.setNom("Filiere");
        filReq.setDepartementId(deptId.longValue());
        Integer filId = given().header("Authorization", getAuthHeader()).contentType(ContentType.JSON).body(filReq).when().post("/api/filieres").then().extract().path("id");

        EtudiantRequest etudiantReq = new EtudiantRequest();
        etudiantReq.setNom("Simo");
        etudiantReq.setPrenom("T");
        etudiantReq.setEmail("etud@test.com");
        etudiantReq.setPassword("password123");
        etudiantReq.setCne("CNE1");
        etudiantReq.setFiliereId(filId.longValue());
        Integer etuId = given().header("Authorization", getAuthHeader()).contentType(ContentType.JSON).body(etudiantReq).when().post("/api/etudiants").then().extract().path("id");

        ModuleRequest modReq = new ModuleRequest();
        modReq.setNom("Mod");
        modReq.setFiliereId(filId.longValue());
        Integer modId = given().header("Authorization", getAuthHeader()).contentType(ContentType.JSON).body(modReq).when().post("/api/modules").then().extract().path("id");

        ProfesseurRequest profReq = new ProfesseurRequest();
        profReq.setNom("P");
        profReq.setPrenom("P");
        profReq.setEmail("prof2@test.com");
        profReq.setDepartementId(deptId.longValue());
        Integer profId = given().header("Authorization", getAuthHeader()).contentType(ContentType.JSON).body(profReq).when().post("/api/professeurs").then().extract().path("id");

        SalleRequest salleReq = new SalleRequest();
        salleReq.setNom("S1");
        salleReq.setCapacite(20);
        salleReq.setType(TypeSalle.TD);
        Integer salleId = given().header("Authorization", getAuthHeader()).contentType(ContentType.JSON).body(salleReq).when().post("/api/salles").then().extract().path("id");

        SeanceRequest seanceReq = new SeanceRequest();
        seanceReq.setModuleId(modId.longValue());
        seanceReq.setProfesseurId(profId.longValue());
        seanceReq.setSalleId(salleId.longValue());
        seanceReq.setDateDebut(LocalDateTime.now().plusDays(1));
        seanceReq.setDateFin(LocalDateTime.now().plusDays(1).plusHours(2));
        Integer seanceId = given().header("Authorization", getAuthHeader()).contentType(ContentType.JSON).body(seanceReq).when().post("/api/seances").then().extract().path("id");

        // Create Absence
        AbsenceRequest absenceReq = new AbsenceRequest();
        absenceReq.setEtudiantId(etuId.longValue());
        absenceReq.setSeanceId(seanceId.longValue());
        absenceReq.setStatut(StatutAbsence.ABSENT);

        given()
                .header("Authorization", getAuthHeader())
                .contentType(ContentType.JSON)
                .body(absenceReq)
                .when()
                .post("/api/absences")
                .then()
                .statusCode(200)
                .body("etudiantId", is(etuId));
    }
}
