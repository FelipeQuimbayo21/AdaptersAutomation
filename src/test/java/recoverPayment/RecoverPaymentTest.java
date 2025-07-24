package recoverPayment;

import baseTest.BaseTest;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.Method;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import request.RequestRecoverPayment;
import utils.Config;
import utils.FileStore;

import java.io.IOException;

public class RecoverPaymentTest extends RequestRecoverPayment {
    int bankId = Config.getInt("bank_id");
    String domesticPayment = FileStore.readConsentId();

    @BeforeTest
    public void setUp() throws IOException {
        BaseTest config = new BaseTest();
        config.setUp();
    }
    @Epic("Payment")
    @Feature("Recover Payment")
    @Test(groups = "Regression")
    public void testGetPayment(){
        config();
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName("Verificar consulta de pago exitosamente"));
        response = request
                .filter(new AllureRestAssured())
                .basePath("/cert/api/paymentInitiation/" + bankId + "/" + domesticPayment)
                .request(Method.GET);
        response.then().log().all().assertThat().statusCode(200);
        String message = response.jsonPath().getString("Data.DomesticPaymentId");
        Assert.assertFalse(message.isEmpty(),"El campo no se encuentra");

    }
    @Epic("Payment")
    @Feature("Recover Payment")
    @Test(groups = "Regression")
    public void isMissingXFapi(){
        config();
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName("Enviar una solicitud GET cuando falta el x-fapi-interaction-id"));
        response = RestAssured.given().spec(new RequestRecoverPayment().requestSpecXFapi())
                .filter(new AllureRestAssured())
                .basePath("/cert/api/paymentInitiation/" + bankId + "/" + domesticPayment)
                .request(Method.GET);
        response.then().log().all().assertThat().statusCode(500);
        String message = response.jsonPath().getString("Message");
        Assert.assertTrue(message.contains("Internal Server Error 400 BAD_REQUEST \"Required header 'x-fapi-interaction-id' is not present.\""));
    }
    @Epic("Payment")
    @Feature("Recover Payment")
    @Test(groups = "Regression")
    public void isMissingBankId(){
        config();
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName("Enviar una solicitud GET cuando falta el bankId"));
        response = request
                .filter(new AllureRestAssured())
                .basePath("/cert/api/paymentInitiation//" + domesticPayment)
                .request(Method.GET);
        response.then().log().all().assertThat().statusCode(403);
        String message = response.jsonPath().getString("message");
        Assert.assertTrue(message.contains("Missing Authentication Token"),"No se encontro el mensaje");
    }
    @Epic("Payment")
    @Feature("Recover Payment")
    @Test(groups = "Regression")
    public void isMissingDomesticPayment(){
        config();
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName("Enviar una solicitud GET cuando falta el domesticPaymentId"));
        response = request
                .filter(new AllureRestAssured())
                .basePath("/cert/api/paymentInitiation/" + bankId + "/")
                .request(Method.GET);
        response.then().log().all().assertThat().statusCode(403);
        String message = response.jsonPath().getString("message");
        Assert.assertTrue(message.contains("Missing Authentication Token"),"No se encontro el mensaje");
    }
    @Epic("Payment")
    @Feature("Recover Payment")
    @Test(groups = "Regression")
    public void domesticPaymentUnRegistered(){
        String domesticPayment = "27d1c99f-bea0-4b0b-b6a6-8c0d1e6e7d19";
        config();
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName("Enviar una solicitud GET cuando se pasa un domesticPaymentId no registrado"));
        response = request
                .filter(new AllureRestAssured())
                .basePath("/cert/api/paymentInitiation/" + bankId + "/" + domesticPayment)
                .request(Method.GET);
        response.then().log().all().assertThat().statusCode(400);
        String message = response.jsonPath().getString("Errors.Message");
        Assert.assertTrue(message.contains("La transacción no existe en la base de datos con ID 27d1c99f-bea0-4b0b-b6a6-8c0d1e6e7d19"),"El mensaje no se encuentra");
    }
    @Epic("Payment")
    @Feature("Recover Payment")
    @Test(groups = "Regression")
    public void visibleDomesticPaymentId(){
        config();
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName("Verificar que la respuesta muestre el campo domesticPaymentId"));
        response = request
                .filter(new AllureRestAssured())
                .basePath("/cert/api/paymentInitiation/" + bankId + "/" + domesticPayment)
                .request(Method.GET);
        response.then().log().all().assertThat().statusCode(200);
        String message = response.jsonPath().getString("Data.DomesticPaymentId");
        Assert.assertFalse(message.isEmpty(),"El campo no se encuentra");
    }
    @Epic("Payment")
    @Feature("Recover Payment")
    @Test(groups = "Regression")
    public void visibleFieldStatus(){
        config();
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName("Verificar que la respuesta muestre el campo status"));
        response = request
                .filter(new AllureRestAssured())
                .basePath("/cert/api/paymentInitiation/" + bankId + "/" + domesticPayment)
                .request(Method.GET);
        response.then().log().all().assertThat().statusCode(200);
        String message = response.jsonPath().getString("Data.Status");
        Assert.assertFalse(message.isEmpty(),"El campo no se encuentra");
    }
    @Epic("Payment")
    @Feature("Recover Payment")
    @Test(groups = "Regression")
    public void visibleFieldAmount(){
        config();
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName("Verificar que la respuesta muestre el campo Amount"));
        response = request
                .filter(new AllureRestAssured())
                .basePath("/cert/api/paymentInitiation/" + bankId + "/" + domesticPayment)
                .request(Method.GET);
        response.then().log().all().assertThat().statusCode(200);
        String message = response.jsonPath().getString("Data.Charges.Amount.Amount");
        Assert.assertFalse(message.isEmpty(),"El campo no se encuentra");
    }
    @Epic("Payment")
    @Feature("Recover Payment")
    @Test(groups = "Regression")
    public void visibleFieldDebtorAccountSchemeName(){
        config();
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName("Verificar que la respuesta muestre el campo DebtorAccount.SchemeName"));
        response = request
                .filter(new AllureRestAssured())
                .basePath("/cert/api/paymentInitiation/" + bankId + "/" + domesticPayment)
                .request(Method.GET);
        response.then().log().all().assertThat().statusCode(200);
        String message = response.jsonPath().getString("Data.Initiation.DebtorAccount.SchemeName");
        Assert.assertFalse(message.isEmpty(),"El campo no se encuentra");
    }
    @Epic("Payment")
    @Feature("Recover Payment")
    @Test(groups = "Regression")
    public void visibleFieldDebtorAccountIdentification(){
        config();
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName("Verificar que la respuesta muestre el campo DebtorAccount.Identification"));
        response = request
                .filter(new AllureRestAssured())
                .basePath("/cert/api/paymentInitiation/" + bankId + "/" + domesticPayment)
                .request(Method.GET);
        response.then().log().all().assertThat().statusCode(200);
        String message = response.jsonPath().getString("Data.Initiation.DebtorAccount.Identification");
        Assert.assertFalse(message.isEmpty(),"El campo no se encuentra");
    }
    @Epic("Payment")
    @Feature("Recover Payment")
    @Test(groups = "Regression")
    public void visibleFieldCreditorAccountSchemeName(){
        config();
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName("Verificar que la respuesta muestre el campo CreditorAccount.SchemeName"));
        response = request
                .filter(new AllureRestAssured())
                .basePath("/cert/api/paymentInitiation/" + bankId + "/" + domesticPayment)
                .request(Method.GET);
        response.then().log().all().assertThat().statusCode(200);
        String message = response.jsonPath().getString("Data.Initiation.CreditorAccount.SchemeName");
        Assert.assertFalse(message.isEmpty(),"El campo no se encuentra");
    }
    @Epic("Payment")
    @Feature("Recover Payment")
    @Test(groups = "Regression")
    public void visibleFieldCreditorAccountIdentification(){
        config();
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName("Verificar que la respuesta muestre el campo CreditorAccount.Identification"));
        response = request
                .filter(new AllureRestAssured())
                .basePath("/cert/api/paymentInitiation/" + bankId + "/" + domesticPayment)
                .request(Method.GET);
        response.then().log().all().assertThat().statusCode(200);
        String message = response.jsonPath().getString("Data.Initiation.CreditorAccount.Identification");
        Assert.assertFalse(message.isEmpty(),"El campo no se encuentra");
    }
    @Epic("Payment")
    @Feature("Recover Payment")
    @Test(groups = "Regression")
    public void visibleFieldInstructionIdentification(){
        config();
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName("Verificar que la respuesta muestre el campo InstructionIdentification"));
        response = request
                .filter(new AllureRestAssured())
                .basePath("/cert/api/paymentInitiation/" + bankId + "/" + domesticPayment)
                .request(Method.GET);
        response.then().log().all().assertThat().statusCode(200);
        String message = response.jsonPath().getString("Data.Initiation.InstructionIdentification");
        Assert.assertFalse(message.isEmpty(),"El campo no se encuentra");
    }
}