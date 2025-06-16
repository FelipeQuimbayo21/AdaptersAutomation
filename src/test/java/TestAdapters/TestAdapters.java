package TestAdapters;

import TestAPIS.TestGetPayment;
import TestAPIS.TestPostPaymentInitiation;
import org.testng.annotations.Test;

public class TestAdapters {
    @Test(priority = 1)
    public void generatePayment() throws Exception {
        new TestPostPaymentInitiation().testPostPayment();
    }
    @Test(priority = 2)
    public void generateConsultPayment() throws Exception {
        new TestGetPayment().testGetPayment();
    }
}
