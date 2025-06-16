package TestAPIS;
import BaseTest.BaseTest;
import io.qameta.allure.*;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utils.FileStore;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import static io.restassured.RestAssured.given;
@Feature("API Testing")
public class TestPostPaymentInitiation {
    @Test
    @Story("POST Payment Status")
    public void testPostPayment() throws IOException {
        BaseTest config = new BaseTest();
        config.setUp();
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName("Verificar un pago exitosamente"));
        int bankId = 51;
        String endpoint = "cert/api/paymentInitiation/" + bankId;
        String jsonBody = new String(Files.readAllBytes(Paths.get("src/test/resources/json/postpayment.json")));
        Response response = given()
                .filter(new AllureRestAssured())
                .header("x-fapi-interaction-id", "e82bc935-644f-4c7e-9273-86d41059cef7")
                .header("Content-Type", "application/json")
                .header("Host", "vj4mk6pz0m.execute-api.us-east-1.amazonaws.com")
                .body(jsonBody)
                .when()
                .post(endpoint);
        response.then().log().all().assertThat().statusCode(201);
        String domesticPayment = response.jsonPath().getString("Data.DomesticPaymentId");
        FileStore.saveConsentId(domesticPayment);
    }
}
