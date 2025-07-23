package request;

import baseTest.BaseTest;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class RequestPayment extends BaseTest {
    protected RequestSpecification request;
    protected Response response;

    @BeforeMethod(alwaysRun = true)
    public void config() throws IOException {
        request = RestAssured.given().spec(requestSpecification());
    }

    public RequestSpecification requestSpecification() throws IOException {
        String uuid = UUID.randomUUID().toString();
        double randomAmount = ThreadLocalRandom.current().nextDouble(100.00, 900.00);
        String amountFormatted = String.format(Locale.US, "%.2f", randomAmount);
        String jsonBodyTemplate = new String(Files.readAllBytes(Paths.get("src/test/resources/json/postpayment.json")));
        String jsonBody = jsonBodyTemplate
                .replace("{{$randomUUID}}", uuid)
                .replace("{{$randomAmount}}", amountFormatted);
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addHeader("x-fapi-interaction-id",uuid)
                .addHeader("Host","vj4mk6pz0m.execute-api.us-east-1.amazonaws.com")
                .setBody(jsonBody)
                .build();
    }
    public RequestSpecification requestSpecificationIsMissingXInteractionId() throws IOException {
        String uuid = UUID.randomUUID().toString();
        double randomAmount = ThreadLocalRandom.current().nextDouble(100.00, 900.00);
        String amountFormatted = String.format(Locale.US, "%.2f", randomAmount);
        String jsonBodyTemplate = new String(Files.readAllBytes(Paths.get("src/test/resources/json/postpayment.json")));
        String jsonBody = jsonBodyTemplate
                .replace("{{$randomUUID}}", uuid)
                .replace("{{$randomAmount}}", amountFormatted);
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addHeader("Host","vj4mk6pz0m.execute-api.us-east-1.amazonaws.com")
                .setBody(jsonBody)
                .build();
    }
    public RequestSpecification requestSpecificationCharSpecialsConsentId() throws IOException {
        String uuid = UUID.randomUUID().toString();
        double randomAmount = ThreadLocalRandom.current().nextDouble(100.00, 900.00);
        String amountFormatted = String.format(Locale.US, "%.2f", randomAmount);
        String jsonBodyTemplate = new String(Files.readAllBytes(Paths.get("src/test/resources/json/postpayment.json")));
        String jsonBody = jsonBodyTemplate
                .replace("{{$randomUUID}}", "@!!$%&=)(/?¡$")
                .replace("{{$randomAmount}}", amountFormatted);
        return new RequestSpecBuilder()
                .setContentType("application/json; charset=UTF-8")
                .addHeader("x-fapi-interaction-id",uuid)
                .addHeader("Host","vj4mk6pz0m.execute-api.us-east-1.amazonaws.com")
                .setBody(jsonBody)
                .build();
    }
    public RequestSpecification requestSpecificationCharSpecialsInstructionIdentification() throws IOException {
        String uuid = UUID.randomUUID().toString();
        double randomAmount = ThreadLocalRandom.current().nextDouble(100.00, 900.00);
        String amountFormatted = String.format(Locale.US, "%.2f", randomAmount);
        String jsonBodyTemplate = new String(Files.readAllBytes(Paths.get("src/test/resources/json/postpayment.json")));
        String jsonBody = jsonBodyTemplate
                .replace("{{$randomUUID}}", uuid)
                .replace("{{$randomAmount}}", amountFormatted)
                .replace("0555ca8f-bca6-470e-a192-2e891572a20", "0555ca8f-bca6-470e-a192-2e891572a20@");
        return new RequestSpecBuilder()
                .setContentType("application/json; charset=UTF-8")
                .addHeader("x-fapi-interaction-id",uuid)
                .addHeader("Host","vj4mk6pz0m.execute-api.us-east-1.amazonaws.com")
                .setBody(jsonBody)
                .build();
    }
    public RequestSpecification requestSpecificationCharSpecialsAmount() throws IOException {
        String uuid = UUID.randomUUID().toString();
        String jsonBodyTemplate = new String(Files.readAllBytes(Paths.get("src/test/resources/json/postpayment.json")));
        String json = jsonBodyTemplate.replace("{{$randomAmount}}", "1223.@8")
                .replace("{{$randomUUID}}", uuid);
        return new RequestSpecBuilder()
                .setContentType("application/json; charset=UTF-8")
                .addHeader("x-fapi-interaction-id",uuid)
                .addHeader("Host","vj4mk6pz0m.execute-api.us-east-1.amazonaws.com")
                .setBody(json)
                .build();
    }
    public RequestSpecification requestSpecificationCharSpecialsDebtorSchemeName() throws IOException {
        String uuid = UUID.randomUUID().toString();
        String jsonBodyTemplate = new String(Files.readAllBytes(Paths.get("src/test/resources/json/postpayment.json")));
        double randomAmount = ThreadLocalRandom.current().nextDouble(1000.00, 20000.00);
        String amountFormatted = String.format(Locale.US, "%.2f", randomAmount);
        String json = jsonBodyTemplate.replace("{{$randomAmount}}", amountFormatted)
                .replace("{{$randomUUID}}", uuid)
                .replace("CAHO", "CAH@");
        return new RequestSpecBuilder()
                .setContentType("application/json; charset=UTF-8")
                .addHeader("x-fapi-interaction-id",uuid)
                .addHeader("Host","vj4mk6pz0m.execute-api.us-east-1.amazonaws.com")
                .setBody(json)
                .build();
    }
    public RequestSpecification requestSpecificationCharSpecialsDebtorIdentification() throws IOException {
        String uuid = UUID.randomUUID().toString();
        String jsonBodyTemplate = new String(Files.readAllBytes(Paths.get("src/test/resources/json/postpayment.json")));
        double randomAmount = ThreadLocalRandom.current().nextDouble(1000.00, 20000.00);
        String amountFormatted = String.format(Locale.US, "%.2f", randomAmount);
        String json = jsonBodyTemplate.replace("{{$randomAmount}}", amountFormatted)
                .replace("{{$randomUUID}}", uuid)
                .replace("214021007548984001","0018000444@");
        return new RequestSpecBuilder()
                .setContentType("application/json; charset=UTF-8")
                .addHeader("x-fapi-interaction-id",uuid)
                .addHeader("Host","vj4mk6pz0m.execute-api.us-east-1.amazonaws.com")
                .setBody(json)
                .build();
    }
    public RequestSpecification requestSpecificationCharSpecialsDebtorName() throws IOException {
        String uuid = UUID.randomUUID().toString();
        String jsonBodyTemplate = new String(Files.readAllBytes(Paths.get("src/test/resources/json/postpayment.json")));
        double randomAmount = ThreadLocalRandom.current().nextDouble(1000.00, 20000.00);
        String amountFormatted = String.format(Locale.US, "%.2f", randomAmount);
        String json = jsonBodyTemplate.replace("{{$randomAmount}}", amountFormatted)
                .replace("Test Prueba","Angela Nuñ@ez")
                .replace("{{$randomUUID}}", uuid);
        return new RequestSpecBuilder()
                .setContentType("application/json; charset=UTF-8")
                .addHeader("x-fapi-interaction-id",uuid)
                .addHeader("Host","vj4mk6pz0m.execute-api.us-east-1.amazonaws.com")
                .setBody(json)
                .build();
    }
    public RequestSpecification requestSpecificationCharSpecialsSecondaryIdentification() throws IOException {
        String uuid = UUID.randomUUID().toString();
        String jsonBodyTemplate = new String(Files.readAllBytes(Paths.get("src/test/resources/json/postpayment.json")));
        double randomAmount = ThreadLocalRandom.current().nextDouble(1000.00, 20000.00);
        String amountFormatted = String.format(Locale.US, "%.2f", randomAmount);
        String json = jsonBodyTemplate.replace("{{$randomAmount}}", amountFormatted)
                .replace("3048660","3048660@")
                .replace("{{$randomUUID}}", uuid);
        return new RequestSpecBuilder()
                .setContentType("application/json; charset=UTF-8")
                .addHeader("x-fapi-interaction-id",uuid)
                .addHeader("Host","vj4mk6pz0m.execute-api.us-east-1.amazonaws.com")
                .setBody(json)
                .build();
    }
    public RequestSpecification requestSpecificationCharSpecialsCreditorAccountIdentification() throws IOException {
        String uuid = UUID.randomUUID().toString();
        String jsonBodyTemplate = new String(Files.readAllBytes(Paths.get("src/test/resources/json/postpayment.json")));
        double randomAmount = ThreadLocalRandom.current().nextDouble(1000.00, 20000.00);
        String amountFormatted = String.format(Locale.US, "%.2f", randomAmount);
        String json = jsonBodyTemplate.replace("{{$randomAmount}}", amountFormatted)
                .replace("0074447483","937134616@")
                .replace("{{$randomUUID}}", uuid);
        return new RequestSpecBuilder()
                .setContentType("application/json; charset=UTF-8")
                .addHeader("x-fapi-interaction-id",uuid)
                .addHeader("Host","vj4mk6pz0m.execute-api.us-east-1.amazonaws.com")
                .setBody(json)
                .build();
    }
    public RequestSpecification requestSpecificationCharSpecialsCreditorAccountName() throws IOException {
        String uuid = UUID.randomUUID().toString();
        String jsonBodyTemplate = new String(Files.readAllBytes(Paths.get("src/test/resources/json/postpayment.json")));
        double randomAmount = ThreadLocalRandom.current().nextDouble(1000.00, 20000.00);
        String amountFormatted = String.format(Locale.US, "%.2f", randomAmount);
        String json = jsonBodyTemplate.replace("{{$randomAmount}}", amountFormatted)
                .replace("Prueba","Prueba@")
                .replace("{{$randomUUID}}", uuid);
        return new RequestSpecBuilder()
                .setContentType("application/json; charset=UTF-8")
                .addHeader("x-fapi-interaction-id",uuid)
                .addHeader("Host","vj4mk6pz0m.execute-api.us-east-1.amazonaws.com")
                .setBody(json)
                .build();
    }
    public RequestSpecification requestSpecificationCharSpecialsCreditorAccountSecondaryIdentification() throws IOException {
        String uuid = UUID.randomUUID().toString();
        String jsonBodyTemplate = new String(Files.readAllBytes(Paths.get("src/test/resources/json/postpayment.json")));
        double randomAmount = ThreadLocalRandom.current().nextDouble(1000.00, 20000.00);
        String amountFormatted = String.format(Locale.US, "%.2f", randomAmount);
        String json = jsonBodyTemplate.replace("{{$randomAmount}}", amountFormatted)
                .replace("MERCHANTID","MERCHANTID@")
                .replace("{{$randomUUID}}", uuid);
        return new RequestSpecBuilder()
                .setContentType("application/json; charset=UTF-8")
                .addHeader("x-fapi-interaction-id",uuid)
                .addHeader("Host","vj4mk6pz0m.execute-api.us-east-1.amazonaws.com")
                .setBody(json)
                .build();
    }
}
