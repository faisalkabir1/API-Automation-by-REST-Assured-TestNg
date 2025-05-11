package runner;

import com.github.javafaker.Faker;
import config.Setup;
import controller.UserController;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class UserTest extends Setup {

    UserController userController = new UserController();
    Properties props = new Properties();

    @Test(priority = 1)
    public void getUserList() throws IOException {
        loadProps();
        String token = props.getProperty("adminToken");

        Response res = userController.getUserList(token);
        Assert.assertEquals(res.statusCode(), 200);
    }

    @Test(priority = 2)
    public void searchUserById() throws IOException {
        loadProps();
        String token = props.getProperty("adminToken");
        String userId = props.getProperty("userId");

        Response res = userController.getUserById(userId, token);
        Assert.assertEquals(res.statusCode(), 200);
    }
    @Test(priority = 3)
    public void searchUserByInvalidId() throws IOException {
        loadProps();
        String token = props.getProperty("adminToken");
        String userId = props.getProperty("invalidId1234");

        Response res = userController.getUserById(userId, token);
        Assert.assertEquals(res.statusCode(), 404);
        String message = res.jsonPath().getString("message");
        Assert.assertTrue(message.contains("User not found"), "Unexpected error message");
    }
    @Test(priority = 3)
    public void editUserInfo() throws IOException {
        loadProps();
        String token = props.getProperty("adminToken");
        String userId = props.getProperty("userId");
        Faker faker = new Faker();

        String updateBody = "{\n" +
                "  \"firstName\": \"" + faker.name().firstName() + "\",\n" +
                "  \"lastName\": \"" + faker.name().lastName() + "\",\n" +
                "  \"phoneNumber\": \"" + faker.phoneNumber().subscriberNumber(11) + "\",\n" +
                "  \"address\": \"" + faker.address().fullAddress() + "\"\n" +
                "}";
        Response res = userController.updateUser(userId, token, updateBody);
        Assert.assertEquals(res.statusCode(), 200);
    }

    private void loadProps() throws IOException {
        FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
        props.load(fis);
        fis.close();
    }
}
