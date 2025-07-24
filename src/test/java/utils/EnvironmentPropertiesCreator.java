package utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class EnvironmentPropertiesCreator {
    public void environment() {
        try {
            // Ruta al directorio donde quieres guardar el archivo (puedes cambiar la ruta si es necesario)
            File allureResultsDir = new File("target/allure-results");

            // Crear el directorio si no existe
            if (!allureResultsDir.exists()) {
                allureResultsDir.mkdirs();
            }

            // Crear el archivo environment.properties
            File environmentFile = new File(allureResultsDir, "environment.properties");

            // Crear el objeto Properties
            Properties properties = new Properties();

            // Establecer las propiedades del entorno
            properties.setProperty("Ambiente", "Certificación");

            // Escribir las propiedades al archivo
            try (FileOutputStream out = new FileOutputStream(environmentFile)) {
                properties.store(out, "Test Environment Properties");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
