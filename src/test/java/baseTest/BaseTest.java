package baseTest;
import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import utils.Config;

import java.io.File;
import java.io.IOException;

import static io.restassured.config.RestAssuredConfig.config;

public class BaseTest {
    public void setUp() throws IOException {
        // Establecer base URI apuntando al túnel SSH o proxy en localhost
        RestAssured.baseURI = Config.get("base_test_url");
        // Cargar el TrustStore con el certificado SSL necesario
        File trustStoreFile = new File("src/test/resources/certificates/keystore.jks");
        String trustStorePassword = "changeit"; // Mismo password usado al generar el keystore
        // Configurar RestAssured para usar el TrustStore y aceptar cualquier hostname (solo para pruebas)
        RestAssured.config = config().sslConfig(new SSLConfig()
                .trustStore(trustStoreFile.getAbsolutePath(), trustStorePassword)
                .allowAllHostnames());
    }
}
