package runner;

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

    private void loadProps() throws IOException {
        FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
        props.load(fis);
        fis.close();
    }
}
