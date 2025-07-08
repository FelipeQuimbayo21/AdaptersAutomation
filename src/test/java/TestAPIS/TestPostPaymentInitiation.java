package TestAPIS;

import BaseTest.BaseTest;
import io.qameta.allure.*;
import io.qameta.allure.model.TestResult;
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
    private final int bankId = 888;
    @Test
    @Feature("POST Payment")
    public void testPostPayment() throws IOException {
        BaseTest config = new BaseTest();
        config.setUp();
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName("Verificar un pago exitosamente"));
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

    @Test
    @Feature("POST Payment")
    public void isMissingXInteractionId() throws IOException {
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName("Verificar respuesta cuando falta el x-fapi-interaction-id"));
        BaseTest config = new BaseTest();
        config.setUp();
        String endpoint = "cert/api/paymentInitiation/" + bankId;
        String jsonBodyTemplate = new String(Files.readAllBytes(Paths.get("src/test/resources/json/postpayment.json")));
        String uuid = UUID.randomUUID().toString();
        double randomAmount = ThreadLocalRandom.current().nextDouble(1000.00, 20000.00);
        String amountFormatted = String.format(Locale.US, "%.2f", randomAmount);
        String jsonBody = jsonBodyTemplate
                .replace("{{$randomUUID}}", uuid)
                .replace("{{$randomAmount}}", amountFormatted);
        Response response = given()
                .header("Content-Type", "application/json")
                .header("Host", "vj4mk6pz0m.execute-api.us-east-1.amazonaws.com")
                .body(jsonBody)
                .post(endpoint);
        response.then().log().all().assertThat().statusCode(400);
    }

    @Feature("POST Payment")
    @Test
    public void charSpecialsConsentId() throws IOException {
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName("Verificar respuesta al ingresar caracteres especiales en el campo ConsentId"));
        BaseTest config = new BaseTest();
        config.setUp();
        String endpoint = "cert/api/paymentInitiation/" + bankId;
        String uuid = UUID.randomUUID().toString();
        String jsonBodyTemplate = new String(Files.readAllBytes(Paths.get("src/test/resources/json/postpayment.json")));
        double randomAmount = ThreadLocalRandom.current().nextDouble(1000.00, 20000.00);
        String amountFormatted = String.format(Locale.US, "%.2f", randomAmount);
        String jsonbody = jsonBodyTemplate
                .replace("{{$randomUUID}}", "@!!$%&=)(/?¡$")
                .replace("{{$randomAmount}}", amountFormatted);
        Response response = given()
                .header("Content-Type", "application/json; charset=UTF-8")
                .header("Host", "vj4mk6pz0m.execute-api.us-east-1.amazonaws.com")
                .header("x-fapi-interaction-id", uuid)
                .body(jsonbody)
                .post(endpoint);
        response.then().log().all().assertThat().statusCode(201);
    }

    @Feature("POST Payment")
    @Test
    public void charSpecialsInstructionIdentification() throws IOException {
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName("Verificar respuesta al ingresar caracteres especiales en el campo Data.Initiation.InstructionIdentification"));
        BaseTest config = new BaseTest();
        config.setUp();
        String endpoint = "cert/api/paymentInitiation/" + bankId;
        String uuid = UUID.randomUUID().toString();
        String jsonBodyTemplate = new String(Files.readAllBytes(Paths.get("src/test/resources/json/postpayment.json")));
        double randomAmount = ThreadLocalRandom.current().nextDouble(1000.00, 20000.00);
        String amountFormatted = String.format(Locale.US, "%.2f", randomAmount);
        String jsonbody = jsonBodyTemplate
                .replace("{{$randomUUID}}", uuid)
                .replace("{{$randomAmount}}", amountFormatted)
                .replace("0555ca8f-bca6-470e-a192-2e891572a20", "0555ca8f-bca6-470e-a192-2e891572a20@");
        Response response = given()
                .header("Content-Type", "application/json")
                .header("Host", "vj4mk6pz0m.execute-api.us-east-1.amazonaws.com")
                .header("x-fapi-interaction-id", uuid)
                .body(jsonbody)
                .post(endpoint);
        response.then().log().all().assertThat().statusCode(400);
    }

    @Test
    @Feature("POST Payment")
    public void charSpecialsAmount() throws IOException {
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName("Verificar respuesta al ingresar caracteres especiales en el campo Data.Initiation.InstructedAmount.Amount"));
        BaseTest config = new BaseTest();
        config.setUp();
        String endpoint = "cert/api/paymentInitiation/" + bankId;
        String uuid = UUID.randomUUID().toString();
        String jsonTemplate = new String(Files.readAllBytes(Paths.get("src/test/resources/json/postpayment.json")));
        String json = jsonTemplate.replace("{{$randomAmount}}", "1223.@8")
                .replace("{{$randomUUID}}", uuid);
        Response response = given()
                .header("Host", "vj4mk6pz0m.execute-api.us-east-1.amazonaws.com")
                .header("x-fapi-interaction-id", uuid)
                .header("Content-Type", "application/json; charset=UTF-8")
                .body(json)
                .post(endpoint);
        response.then().log().all().assertThat().statusCode(400);
    }

    @Test
    @Feature("POST Payment")
    public void charSpecialsDebtorSchemeName() throws IOException {
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName("Verificar respuesta al ingresar caracteres especiales en el campo DebtorAccount.SchemeName"));
        BaseTest config = new BaseTest();
        config.setUp();
        String endpoint = "cert/api/paymentInitiation/" + bankId;
        String uuid = UUID.randomUUID().toString();
        String jsonTemplate = new String(Files.readAllBytes(Paths.get("src/test/resources/json/postpayment.json")));
        double randomAmount = ThreadLocalRandom.current().nextDouble(1000.00, 20000.00);
        String amountFormatted = String.format(Locale.US, "%.2f", randomAmount);
        String json = jsonTemplate.replace("{{$randomAmount}}", amountFormatted)
                .replace("{{$randomUUID}}", uuid)
                .replace("CAHO", "CAH@");
        Response response = given()
                .header("Host", "vj4mk6pz0m.execute-api.us-east-1.amazonaws.com")
                .header("x-fapi-interaction-id", uuid)
                .header("Content-Type", "application/json")
                .body(json)
                .post(endpoint);
        response.then().log().all().assertThat().statusCode(400);
    }

    @Test
    @Feature("POST Payment")
    public void charSpecialsDebtorIdentification() throws IOException {
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName("Verificar respuesta al ingresar caracteres especiales en el campo DebtorAccount.Identification"));
        BaseTest config = new BaseTest();
        config.setUp();
        String endpoint = "cert/api/paymentInitiation/"+bankId;
        String uuid = UUID.randomUUID().toString();
        String jsonTemplate = new String(Files.readAllBytes(Paths.get("src/test/resources/json/postpayment.json")));
        double randomAmount = ThreadLocalRandom.current().nextDouble(1000.00, 20000.00);
        String amountFormatted = String.format(Locale.US, "%.2f", randomAmount);
        String json = jsonTemplate
                .replace("{{$randomUUID}}", uuid)
                .replace("00180004440","0018000444@")
                .replace("{{$randomAmount}}", amountFormatted);
        Response response = given()
                .header("Content-Type","application/json")
                .header("Host","vj4mk6pz0m.execute-api.us-east-1.amazonaws.com")
                .header("x-fapi-interaction-id", uuid)
                .body(json)
                .post(endpoint);
        response.then().log().all().assertThat().statusCode(400);
    }
    @Test
    @Feature("POST Payment")
    public void charSpecialsDebtorName() throws IOException {
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName("Verificar respuesta al ingresar caracteres especiales en el campo DebtorAccount.Name"));
        BaseTest config = new BaseTest();
        config.setUp();
        String endpoint = "cert/api/paymentInitiation/"+bankId;
        String uuid = UUID.randomUUID().toString();
        String jsonTemplate = new String(Files.readAllBytes(Paths.get("src/test/resources/json/postpayment.json")));
        double randomAmount = ThreadLocalRandom.current().nextDouble(1000.00, 20000.00);
        String amountFormatted = String.format(Locale.US, "%.2f", randomAmount);
        String json = jsonTemplate
                .replace("{{$randomUUID}}", uuid)
                .replace("Angela benitez","Angela Nuñ@ez")
                .replace("{{$randomAmount}}", amountFormatted);
        Response response = given()
                .header("Content-Type", "application/json; charset=UTF-8")
                .header("Host","vj4mk6pz0m.execute-api.us-east-1.amazonaws.com")
                .header("x-fapi-interaction-id", uuid)
                .body(json)
                .post(endpoint);
        response.then().log().all().assertThat().statusCode(400);
    }
    @Test
    @Feature("POST Payment")
    public void charSpecialsSecondaryIdentification() throws IOException {
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName("Verificar respuesta al ingresar caracteres especiales en el campo DebtorAccount.SecondaryIdentification"));
        BaseTest config = new BaseTest();
        config.setUp();
        String endpoint = "cert/api/paymentInitiation/"+bankId;
        String uuid = UUID.randomUUID().toString();
        String jsonTemplate = new String(Files.readAllBytes(Paths.get("src/test/resources/json/postpayment.json")));
        double randomAmount = ThreadLocalRandom.current().nextDouble(1000.00, 20000.00);
        String amountFormatted = String.format(Locale.US, "%.2f", randomAmount);
        String json = jsonTemplate
                .replace("{{$randomUUID}}", uuid)
                .replace("19256597","19256597@")
                .replace("{{$randomAmount}}", amountFormatted);
        Response response = given()
                .header("Content-Type","application/json")
                .header("Host","vj4mk6pz0m.execute-api.us-east-1.amazonaws.com")
                .header("x-fapi-interaction-id", uuid)
                .body(json)
                .post(endpoint);
        response.then().log().all().assertThat().statusCode(400);
    }
    @Test
    @Feature("POST Payment")
    public void charSpecialsCreditorAccountSchemeName() throws IOException {
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName("Verificar respuesta al ingresar caracteres especiales en el campo CreditorAccount.SchemeName"));
        BaseTest config = new BaseTest();
        config.setUp();
        String endpoint = "cert/api/paymentInitiation/"+bankId;
        String uuid = UUID.randomUUID().toString();
        String jsonTemplate = new String(Files.readAllBytes(Paths.get("src/test/resources/json/postpayment.json")));
        double randomAmount = ThreadLocalRandom.current().nextDouble(1000.00, 20000.00);
        String amountFormatted = String.format(Locale.US, "%.2f", randomAmount);
        String json = jsonTemplate
                .replace("{{$randomUUID}}", uuid)
                .replace("{{$randomAmount}}", amountFormatted)
                .replace("CAHO","CAHO@");
        Response response = given()
                .header("Content-Type","application/json")
                .header("Host","vj4mk6pz0m.execute-api.us-east-1.amazonaws.com")
                .header("x-fapi-interaction-id",uuid)
                .body(json)
                .post(endpoint);
        response.then().log().all().assertThat().statusCode(400);
    }
    @Test
    @Feature("POST Payment")
    public void charSpecialsCreditorAccountIdentification() throws IOException {
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName("Verificar respuesta al ingresar caracteres especiales en el campo CreditorAccount.Identification"));
        BaseTest config = new BaseTest();
        config.setUp();
        String endpoint = "cert/api/paymentInitiation/"+bankId;
        String jsonTemplate = new String(Files.readAllBytes(Paths.get("src/test/resources/json/postpayment.json")));
        double randomAmount = ThreadLocalRandom.current().nextDouble(1000.00, 20000.00);
        String amountFormatted = String.format(Locale.US, "%.2f", randomAmount);
        String uuid = UUID.randomUUID().toString();
        String json = jsonTemplate
                .replace("{{$randomUUID}}", uuid)
                .replace("{{$randomAmount}}", amountFormatted)
                .replace("937134616","937134616@");
        Response response = given()
                .header("Content-Type","application/json")
                .header("Host","vj4mk6pz0m.execute-api.us-east-1.amazonaws.com")
                .header("x-fapi-interaction-id",uuid)
                .body(json)
                .post(endpoint);
        response.then().log().all().assertThat().statusCode(400);
    }
    @Test
    @Feature("POST Payment")
    public void charSpecialsCreditorAccountName() throws IOException {
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName("Verificar respuesta al ingresar caracteres especiales en el campo CreditorAccount.Name") );
        BaseTest config = new BaseTest();
        config.setUp();
        String endpoint = "cert/api/paymentInitiation/"+bankId;
        String uuid = UUID.randomUUID().toString();
        String jsonTemplate = new String(Files.readAllBytes(Paths.get("src/test/resources/json/postpayment.json")));
        double randomAmount = ThreadLocalRandom.current().nextDouble(10000.00,20000.00);
        String amountFormatted = String.format(Locale.US, "%.2f", randomAmount );
        String json = jsonTemplate
                .replace("{{$randomAmount}}",amountFormatted)
                .replace("{{$randomUUID}}",uuid)
                .replace("Prueba","Prueba@");
        Response response = given()
                .header("x-fapi-interaction-id",uuid)
                .header("Host","vj4mk6pz0m.execute-api.us-east-1.amazonaws.com")
                .header("Content-Type","application/json")
                .body(json)
                .post(endpoint);
        response.then().log().all().assertThat().statusCode(400);
    }
    @Test
    @Feature("POST Payment")
    public void charSpecialsCreditorAccountSecondaryIdentification() throws IOException{
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName("Verificar respuesta al ingresar caracteres especiales en el campo CreditorAccount.SecondaryIdentification"));
        BaseTest config = new BaseTest();
        config.setUp();
        String uuid = UUID.randomUUID().toString();
        String endpoint = "cert/api/paymentInitiation/"+bankId;
        String jsonTemplate = new String(Files.readAllBytes(Paths.get("src/test/resources/json/postpayment.json")));
        double amountRandom = ThreadLocalRandom.current().nextDouble(20000,30000);
        String formatAmount = String.format(Locale.US, "%.2f", amountRandom);
        String json = jsonTemplate
                .replace("{{$randomUUID}}",uuid)
                .replace("{{$randomAmount}}",formatAmount)
                .replace("MERCHANTID","MERCHANTID@");
        Response response = given()
                .header("Host","vj4mk6pz0m.execute-api.us-east-1.amazonaws.com")
                .header("x-fapi-interaction-id",uuid)
                .header("Content-Type","application/json")
                .body(json)
                .post(endpoint);
        response.then().log().all().assertThat().statusCode(400);
    }
}
