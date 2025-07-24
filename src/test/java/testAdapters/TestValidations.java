package testAdapters;

import baseTest.BaseTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import payment.PaymentTest;
import recoverPayment.RecoverPaymentTest;
import utils.EnvironmentPropertiesCreator;
import java.io.IOException;

public class TestValidations {
    @BeforeMethod
    public void setUp() throws Exception {
        BaseTest config = new BaseTest();
        config.setUp();
    }
    @Test(priority = 1)
    public void scenario1() throws IOException {
        new EnvironmentPropertiesCreator().environment();
        new PaymentTest().isMissingXInteractionId();
    }
    @Test(priority = 2)
    public void scenario2() throws IOException {
        new PaymentTest().charSpecialsConsentId();
    }
    @Test(priority = 3)
    public void scenario3() throws IOException {
        new PaymentTest().charSpecialsInstructionIdentification();
    }
    @Test(priority = 4)
    public void scenario4() throws IOException {
        new PaymentTest().charSpecialsAmount();
    }
    @Test(priority = 5)
    public void scenario5() throws IOException {
        new PaymentTest().charSpecialsDebtorSchemeName();
    }
    @Test(priority = 6)
    public void scenario6() throws IOException {
        new PaymentTest().charSpecialsDebtorIdentification();
    }
    @Test(priority = 7)
    public void scenario7() throws IOException {
        new PaymentTest().charSpecialsDebtorName();
    }
    @Test(priority = 8)
    public void scenario8() throws IOException {
        new PaymentTest().charSpecialsSecondaryIdentification();
    }
    @Test(priority = 9)
    public void scenario9() throws IOException {
        new PaymentTest().charSpecialsCreditorAccountSchemeName();
    }
    @Test(priority = 10)
    public void scenario10() throws IOException {
        new PaymentTest().charSpecialsCreditorAccountIdentification();
    }
    @Test(priority = 11)
    public void scenario11() throws IOException {
        new PaymentTest().charSpecialsCreditorAccountName();
    }
    @Test(priority = 12)
    public void scenario12() throws IOException {
        new PaymentTest().charSpecialsCreditorAccountSecondaryIdentification();
    }
    @Test(priority = 13)
    public void scenario13() throws IOException {
        new PaymentTest().paymentContextEcommerceGoods();
    }
    @Test(priority = 14)
    public void scenario14() throws IOException {
        new PaymentTest().isMissingBankId();
    }
    @Test(priority = 15)
    public void scenario15() throws IOException {
        new PaymentTest().requestSpecificationNoAvailableBalance();
    }
    @Test(priority = 16)
    public void scenario16() throws IOException {
        new PaymentTest().amountNegativeTransaction();
    }
    @Test(priority = 17)
    public void scenario17() throws IOException {
        new PaymentTest().bankIdNoExist();
    }
    @Test(priority = 18)
    public void scenario18() throws IOException {
        new PaymentTest().visibleFieldDomesticPaymentId();
    }
    @Test(priority = 19)
    public void scenario19() throws IOException {
        new PaymentTest().visibleFieldStatus();
    }
    @Test(priority = 20)
    public void scenario20() {
        new RecoverPaymentTest().isMissingXFapi();
    }
    @Test(priority = 21)
    public void scenario21() {
        new RecoverPaymentTest().isMissingBankId();
    }
    @Test(priority = 22)
    public void scenario22()  {
        new RecoverPaymentTest().isMissingDomesticPayment();
    }
    @Test(priority = 23)
    public void scenario23() {
        new RecoverPaymentTest().domesticPaymentUnRegistered();
    }
    @Test(priority = 24)
    public void scenario24() {
        new RecoverPaymentTest().visibleDomesticPaymentId();
    }
    @Test(priority = 25)
    public void scenario25()  {
        new RecoverPaymentTest().visibleFieldStatus();
    }
    @Test(priority = 26)
    public void scenario26()  {
        new RecoverPaymentTest().visibleFieldAmount();
    }
    @Test(priority = 27)
    public void scenario27() {
        new RecoverPaymentTest().visibleFieldDebtorAccountSchemeName();
    }
    @Test(priority = 28)
    public void scenario28() {
        new RecoverPaymentTest().visibleFieldDebtorAccountIdentification();
    }
    @Test(priority = 29)
    public void scenario29()  {
        new RecoverPaymentTest().visibleFieldCreditorAccountSchemeName();
    }
    @Test(priority = 30)
    public void scenario30() {
        new RecoverPaymentTest().visibleFieldCreditorAccountIdentification();
    }
    @Test(priority = 31)
    public void scenario31() {
        new RecoverPaymentTest().visibleFieldInstructionIdentification();
    }

}

