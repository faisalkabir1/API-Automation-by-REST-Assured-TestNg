package runner;

import config.Setup;
import controller.CostController;
import Model.CostModel;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.*;
import java.util.Properties;

public class CostTest extends Setup {

    CostController costController = new CostController();
    Properties props = new Properties();
    String costId;

    @Test(priority = 1)
    public void addItem() throws IOException {
        loadProps();
        String token = props.getProperty("userToken");

        Faker faker = new Faker();
        CostModel item = new CostModel(
                faker.commerce().productName(),
                faker.number().numberBetween(1, 5),
                String.valueOf(faker.number().numberBetween(100, 999)),
                "2025-05-07",
                "May",
                faker.lorem().word()
        );

        Response res = costController.addItem(item, token);
        Assert.assertEquals(res.statusCode(), 201);

        costId = res.jsonPath().get("_id");
        updateProperty("costId", costId);
    }

    @Test(priority = 2)
    public void getItems() throws IOException {
        loadProps();
        String token = props.getProperty("userToken");

        Response res = costController.getItems(token);
        Assert.assertEquals(res.statusCode(), 200);
    }

    @Test(priority = 3)
    public void editItem() throws IOException {
        loadProps();
        String token = props.getProperty("userToken");
        String costId = props.getProperty("costId");

        Faker faker = new Faker();
        String updateBody = "{\n" +
                "  \"itemName\": \"" + faker.commerce().productName() + "\"\n" +
                "}";

        Response res = costController.updateItem(costId, token, updateBody);
        Assert.assertEquals(res.statusCode(), 200);
    }

    @Test(priority = 4)
    public void deleteItem() throws IOException {
        loadProps();
        String token = props.getProperty("userToken");
        String costId = props.getProperty("costId");

        Response res = costController.deleteItem(costId, token);
        Assert.assertEquals(res.statusCode(), 200);
    }

    private void loadProps() throws IOException {
        FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
        props.load(fis);
        fis.close();
    }

    private void updateProperty(String key, String value) throws IOException {
        FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
        props.load(fis);
        fis.close();

        props.setProperty(key, value);
        FileOutputStream fos = new FileOutputStream("src/test/resources/config.properties");
        props.store(fos, null);
        fos.close();
    }
}
