package ap.cayenne.learning.validations;

import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.configuration.server.ServerRuntime;
import org.example.cayenne.persistent.Invoice;
import org.example.cayenne.persistent.Payment;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class PaymentValidationTest {
    private ObjectContext context;
    private Invoice invoice;
    private Payment payment;

    @Before
    public void before(){
        ServerRuntime testRuntime = ServerRuntime.builder()
                .addConfig("cayenne-CayenneModelerTest.xml").build();
        context = testRuntime.newContext();

        invoice = context.newObject(Invoice.class);
        invoice.setAmount(500);

        for (int i = 0; i < 5; i++) {
            payment = context.newObject(Payment.class);
            payment.setAmount(30);
            payment.setInvoice(invoice);
        }

        invoice.setDebt(Invoice.calculatePaymentsSum(invoice.getPayments()));
     }

    @After
    public void after(){
        invoice = null;
        payment = null;
    }

    @Test
    public void validatePayment (){
        Invoice invoice = new Invoice();
        invoice.setDebt(0);
        Assert.assertTrue(PaymentValidation.isPaymentsValid(invoice));

        invoice = new Invoice();
        invoice.setDebt(100);
        Assert.assertTrue(PaymentValidation.isPaymentsValid(invoice));

        invoice = new Invoice();
        invoice.setDebt(-100);
        Assert.assertFalse(PaymentValidation.isPaymentsValid(invoice));
    }

    @Test
    public void countDebt(){
        List<Payment> payments = new ArrayList<>();

        for (int i = 0; i < 1; i++){
            Payment payment = new Payment(30);
            payments.add(payment);
        }


        Assert.assertEquals(1, payments.size());
    }
}
