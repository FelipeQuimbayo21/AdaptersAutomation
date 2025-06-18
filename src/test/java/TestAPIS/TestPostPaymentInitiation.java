package TestAPIS;
import BaseTest.BaseTest;
import io.qameta.allure.*;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import Utils.FileStore;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static io.restassured.RestAssured.given;
@Feature("API Testing")
public class TestPostPaymentInitiation {
    @Test
    @Feature("POST Payment")
    public void testPostPayment() throws IOException {
        BaseTest config = new BaseTest();
        config.setUp();
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName("Verificar un pago exitosamente"));
        int bankId = 888;
        String endpoint = "cert/api/paymentInitiation/" + bankId;
        String jsonBodyTemplate = new String(Files.readAllBytes(Paths.get("src/test/resources/json/postpayment.json")));
        String uuid = UUID.randomUUID().toString();
        double randomAmount = ThreadLocalRandom.current().nextDouble(1000.00, 20000.00);
        String amountFormatted = String.format(Locale.US, "%.2f", randomAmount);
        String jsonBody = jsonBodyTemplate
                .replace("{{$randomUUID}}", uuid)
                .replace("{{$randomAmount}}", amountFormatted);
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
