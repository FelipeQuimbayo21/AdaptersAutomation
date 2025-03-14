package TestAPIS;
import io.qameta.allure.*;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import static io.restassured.RestAssured.given;
import static io.restassured.config.RestAssuredConfig.config;
@Feature("Api Testing")
public class TestPostPaymentInitiation {
    final Integer bankId = 51;

    @BeforeClass
    public void setUp() {
        // Configurar base URI apuntando al túnel SSH en localhost:8080
        RestAssured.baseURI = "https://localhost:8080";

        // Cargar el TrustStore con el certificado
        File trustStoreFile = new File("src/test/resources/certificates/keystore.jks");
        String trustStorePassword = "changeit";  // Mismo password usado en keytool

        // Configurar RestAssured para usar el TrustStore y aceptar cualquier hostname
        RestAssured.config = config().sslConfig(new SSLConfig()
                .trustStore(trustStoreFile.getAbsolutePath(), trustStorePassword)
                .allowAllHostnames());
    }
    @Test
    @Story("POST Payment Status")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Consulta el estado de un pago exitosamente")
    @Step("Verificar el estado de pago con ID válido")
    public void testPostPayment() throws IOException {
        String endpoint = "dev/api/paymentInitiation/" + bankId;
        String jsonBody = new String(Files.readAllBytes(Paths.get("src/test/resources/json/postpayment.json")));
        given()
                .filter(new AllureRestAssured())
                .header("x-fapi-interaction-id", "e82bc935-644f-4c7e-9273-86d41059cef7")
                .header("Content-Type", "application/json")
                .header("Host", "d4pzx2ks8a.execute-api.us-east-1.amazonaws.com")
                .body(jsonBody)
                .when()
                .post(endpoint)
                .then()
                .statusCode(201)
                .log().all();
    }
}
