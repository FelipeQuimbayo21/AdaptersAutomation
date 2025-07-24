package testAdapters;

import baseTest.BaseTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import payment.PaymentTest;
import recoverPayment.RecoverPaymentTest;
import utils.EnvironmentPropertiesCreator;

import java.io.IOException;

public class TestAdapters {
    @BeforeMethod
    public void setUp() throws Exception {
        BaseTest config = new BaseTest();
        config.setUp();
    }
    @Test
    public void paso1GeneratePayment() throws IOException, InterruptedException {
        new EnvironmentPropertiesCreator().environment();
        new PaymentTest().testPostPayment();
        Thread.sleep(6000);
    }
    @Test
    public void paso2RecoverPayment() throws IOException {
        new RecoverPaymentTest().testGetPayment();
    }
}
