package payment;

import baseTest.BaseTest;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import request.RequestPayment;
import utils.Config;
import utils.FileStore;

import java.io.IOException;

public class PaymentTest extends RequestPayment {
    private final int bankId = Config.getInt("bank_id");
    @BeforeTest
    public void setUp() throws IOException {
        BaseTest config = new BaseTest();
        config.setUp();
    }
    @Epic("Payment")
    @Feature("Make payment")
    @Test(groups = "Regression")
    public void testPostPayment() throws IOException {
        config();
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName("Verificar un pago exitosamente"));
        response = request.basePath("cert/api/paymentInitiation/" + bankId)
                .filter(new AllureRestAssured())
                .request(Method.POST);
        response.then().log().all().assertThat().statusCode(201);
        String domesticPayment = response.jsonPath().getString("Data.DomesticPaymentId");
        FileStore.saveConsentId(domesticPayment);
        Assert.assertFalse(domesticPayment.isEmpty(),"No se muestra el mensaje");
    }
    @Epic("Payment")
    @Feature("Make payment")
    @Test(groups = "Regression")
    public void isMissingXInteractionId() throws IOException {
        config();
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName("Verificar respuesta cuando falta el x-fapi-interaction-id"));
        response = RestAssured.given()
                .spec(new RequestPayment().requestSpecificationIsMissingXInteractionId())
                .basePath("cert/api/paymentInitiation/" + bankId)
                .filter(new AllureRestAssured())
                .request(Method.POST);
        response.then().log().all().assertThat().statusCode(400);
        String message = response.jsonPath().getString("Errors.Message");
        Assert.assertTrue(message.contains("A required field is missing or invalid."),"No se muestra el mensaje");
    }
    @Epic("Payment")
    @Feature("Make payment")
    @Test(groups = "Regression")
    public void charSpecialsConsentId() throws IOException {
        config();
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName("Verificar respuesta al ingresar caracteres especiales en el campo ConsentId"));
        response = RestAssured.given()
                .spec(new RequestPayment().requestSpecificationCharSpecialsConsentId())
                .basePath("cert/api/paymentInitiation/" + bankId)
                .filter(new AllureRestAssured())
                .request(Method.POST);
        response.then().log().all().assertThat().statusCode(201);
        String domesticPayment = response.jsonPath().getString("Data.DomesticPaymentId");
        Assert.assertFalse(domesticPayment.isEmpty(),"No se muestra el mensaje");
    }
    @Epic("Payment")
    @Feature("Make payment")
    @Test(groups = "Regression")
    public void charSpecialsInstructionIdentification() throws IOException {
        config();
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName("Verificar respuesta al ingresar caracteres especiales en el campo Data.Initiation.InstructionIdentification"));
        response = RestAssured.given()
                .spec(new RequestPayment().requestSpecificationCharSpecialsInstructionIdentification())
                .basePath("cert/api/paymentInitiation/" + bankId)
                .filter(new AllureRestAssured())
                .request(Method.POST);
        response.then().log().all().assertThat().statusCode(400);
        String message = response.jsonPath().getString("Errors.Message");
        Assert.assertTrue(message.contains("Valor inválido"),"No se encuentra el mensaje");
    }
    @Epic("Payment")
    @Feature("Make payment")
    @Test(groups = "Regression")
    public void charSpecialsAmount() throws IOException {
        config();
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName("Verificar respuesta al ingresar caracteres especiales en el campo Data.Initiation.InstructedAmount.Amount"));
        response = RestAssured.given()
                .spec(new RequestPayment().requestSpecificationCharSpecialsAmount())
                .basePath("cert/api/paymentInitiation/" + bankId)
                .filter(new AllureRestAssured())
                .request(Method.POST);
        response.then().log().all().assertThat().statusCode(400);
        String message = response.jsonPath().getString("Errors.Message");
        Assert.assertTrue(message.contains("Valor inválido"),"No se encuentra el mensaje");
    }
    @Epic("Payment")
    @Feature("Make payment")
    @Test(groups = "Regression")
    public void charSpecialsDebtorSchemeName() throws IOException {
        config();
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName("Verificar respuesta al ingresar caracteres especiales en el campo DebtorAccount.SchemeName"));
        response = RestAssured.given()
                .spec(new RequestPayment().requestSpecificationCharSpecialsDebtorSchemeName())
                .basePath("cert/api/paymentInitiation/" + bankId)
                .filter(new AllureRestAssured())
                .request(Method.POST);
        response.then().log().all().assertThat().statusCode(400);
        String message = response.jsonPath().getString("Errors.Message");
        Assert.assertTrue(message.contains("Valor inválido"),"No se encuentra el mensaje");
    }
    @Epic("Payment")
    @Feature("Make payment")
    @Test(groups = "Regression")
    public void charSpecialsDebtorIdentification() throws IOException {
        config();
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName("Verificar respuesta al ingresar caracteres especiales en el campo DebtorAccount.Identification"));
        response = RestAssured.given()
                .spec(new RequestPayment().requestSpecificationCharSpecialsDebtorIdentification())
                .basePath("cert/api/paymentInitiation/" + bankId)
                .filter(new AllureRestAssured())
                .request(Method.POST);
        response.then().log().all().assertThat().statusCode(400);
        String message = response.jsonPath().getString("Errors.Message");
        Assert.assertTrue(message.contains("Valor inválido"),"No se encuentra el mensaje");
    }
    @Epic("Payment")
    @Feature("Make payment")
    @Test(groups = "Regression")
    public void charSpecialsDebtorName() throws IOException {
        config();
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName("Verificar respuesta al ingresar caracteres especiales en el campo DebtorAccount.Name"));
        response = RestAssured.given()
                .spec(new RequestPayment().requestSpecificationCharSpecialsDebtorName())
                .basePath("cert/api/paymentInitiation/" + bankId)
                .filter(new AllureRestAssured())
                .log().all()
                .request(Method.POST);
        response.then().log().all().assertThat().statusCode(400);
        String message = response.jsonPath().getString("Errors.Message");
        Assert.assertTrue(message.contains("Valor inválido"),"No se encuentra el mensaje");
    }
    @Epic("Payment")
    @Feature("Make payment")
    @Test(groups = "Regression")
    public void charSpecialsSecondaryIdentification() throws IOException {
        config();
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName("Verificar respuesta al ingresar caracteres especiales en el campo DebtorAccount.SecondaryIdentification"));
        response = RestAssured.given()
                .spec(new RequestPayment().requestSpecificationCharSpecialsSecondaryIdentification())
                .basePath("cert/api/paymentInitiation/" + bankId)
                .filter(new AllureRestAssured())
                .request(Method.POST);
        response.then().log().all().assertThat().statusCode(400);
        String message = response.jsonPath().getString("Errors.Message");
        Assert.assertTrue(message.contains("Valor inválido"),"No se encuentra el mensaje");
    }
    @Epic("Payment")
    @Feature("Make payment")
    @Test(groups = "Regression")
    public void charSpecialsCreditorAccountSchemeName() throws IOException {
        config();
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName("Verificar respuesta al ingresar caracteres especiales en el campo CreditorAccount.SchemeName"));
        response = RestAssured.given()
                .spec(new RequestPayment().requestSpecificationCharSpecialsDebtorSchemeName())
                .basePath("cert/api/paymentInitiation/" + bankId)
                .filter(new AllureRestAssured())
                .request(Method.POST);
        response.then().log().all().assertThat().statusCode(400);
        String message = response.jsonPath().getString("Errors.Message");
        Assert.assertTrue(message.contains("Valor inválido"),"No se encuentra el mensaje");
    }
    @Epic("Payment")
    @Feature("Make payment")
    @Test(groups = "Regression")
    public void charSpecialsCreditorAccountIdentification() throws IOException {
        config();
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName("Verificar respuesta al ingresar caracteres especiales en el campo CreditorAccount.Identification"));
        response = RestAssured.given()
                .spec(new RequestPayment().requestSpecificationCharSpecialsCreditorAccountIdentification())
                .basePath("cert/api/paymentInitiation/" + bankId)
                .filter(new AllureRestAssured())
                .request(Method.POST);
        response.then().log().all().assertThat().statusCode(400);
        String message = response.jsonPath().getString("Errors.Message");
        Assert.assertTrue(message.contains("Valor inválido"),"No se encuentra el mensaje");
    }
    @Epic("Payment")
    @Feature("Make payment")
    @Test(groups = "Regression")
    public void charSpecialsCreditorAccountName() throws IOException {
        config();
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName("Verificar respuesta al ingresar caracteres especiales en el campo CreditorAccount.Name"));
        response = RestAssured.given()
                .spec(new RequestPayment().requestSpecificationCharSpecialsCreditorAccountName())
                .basePath("cert/api/paymentInitiation/" + bankId)
                .filter(new AllureRestAssured())
                .request(Method.POST);
        response.then().log().all().assertThat().statusCode(400);
        String message = response.jsonPath().getString("Errors.Message");
        Assert.assertTrue(message.contains("Valor inválido"),"No se encuentra el mensaje");
    }
    @Epic("Payment")
    @Feature("Make payment")
    @Test(groups = "Regression")
    public void charSpecialsCreditorAccountSecondaryIdentification() throws IOException {
        config();
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName("Verificar respuesta al ingresar caracteres especiales en el campo CreditorAccount.SecondaryIdentification"));
        response = RestAssured.given()
                .spec(new RequestPayment().requestSpecificationCharSpecialsCreditorAccountSecondaryIdentification())
                .basePath("cert/api/paymentInitiation/" + bankId)
                .filter(new AllureRestAssured())
                .request(Method.POST);
        response.then().log().all().assertThat().statusCode(400);
        String message = response.jsonPath().getString("Errors.Message");
        Assert.assertTrue(message.contains("Valor inválido"),"No se encuentra el mensaje");
    }
    @Epic("Payment")
    @Feature("Make payment")
    @Test(groups = "Regression")
    public void paymentContextEcommerceGoods() throws IOException {
        config();
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName("Enviar una solicitud POST con valor EcommerceGoods en el campo PaymentContextCode"));
        response = RestAssured.given()
                .spec(new RequestPayment().requestSpecificationPaymentContextBillPayment())
                .basePath("cert/api/paymentInitiation/" + bankId)
                .filter(new AllureRestAssured())
                .request(Method.POST);
        response.then().log().all().assertThat().statusCode(201);
        String domestic = response.jsonPath().getString("Data.DomesticPaymentId");
        Assert.assertFalse(domestic.isEmpty(),"No se muestra el mensaje");
    }
    @Epic("Payment")
    @Feature("Make payment")
    @Test(groups = "Regression")
    public void isMissingBankId() throws IOException {
        config();
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName("Enviar una solicitud POST cuando falta el bankId"));
        response = request.basePath("cert/api/paymentInitiation/")
                .filter(new AllureRestAssured())
                .request(Method.POST);
        response.then().log().all().assertThat().statusCode(403);
        String message = response.jsonPath().getString("message");
        Assert.assertTrue(message.contains("Missing Authentication Token"),"No se muestra el mensaje");
    }
    @Epic("Payment")
    @Feature("Make payment")
    @Test(groups = "Regression")
    public void NoAvailableBalance() throws IOException {
        config();
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName("Enviar una solicitud POST sin saldo disponible."));
        response = RestAssured.given()
                .spec(new RequestPayment().requestSpecificationNoAvailableBalance())
                .basePath("cert/api/paymentInitiation/"+bankId)
                .filter(new AllureRestAssured())
                .request(Method.POST);
        response.then().log().all().assertThat().statusCode(201);
        String domestic = response.jsonPath().getString("Data.DomesticPaymentId");
        Assert.assertFalse(domestic.isEmpty(),"No se muestra el mensaje");
    }
    @Epic("Payment")
    @Feature("Make payment")
    @Test(groups = "Regression")
    public void amountNegativeTransaction() throws IOException {
        config();
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName("Enviar una solicitud POST con un monto en negativo."));
        response = RestAssured.given()
                .spec(new RequestPayment().requestSpecificationAmountNegativeTransaction())
                .basePath("cert/api/paymentInitiation/"+bankId)
                .filter(new AllureRestAssured())
                .request(Method.POST);
        response.then().log().all().assertThat().statusCode(400);
        String message = response.jsonPath().getString("Errors.Message");
        Assert.assertTrue(message.contains("Valor inválido"),"No se encuentra el mensaje");
    }
    @Epic("Payment")
    @Feature("Make payment")
    @Test(groups = "Regression")
    public void bankIdNoExist() throws IOException {
        config();
        int bankId = 9999;
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName("Enviar una solicitud POST cuando el bankId no existe"));
        response = request.basePath("cert/api/paymentInitiation/" + bankId)
                .filter(new AllureRestAssured())
                .request(Method.POST);
        response.then().log().all().assertThat().statusCode(404);
        String message = response.jsonPath().getString("Errors.Message");
        Assert.assertTrue(message.contains("BankId no se encuentra registrado"),"No se muestra el mensaje");
    }
    @Epic("Payment")
    @Feature("Make payment")
    @Test(groups = "Regression")
    public void visibleFieldDomesticPaymentId() throws IOException {
        config();
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName("Verificar que la respuesta muestre el campo domesticPaymentId"));
        response = request.basePath("cert/api/paymentInitiation/" + bankId)
                .filter(new AllureRestAssured())
                .request(Method.POST);
        response.then().log().all().assertThat().statusCode(201);
        String message = response.jsonPath().getString("Data.DomesticPaymentId");
        Assert.assertFalse(message.isEmpty(),"No se muestra el mensaje");
    }
    @Epic("Payment")
    @Feature("Make payment")
    @Test(groups = "Regression")
    public void visibleFieldStatus() throws IOException {
        config();
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName("Verificar que la respuesta muestre el valor status"));
        response = request.basePath("cert/api/paymentInitiation/" + bankId)
                .filter(new AllureRestAssured())
                .request(Method.POST);
        response.then().log().all().assertThat().statusCode(201);
        String message = response.jsonPath().getString("Data.Status");
        Assert.assertFalse(message.isEmpty(),"No se muestra el mensaje");
    }
}