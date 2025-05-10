package config;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Setup {

    public static RequestSpecification reqSpec;
    public static String baseUrl;

    @BeforeClass
    public void setupConfig() throws IOException {
        Properties props = new Properties();
        FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
        props.load(fis);

        baseUrl = props.getProperty("baseUrl");

        reqSpec = new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .setContentType("application/json")
                .build();

        RestAssured.baseURI = baseUrl;
    }
}
