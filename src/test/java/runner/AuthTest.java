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
    Properties props = new Properties();
    @Test(priority = 1, description = "Register new user with valid data")
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
        String mail = res.jsonPath().get("email");
        updateProperty("user_email", userEmail);
        updateProperty("userId", userId);
        updateProperty("userToken", userToken);
    }

    @Test(priority = 2, description = "Try to register new user with previous email")
    public void registerExistingMail() throws IOException {
        Faker faker = new Faker();
        loadProps();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String Oldemail = props.getProperty("user_email");
        String phone = faker.phoneNumber().subscriberNumber(11);
        String address = faker.address().streetAddress();

        UserModel user = new UserModel(
                firstName,
                lastName,
                Oldemail,
                "1234",
                phone,
                address,
                "Male",
                true);

        Response res = auth.register(user);
      //  Assert.assertEquals(res.statusCode(), 400);
        String message = res.jsonPath().getString("message");
        Assert.assertTrue(message.contains("User already exists with this email address"),
                "Expected error message not found. Actual: " + message);

    }

    @Test(priority = 3, description = "Login as Admin with valid data")
    public void loginAsAdmin() throws IOException {
        Response res = auth.adminLogin("admin@test.com", "admin123");
        Assert.assertEquals(res.statusCode(), 200);
        adminToken = res.jsonPath().get("token");
     //   System.out.println(adminToken);
        updateProperty("adminToken", adminToken);
    }
    @Test(priority = 4, description = "Try Login as Admin with invalid credential data")
    public void loginAsInvalidAdmin() throws IOException {
        Response res = auth.adminLogin("admininvalid@test.com", "admin123");

        // Assert the status code is 401 for unauthorized login
        Assert.assertEquals(res.statusCode(), 401, "Expected 401 Unauthorized for invalid admin login");

        // Extract and assert error message
        String message = res.jsonPath().getString("message");
        Assert.assertTrue(message.contains("Invalid email or password"), "Unexpected error message");

    }

    @Test(priority = 5, dependsOnMethods = "registerNewUser", description = "Login as new user with valid data")
    public void loginAsUser() throws IOException {
        Response res = auth.userLogin(userEmail, "1234");
        Assert.assertEquals(res.statusCode(), 200);

    }
    @Test(priority = 6, description = "Login as User with invalid data")
    public void loginAsUserWithWrongCred() throws IOException {
        Response res = auth.userLogin("invalidemail123@gmail.com", "invalidpass123");

        Assert.assertEquals(res.statusCode(), 401, "Expected 401 Unauthorized for invalid admin login");

        String message = res.jsonPath().getString("message");
        Assert.assertTrue(message.contains("Invalid email or password"), "Unexpected error message");

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
    private void loadProps() throws IOException {
        FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
        props.load(fis);
        fis.close();
    }
}
