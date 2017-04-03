package ap.cayenne.learning.functions;

import org.apache.cayenne.ObjectContext;

import org.apache.cayenne.configuration.server.ServerRuntime;
import org.apache.cayenne.query.SelectById;
import org.apache.cayenne.validation.ValidationException;
import org.example.cayenne.persistent.Contact;
import org.example.cayenne.persistent.Invoice;
import org.example.cayenne.persistent.Payment;
import org.junit.*;


public class InvoiceFunctionsTest {
    ServerRuntime runtime;
    private ObjectContext context;
    private Contact contact;

    @Before
    public void before(){
        runtime = ServerRuntime.builder()
                .addConfig("cayenne-CayenneModelerTest.xml").build();
        context = runtime.newContext();

        //we cant commit invoice without any contact related
        contact = ContactFunctions.createContact("name", "lastName", "gmail", context);

        context.commitChanges();

    }

    @After
    public void clearDB(){
        context.deleteObject(contact);
        context.commitChanges();
    }

    @Test
    public void testCreateInvoice (){

        Invoice actual = createInvoice(context, 500, contact);
        context.commitChanges();

        ObjectContext newContext = runtime.newContext();
        Invoice expected = SelectById.query(Invoice.class, actual.getObjectId()).selectOne(newContext);

        if (expected != null){
            Assert.assertEquals(expected.getObjectId(), actual.getObjectId());
        } else Assert.fail();

        newContext.deleteObject(expected);
        newContext.commitChanges();
    }


    @Test
    public void setOnInsertValidation(){
        //getting invoice
        Invoice invoice = createInvoice(context, 500, contact);

        //adding payments to invoice with summary amount LESS than invoice amount
        addPaymentsToInvoice(invoice, context, 249);
        context.commitChanges();

            //how to check???

        Invoice expected = SelectById.query(Invoice.class, invoice.getObjectId()).selectOne(context);
        Assert.assertEquals(expected, invoice);
    }

    @Test(expected = ValidationException.class)
    public void setOnInsertValidationException(){
        Invoice invoice = createInvoice(context, 500, contact);

        //adding payments to invoice with summary amount GREATER than invoice amount
        addPaymentsToInvoice(invoice, context, 251);
        context.commitChanges();
    }

    /*
     * creates and returns new invoice
     * sets the amount of new invoice with 500
     * adds new invoice to the "contact" variable
     */
    private Invoice createInvoice(ObjectContext context, int amount, Contact contact) {
        Invoice actual = context.newObject(Invoice.class);

        //filling our test invoice
        actual.setAmount(amount);
        actual.setContact(contact);

        //getting invoice
        return actual;
    }

    /*
     * creating 2 payments and setting them to an invoice
     *
     * int paymentAmount - amount that will be set to every created payment
     * Invoice invoice - created payments will be set to that invoice
     * ObjectContext context - its the context to put payments
     */
    private void addPaymentsToInvoice (Invoice invoice, ObjectContext context, int paymentAmount){
        for (int i = 0; i < 2; i++) {
            Payment payment = context.newObject(Payment.class);
            payment.setAmount(paymentAmount);
            payment.setInvoice(invoice);
        }
    }
}
