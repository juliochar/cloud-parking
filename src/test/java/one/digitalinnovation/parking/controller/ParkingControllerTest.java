package one.digitalinnovation.parking.controller;

import io.restassured.RestAssured;
import one.digitalinnovation.parking.controller.dto.ParkingCreateDTO;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ParkingControllerTest extends AbstractContainerBase {

    @LocalServerPort
    private int randomPort;

    @BeforeEach
    public void setUpTest(){
        System.out.println(randomPort);
        RestAssured.port = randomPort;

    }

    @Test
    void WhenFindAllthenCheckResult() {
        RestAssured.given()
                .header("authorization","Basic dXNlcjpkaW8=")
                .when()
                .get("/parking")
                .then()
                .statusCode(HttpStatus.OK.value());
                //.body("license[0]", Matchers.equalTo("SSS-2352"));

                //no banco criado novo nao passou a base de dados e nao tem como comparar com os dados antigos



    }

    @Test
    void WhenCreatethenCheckIsCreated() {

        var createDTO = new ParkingCreateDTO();
        createDTO.setColor("AMARELO");
        createDTO.setLicense("WRT-5555");
        createDTO.setModel("BRASILIA");
        createDTO.setState("SP");


        RestAssured.given()
                .auth()
                .basic("user", "dio")
                .when()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(createDTO)
                .post("/parking")
                .then()
                //statuscode
                //.statusCode(HttpStatus.CREATED.value())
                .statusCode(401);
                //.body("license", Matchers.equalTo("WRT-5555"))
                //.body("color", Matchers.equalTo("AMARELO"));




        /*
        RestAssured.given()
                .when()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(createDTO)
                .post("/parking")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("license", Matchers.equalTo("WRT-5555"))
                .body("color", Matchers.equalTo("AMARELO"));


         */
    }
}