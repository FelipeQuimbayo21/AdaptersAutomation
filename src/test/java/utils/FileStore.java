package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileStore {
    private static final Path path2 = Path.of("domesticPayment.tmp");

    public static void saveConsentId (String domesticPayment) {
        try {
            Files.writeString(path2, domesticPayment);
        } catch (IOException e) {
            throw new RuntimeException("Error guardando domesticPayment", e);
        }
    }
    public static String readConsentId() {
        try {
            String domesticPayment = Files.readString(path2);
            return domesticPayment;
        } catch (IOException e) {
            throw new RuntimeException("Error leyendo domesticPayment", e);
        }
    }




}
