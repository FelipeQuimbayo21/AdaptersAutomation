package TestAdapters;

import TestAPIS.TestPostPaymentInitiation;
import org.testng.annotations.Test;

import java.io.IOException;

public class TestValidations {
    @Test(priority = 1)
    public void isMissingXInteractionId() throws IOException {
        new TestPostPaymentInitiation().isMissingXInteractionId();
    }
    @Test(priority = 2)
    public void charSpecialsConsentId() throws IOException {
        new TestPostPaymentInitiation().charSpecialsConsentId();
    }
    @Test(priority = 3)
    public void charSpecialsInstructionIdentification() throws IOException {
        new TestPostPaymentInitiation().charSpecialsInstructionIdentification();
    }
    @Test(priority = 4)
    public void charSpecialsAmount() throws IOException {
        new TestPostPaymentInitiation().charSpecialsAmount();
    }
    @Test(priority = 5)
    public void charSpecialsDebtorSchemeName() throws IOException {
        new TestPostPaymentInitiation().charSpecialsDebtorSchemeName();
    }
    @Test(priority = 6)
    public void charSpecialsDebtorIdentification() throws IOException {
        new TestPostPaymentInitiation().charSpecialsDebtorIdentification();
    }
    @Test(priority = 7)
    public void charSpecialsDebtorName() throws IOException {
        new TestPostPaymentInitiation().charSpecialsDebtorName();
    }
    @Test(priority = 8)
    public void charSpecialsSecondaryIdentification() throws IOException {
        new TestPostPaymentInitiation().charSpecialsSecondaryIdentification();
    }
    @Test(priority = 9)
    public void charSpecialsCreditorAccountSchemeName() throws IOException {
        new TestPostPaymentInitiation().charSpecialsCreditorAccountSchemeName();
    }
    @Test(priority = 10)
    public void charSpecialsCreditorAccountIdentification() throws IOException {
        new TestPostPaymentInitiation().charSpecialsCreditorAccountIdentification();
    }
    @Test(priority = 11)
    public void charSpecialsCreditorAccountName() throws IOException {
        new TestPostPaymentInitiation().charSpecialsCreditorAccountName();
    }
    @Test(priority = 12)
    public void charSpecialsCreditorAccountSecondaryIdentification() throws IOException {
        new TestPostPaymentInitiation().charSpecialsCreditorAccountSecondaryIdentification();
    }
}

