package ap.cayenne.learning.validations;

import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.configuration.server.ServerRuntime;
import org.example.cayenne.persistent.Contact;
import org.example.cayenne.persistent.Invoice;
import org.example.cayenne.persistent.Payment;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class tstPaymentValidation {
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
        Assert.assertTrue(PaymentValidation.isPaymentsValid(invoice));
    }
}
