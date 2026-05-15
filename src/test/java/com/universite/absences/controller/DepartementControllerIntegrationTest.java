package com.universite.absences.controller;

import com.universite.absences.BaseIntegrationTest;
import com.universite.absences.dto.request.DepartementRequest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class DepartementControllerIntegrationTest extends BaseIntegrationTest {

    @Test
    void shouldCreateAndRetrieveDepartement() {
        DepartementRequest request = new DepartementRequest();
        request.setNom("Informatique");
        request.setDescription("Département d'informatique");
        request.setChefDepartement("M. Dupont");

        // Create
        Integer id = given()
                .header("Authorization", getAuthHeader())
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/api/departements")
                .then()
                .statusCode(200)
                .body("nom", is("Informatique"))
                .extract().path("id");

        // Get by ID
        given()
                .header("Authorization", getAuthHeader())
                .when()
                .get("/api/departements/" + id)
                .then()
                .statusCode(200)
                .body("nom", is("Informatique"));

        // Get all
        given()
                .header("Authorization", getAuthHeader())
                .when()
                .get("/api/departements")
                .then()
                .statusCode(200)
                .body("content", hasSize(greaterThanOrEqualTo(1)));
    }
}
