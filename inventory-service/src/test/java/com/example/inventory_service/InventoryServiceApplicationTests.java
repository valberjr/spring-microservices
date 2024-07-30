package com.example.inventory_service;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MySQLContainer;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class InventoryServiceApplicationTests {

    @LocalServerPort
    private Integer port;

    @Autowired
    private MySQLContainer<?> mySQLContainer;

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    void shouldReturnTrueWhenItemIsInStock() {
        var response = RestAssured.given()
                .when()
                .get("/api/inventory?skuCode=iphone_15&quantity=100")
                .then()
                .log().all()
                .statusCode(200)
                .extract().response().as(Boolean.class);

        assertTrue(response);
    }

    @Test
    void shouldReturnFalseWhenItemIsOutOfStock() {
        var response = RestAssured.given()
                .when()
                .get("/api/inventory?skuCode=iphone_15&quantity=1001")
                .then()
                .log().all()
                .statusCode(200)
                .extract().response().as(Boolean.class);

        assertFalse(response);
    }

}
