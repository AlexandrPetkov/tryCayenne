package ap.cayenne.learning.functions;

import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.Persistent;
import org.apache.cayenne.configuration.server.ServerRuntime;
import org.apache.cayenne.query.SelectById;
import org.apache.cayenne.validation.ValidationException;
import org.example.cayenne.persistent.Contact;
import org.example.cayenne.persistent.Invoice;
import org.example.cayenne.persistent.Payment;
import org.junit.*;

import java.util.ArrayList;
import java.util.List;


public class InvoiceFunctionsTest {
    private ObjectContext context;
    private List<Persistent> objects = new ArrayList<>();
    private List<Payment> payments = new ArrayList<>();
    private Contact contact;

    @Before
    public void before(){
        context = ServerRuntime.builder()
                .addConfig("cayenne-CayenneModelerTest.xml").build().newContext();

        //we cant commit invoice without any contact related
        contact = context.newObject(Contact.class);

        //contact must contain some data
        contact.setEmail("gmail");
        contact.setName("name");
        contact.setLastName("lastname");

        //its for delete contact from DB after test

        context.commitChanges();
        objects.add(contact);

    }

    @After
    public void clearDB(){
        context.deleteObjects(objects);
        context.commitChanges();
    }

    @Test
    public void testCreateInvoice (){

        Invoice actual = createInvoice();

        context.commitChanges();
        //getting invoice from DB by id
        Invoice expected = SelectById.query(Invoice.class, actual.getObjectId()).selectOne(context);

        Assert.assertEquals(expected, actual);
    }

    private Invoice createInvoice() {
        Invoice actual = context.newObject(Invoice.class);

        //filling our test invoice
        actual.setAmount(500);
        actual.setContact(contact);
        actual.setDescription("Some description");

        //getting invoice
        return actual;
    }

    @Test
    public void setOnInsertValidation(){
        //getting invoice
        Invoice invoice = createInvoice();

        //adding payments to invoice with summary amount LESS than invoice amount
        for (int i = 0; i < 2; i++) {
            Payment payment = context.newObject(Payment.class);
            payment.setAmount(249);
            payment.setInvoice(invoice);
            payments.add(payment);
        }
        context.commitChanges();

        Invoice expected = SelectById.query(Invoice.class, invoice.getObjectId()).selectOne(context);

        Assert.assertEquals(expected, invoice);
    }

    @Test(expected = ValidationException.class)
    public void setOnInsertValidationException(){

        Invoice invoice = createInvoice();

        //adding payments to invoice with summary amount greater than invoice amount
        for (int i = 0; i < 2; i++) {
            Payment payment = context.newObject(Payment.class);
            payment.setAmount(251);
            payment.setInvoice(invoice);
            payments.add(payment);
        }
        context.commitChanges();
    }
}
