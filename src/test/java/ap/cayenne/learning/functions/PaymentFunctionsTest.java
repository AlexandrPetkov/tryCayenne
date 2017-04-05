package ap.cayenne.learning.functions;

import ap.cayenne.learning.listeners.MyLifecycleListener;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.Persistent;
import org.apache.cayenne.configuration.server.ServerRuntime;
import org.apache.cayenne.query.SelectById;
import org.example.cayenne.persistent.Contact;
import org.example.cayenne.persistent.Invoice;
import org.example.cayenne.persistent.Payment;
import org.junit.*;

import java.util.ArrayList;
import java.util.List;

public class PaymentFunctionsTest {
    private ServerRuntime runtime;
    private ObjectContext context;
    private List<Persistent> objects;
    private Invoice invoice;
    Contact contact;

    @Before
    public void before(){
        runtime = ServerRuntime.builder().addConfig("cayenne-CayenneModelerTest.xml").build();
        context = runtime.newContext();
        objects = new ArrayList<>();

        contact = ContactFunctions.createContact("testName", "testLastName", "test@gmail.com", context);
        objects.add(contact);

        invoice = InvoiceFunctions.createInvoice(context, 600, "1st invoice");
        invoice.setContact(contact);
        objects.add(invoice);
    }

    @After
    public void after(){
        context.deleteObjects(objects);
        context.commitChanges();
    }

    @Test
    public void testCreatePayment(){
        Payment payment = context.newObject(Payment.class);
        payment.setInvoice(invoice);
        objects.add(payment);


        context.commitChanges();

        ObjectContext newContext = runtime.newContext();
        Payment expected = SelectById.query(Payment.class, payment.getObjectId()).selectOne(newContext);

        if (expected == null){
            Assert.fail();
        } else {
            Assert.assertEquals(expected.getObjectId(), payment.getObjectId());
        }
    }

    @Ignore
    @Test()
    public void TestValidateOnSaveListenerWork (){
        runtime.getChannel().getEntityResolver().getCallbackRegistry().addListener(Payment.class, new MyLifecycleListener());
        addPaymentsToInvoice(invoice, 250, 2);

        Invoice inv = InvoiceFunctions.createInvoice(context, 500, "2d invoice");
        inv.setContact(contact);
        objects.add(inv);

        addPaymentsToInvoice(inv, 40, 4);

        context.commitChanges();
    }

    /*
 * creating 2 payments and setting them to an invoice
 *
 * int paymentAmount - amount that will be set to every created payment
 * Invoice invoice - created payments will be set to that invoice
 * ObjectContext context - its the context to put payments
 */
    private void addPaymentsToInvoice (Invoice invoice, int paymentAmount, int quantity){
        for (int i = 0; i < quantity; i++) {
            Payment payment = context.newObject(Payment.class);
            payment.setAmount(paymentAmount);
            payment.setInvoice(invoice);
            objects.add(payment);
        }
    }
}
