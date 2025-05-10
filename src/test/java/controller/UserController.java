package controller;

import config.Setup;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserController extends Setup {

    public Response getUserList(String token) {
        return given()
                .spec(reqSpec)
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/api/user/users");
    }
    public Response getUserById(String userId, String token) {
        return given()
                .spec(reqSpec)
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/api/user/" + userId);
    }
    public Response updateUser(String userId, String token, String updatedBodyJson) {
        return given()
                .spec(reqSpec)
                .header("Authorization", "Bearer " + token)
                .body(updatedBodyJson)
                .when()
                .put("/api/user/" + userId);
    }
}
