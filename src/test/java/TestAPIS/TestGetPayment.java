package TestAPIS;
import BaseTest.BaseTest;
import io.qameta.allure.*;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import Utils.FileStore;

import java.util.UUID;

import static io.restassured.RestAssured.given;

@Feature("Api Testing")
public class TestGetPayment {
    String domesticPayment = FileStore.readConsentId();

    @Test
    @Feature("Get Payment")
    public void testGetPayment() {
        BaseTest config = new BaseTest();
        config.setUp();
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName("Verificar consulta de pago exitosamente"));
        int bankId = 888;
        String endpoint = "/cert/api/paymentInitiation/" + bankId + "/" + domesticPayment;
        String uuid = UUID.randomUUID().toString();
        Response response = given()
                .filter(new AllureRestAssured())
                .header("Host", "vj4mk6pz0m.execute-api.us-east-1.amazonaws.com")
                .header("Content-Type", "application/json")
                .header("x-fapi-interaction-id", uuid)
                .when()
                .get(endpoint);
        response.then().log().all().assertThat().statusCode(200);
    }
}
