package com.example.order;

import com.example.order.stubs.InventoryClientStub;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.testcontainers.containers.MySQLContainer;

import static org.hamcrest.MatcherAssert.assertThat;


@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
class OrderServiceApplicationTests {

    @Autowired
    private MySQLContainer<?> mySQLContainer;

    @LocalServerPort
    private Integer port;

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    void shouldPlaceOrder() {
        var submitOrderJson = """
                {
                	"skuCode": "iphone_15",
                	"price": 1000,
                	"quantity": 1
                }
                """;

        InventoryClientStub.stubInventoryCall("iphone_15", 1);

        var responseBodyString = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(submitOrderJson)
                .when()
                .post("/api/order")
                .then()
                .log()
                .all()
                .statusCode(201)
                .extract()
                .body()
                .asString();


        assertThat(responseBodyString, Matchers.is("Order placed successfully"));
    }

}
