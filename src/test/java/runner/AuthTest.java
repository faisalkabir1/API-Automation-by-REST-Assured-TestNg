package runner;

import config.Setup;
import controller.AuthController;
import Model.UserModel;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AuthTest extends Setup {

    AuthController auth = new AuthController();
    public static String userEmail;
    public static String userId;
    public static String userToken;
    public static String adminToken;

    @Test(priority = 1)
    public void registerNewUser() {
        UserModel user = new UserModel("John", "Doe", "john" + System.currentTimeMillis() + "@gmail.com",
                "1234", "01700000000", "Dhaka", "Male", true);

        Response res = auth.register(user);
        Assert.assertEquals(res.statusCode(), 201);
        userEmail = res.jsonPath().get("email");
        userId = res.jsonPath().get("_id");

    }

    @Test(priority = 2)
    public void loginAsAdmin() {
        Response res = auth.adminLogin("admin@test.com", "admin123");
        Assert.assertEquals(res.statusCode(), 200);
        adminToken = res.jsonPath().get("token");
        System.out.println(adminToken);
    }

    @Test(priority = 3, dependsOnMethods = "registerNewUser")
    public void loginAsUser() {
        Response res = auth.userLogin(userEmail, "1234");
        Assert.assertEquals(res.statusCode(), 200);
        userToken = res.jsonPath().get("token");
        System.out.println(userToken);

    }
}
