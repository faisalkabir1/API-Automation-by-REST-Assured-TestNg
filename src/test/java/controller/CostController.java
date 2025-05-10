package controller;

import Model.CostModel;
import config.Setup;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CostController extends Setup {

    public Response addItem(CostModel item, String token) {
        return given()
                .spec(reqSpec)
                .header("Authorization", "Bearer " + token)
                .body(item)
                .when()
                .post("/api/costs");
    }

    public Response getItems(String token) {
        return given()
                .spec(reqSpec)
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/api/costs");
    }

    public Response updateItem(String itemId, String token, String updateBodyJson) {
        return given()
                .spec(reqSpec)
                .header("Authorization", "Bearer " + token)
                .body(updateBodyJson)
                .when()
                .put("/api/costs/" + itemId);
    }

    public Response deleteItem(String itemId, String token) {
        return given()
                .spec(reqSpec)
                .header("Authorization", "Bearer " + token)
                .when()
                .delete("/api/costs/" + itemId);
    }
}
