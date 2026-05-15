package com.universite.absences.controller;

import com.universite.absences.BaseIntegrationTest;
import com.universite.absences.dto.request.SalleRequest;
import com.universite.absences.entity.enums.TypeSalle;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class SalleControllerIntegrationTest extends BaseIntegrationTest {

    @Test
    void shouldCreateSalle() {
        SalleRequest request = new SalleRequest();
        request.setNom("Salle 101");
        request.setCapacite(40);
        request.setType(TypeSalle.TP);

        given()
                .header("Authorization", getAuthHeader())
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/api/salles")
                .then()
                .statusCode(200)
                .body("nom", is("Salle 101"));
    }
}
