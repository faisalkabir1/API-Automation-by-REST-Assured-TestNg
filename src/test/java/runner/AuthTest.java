package runner;

import com.github.javafaker.Faker;
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
    public void registerNewUser() throws IOException {
        Faker faker = new Faker();

        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String email = firstName.toLowerCase() + System.currentTimeMillis() + "@gmail.com";
        String phone = faker.phoneNumber().subscriberNumber(11);
        String address = faker.address().streetAddress();

        UserModel user = new UserModel(
                firstName,
                lastName,
                email,
                "1234",
                phone,
                address,
                "Male",
                true);

        Response res = auth.register(user);
        Assert.assertEquals(res.statusCode(), 201);

        userEmail = res.jsonPath().get("email");
        userId = res.jsonPath().get("_id");
        userToken = res.jsonPath().get("token");
        updateProperty("user_email", userEmail);
        updateProperty("userId", userId);
        updateProperty("userToken", userToken);
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
