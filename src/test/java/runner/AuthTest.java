package runner;

import config.Setup;
import controller.AuthController;
import Model.UserModel;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

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
    public void loginAsAdmin() throws IOException {
        Response res = auth.adminLogin("admin@test.com", "admin123");
        Assert.assertEquals(res.statusCode(), 200);
        adminToken = res.jsonPath().get("token");
     //   System.out.println(adminToken);
        updateProperty("adminToken", adminToken);
    }

    @Test(priority = 3, dependsOnMethods = "registerNewUser")
    public void loginAsUser() throws IOException {
        Response res = auth.userLogin(userEmail, "1234");
        Assert.assertEquals(res.statusCode(), 200);
        userToken = res.jsonPath().get("token");
      //  System.out.println(userToken);
        updateProperty("adminToken", adminToken);

    }
    private void updateProperty(String key, String value) throws IOException {
        Properties props = new Properties();
        FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
        props.load(fis);
        fis.close();

        props.setProperty(key, value);
        FileOutputStream fos = new FileOutputStream("src/test/resources/config.properties");
        props.store(fos, null);
        fos.close();
    }
}
