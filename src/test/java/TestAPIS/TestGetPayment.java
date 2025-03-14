package TestAPIS;
import io.qameta.allure.*;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.io.File;
import static io.restassured.RestAssured.given;
import static io.restassured.config.RestAssuredConfig.config;

@Feature("Api Testing")
public class TestGetPayment {
    final String domesticPayment = "7ffb0f7a-bed2-46d5-93a4-e5f7542a37c4";
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
    @Story("GET Payment Status")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Consulta el estado de un pago exitosamente")
    @Step("Verificar el estado de pago con ID válido")
    public void testGetPayment() {
        String endpoint = "/dev/api/paymentInitiation/" + bankId + "/" + domesticPayment;

        given()
                .filter(new AllureRestAssured())
                .header("Host", "d4pzx2ks8a.execute-api.us-east-1.amazonaws.com")  // AWS requiere este Host
                .header("x-fapi-interaction-id", "e82bc935-644f-4c7e-9273-86d41059cef7")
                .when()
                .get(endpoint)
                .then()
                .statusCode(200)
                .log().all();
    }
}
