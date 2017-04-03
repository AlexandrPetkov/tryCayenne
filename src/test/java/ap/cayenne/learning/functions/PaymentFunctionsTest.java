package ap.cayenne.learning.functions;

import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.Persistent;
import org.apache.cayenne.configuration.server.ServerRuntime;
import org.apache.cayenne.query.SelectById;
import org.example.cayenne.persistent.Contact;
import org.example.cayenne.persistent.Invoice;
import org.example.cayenne.persistent.Payment;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

public class PaymentFunctionsTest {
    private ServerRuntime runtime;
    private ObjectContext context;
    private List<Persistent> objects;
    private Invoice invoice;

    @Before
    public void before(){
        runtime = ServerRuntime.builder().addConfig("cayenne-CayenneModelerTest.xml").build();
        context = runtime.newContext();
        objects = new ArrayList<>();

        Contact contact = ContactFunctions.createContact("testName", "testLastName", "test@gmail.com", context);
        objects.add(contact);

        invoice = InvoiceFunctions.createInvoice(context, 600);
        invoice.setContact(contact);
        objects.add(invoice);

        context.commitChanges();
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
}
