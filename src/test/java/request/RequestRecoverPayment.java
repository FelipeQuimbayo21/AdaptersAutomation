package request;

import baseTest.BaseTest;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeMethod;
import utils.FileStore;

import java.util.UUID;

public class RequestRecoverPayment extends BaseTest {
    String uuid = UUID.randomUUID().toString();
    protected RequestSpecification request;
    protected Response response;

    @BeforeMethod
    public void config() {
        request = RestAssured.given().spec(requestSpec());
    }
    public RequestSpecification requestSpec() {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addHeader("Host","vj4mk6pz0m.execute-api.us-east-1.amazonaws.com")
                .addHeader("x-fapi-interaction-id",uuid)
                .build();
    }
    public RequestSpecification requestSpecXFapi() {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addHeader("Host","vj4mk6pz0m.execute-api.us-east-1.amazonaws.com")
                .build();
    }
}
