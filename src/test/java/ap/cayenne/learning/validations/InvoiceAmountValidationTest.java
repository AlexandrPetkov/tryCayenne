package ap.cayenne.learning.validations;

import ap.cayenne.learning.functions.ContactFunctions;
import ap.cayenne.learning.functions.InvoiceFunctions;
import ap.cayenne.learning.functions.PaymentFunctions;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.Persistent;
import org.apache.cayenne.configuration.server.ServerRuntime;
import org.apache.cayenne.validation.ValidationException;
import org.example.cayenne.persistent.Contact;
import org.example.cayenne.persistent.Invoice;
import org.example.cayenne.persistent.Payment;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

public class InvoiceAmountValidationTest {

    ServerRuntime runtime;
    ObjectContext context;
    List<Persistent> objects;
    Contact contact;
    Invoice invoice;

    @Before
    public void before(){
        runtime = ServerRuntime.builder().addConfig("cayenne-CayenneModelerTest.xml").build();
        context = runtime.newContext();
        objects = new ArrayList<>();

        contact = ContactFunctions.createContact("name", "surname", "mail", context);
        objects.add(contact);
        invoice = InvoiceFunctions.createInvoice(context, 500);
        invoice.setContact(contact);
        objects.add(invoice);
        context.commitChanges();

    }

    @After
    public void after(){
        context.deleteObjects(objects);
        context.commitChanges();
    }

    @Test(expected = ValidationException.class)
    public void onSaveValidationException(){
        createPayments(6);
        context.commitChanges();
    }

    private List<Payment> createPayments (int quantity){
        List<Payment> payments = new ArrayList<>();

        for (int i = 0; i < quantity; i++) {
            Payment payment = PaymentFunctions.createPayment(100, context);
            payment.setInvoice(invoice);
            objects.add(payment);
            payments.add(payment);
        }

        return payments;
    }
}
