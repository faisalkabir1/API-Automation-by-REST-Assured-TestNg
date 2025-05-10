package controller;

import config.Setup;
import Model.UserModel;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class AuthController extends Setup {

    public Response register(UserModel user) {
        return given()
                .spec(reqSpec)
                .body(user)
                .when()
                .post("/api/auth/register");
    }

    public Response adminLogin(String email, String password) {
        return given()
                .spec(reqSpec)
                .body("{ \"email\": \"" + email + "\", \"password\": \"" + password + "\" }")
                .when()
                .post("/api/auth/login");
    }

    public Response userLogin(String email, String password) {
        return adminLogin(email, password);
    }
}