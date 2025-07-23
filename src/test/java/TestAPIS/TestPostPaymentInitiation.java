package TestAPIS;

import baseTest.BaseTest;
import io.qameta.allure.*;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static io.restassured.RestAssured.given;

public class TestPostPaymentInitiation {
    private final int bankId = 888;

    @Test
    @Feature("POST Payment")
    public void paymentContextBillPayment() throws IOException{
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName("Enviar una solicitud POST con valor EcommerceGoods en el campo PaymentContextCode"));
        BaseTest config = new BaseTest();
        config.setUp();
        String endpoint = "cert/api/paymentInitiation/"+bankId;
        String uuid = UUID.randomUUID().toString();
        double amountRandom = ThreadLocalRandom.current().nextDouble(10000, 20000);
        String formatAmount = String.format(Locale.US, "%.2f", amountRandom);
        String jsonTemplate = new String(Files.readAllBytes(Paths.get("src/test/resources/json/postpayment.json")));
        String json = jsonTemplate
                .replace("{{$randomUUID}}", uuid)
                .replace("{{$randomAmount}}", formatAmount)
                .replace("BillPayment","EcommerceGoods");
        Response response = given()
                .filter(new AllureRestAssured())
                .header("Host","vj4mk6pz0m.execute-api.us-east-1.amazonaws.com")
                .header("Content-Type","application/json")
                .header("x-fapi-interaction-id",uuid)
                .body(json)
                .post(endpoint);
        response.then().log().all().assertThat().statusCode(201);
    }
    @Test
    @Feature("POST Payment")
    public void isMissingBankId() throws IOException{
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName("Enviar una solicitud POST cuando falta el bankId"));
        BaseTest config = new BaseTest();
        config.setUp();
        String endpoint = "cert/api/paymentInitiation/";
        String uuid = UUID.randomUUID().toString();
        double amountRandom = ThreadLocalRandom.current().nextDouble(10000, 20000);
        String formatAmount = String.format(Locale.US, "%.2f", amountRandom);
        String jsonTemplate = new String(Files.readAllBytes(Paths.get("src/test/resources/json/postpayment.json")));
        String json = jsonTemplate
                .replace("{{$randomUUID}}",uuid)
                .replace("{{$randomAmount}}",formatAmount);
        Response response = given()
                .filter(new AllureRestAssured())
                .header("Host","vj4mk6pz0m.execute-api.us-east-1.amazonaws.com")
                .header("Content-Type","application/json")
                .header("x-fapi-interaction-id",uuid)
                .body(json)
                .post(endpoint);
        response.then().log().all().assertThat().statusCode(403);
    }
    @Test
    @Feature("POST Payment")
    public void NoAvailableBalance() throws IOException{
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName("Enviar una solicitud POST sin saldo disponible."));
        BaseTest config = new BaseTest();
        config.setUp();
        String endpoint = "cert/api/paymentInitiation/"+bankId;
        String uuid = UUID.randomUUID().toString();
        double randomAmount = ThreadLocalRandom.current().nextDouble(20000,40000);
        String formatAmount = String.format(Locale.US, "%.2f", randomAmount);
        String jsonTemplate = new String(Files.readAllBytes(Paths.get("src/test/resources/json/postpayment.json")));
        String json = jsonTemplate
                .replace("{{$randomUUID}}",uuid)
                .replace("{{$randomAmount}}", formatAmount)
                .replace("214021007548984001","15489666")
                .replace("3048660","100225425");
        Response response = given()
                .filter(new AllureRestAssured())
                .header("x-fapi-interaction-id",uuid)
                .header("Host","vj4mk6pz0m.execute-api.us-east-1.amazonaws.com")
                .header("Content-Type","application/json")
                .body(json)
                .post(endpoint);
        response.then().log().all().assertThat().statusCode(201);
    }
    @Test
    @Feature("POST Payment")
    public void amountNegativeTransaction() throws IOException {
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName("Enviar una solicitud POST con un monto en negativo."));
        BaseTest config = new BaseTest();
        config.setUp();
        String uuid = UUID.randomUUID().toString();
        String endpoint = "cert/api/paymentInitiation/"+bankId;
        String jsonTemplate = new String(Files.readAllBytes(Paths.get("src/test/resources/json/postpayment.json")));
        String json = jsonTemplate
                .replace("{{$randomUUID}}",uuid)
                .replace("{{$randomAmount}}", "-50000");
        Response response = given()
                .filter(new AllureRestAssured())
                .header("Host","vj4mk6pz0m.execute-api.us-east-1.amazonaws.com")
                .header("Content-Type","application/json")
                .header("x-fapi-interaction-id",uuid)
                .body(json)
                .post(endpoint);
        response.then().log().all().assertThat().statusCode(400);
    }
}