package ap.cayenne.learning.functions;

import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.configuration.server.ServerRuntime;
import org.apache.cayenne.query.SelectById;
import org.apache.cayenne.validation.ValidationException;
import org.example.cayenne.persistent.Contact;
import org.example.cayenne.persistent.Invoice;
import org.example.cayenne.persistent.Payment;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class InvoiceFunctionsTest {
    static ObjectContext context = ServerRuntime.builder()
            .addConfig("cayenne-CayenneModelerTest.xml").build().newContext();
    static List<Object> objects = new ArrayList<>();

    @AfterClass
    public static void clearDB(){
        context.deleteObjects(objects);
        context.commitChanges();
    }

    @Test
    public void createInvoice (){
        Invoice actual = new Invoice();

        //we cant commit invoice without any contact related
        Contact contact = new Contact();

        //its for delete contact from DB after test
        objects.add(contact);

        //contact must contain some data
        context.registerNewObject(contact);
        contact.setEmail("gmail");
        contact.setName("name");
        contact.setLastName("lastname");

        //filling our test invoice
        actual.setAmount(500);
        actual.setContact(contact);
        actual.setDescription("Some description");

        context.commitChanges();

        //getting invoice from DB by id
        Invoice expected = SelectById.query(Invoice.class, actual.getObjectId()).selectOne(context);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void setOnInsertValidation(){
        Invoice actual = new Invoice();

        //we cant commit invoice without any contact related
        Contact contact = new Contact();

        //its to delete contact from DB after test
        objects.add(contact);

        //contact must contain some data
        context.registerNewObject(contact);
        contact.setEmail("12711");
        contact.setName("name");
        contact.setLastName("lastname");

        //filling our test invoice
        actual.setAmount(500);
        actual.setContact(contact);
        actual.setDescription("Some description");

        for (int i = 0; i < 2; i++) {
            Payment payment = context.newObject(Payment.class);
            payment.setAmount(249);
            payment.setInvoice(actual);
        }
        context.commitChanges();

        Invoice expected = SelectById.query(Invoice.class, actual.getObjectId()).selectOne(context);

        Assert.assertEquals(expected, actual);
    }

    @After
    public void clearContact(){
        context.deleteObjects(objects);
        context.commitChanges();
    }

    @Test(expected = ValidationException.class)
    public void setOnInsertValidationException(){
        Invoice actual = new Invoice();

        //we cant commit invoice without any contact related
        Contact contact = new Contact();

        //its to delete contact from DB after test
        objects.add(contact);

        //contact must contain some data
        context.registerNewObject(contact);
        contact.setEmail("1281");
        contact.setName("name");
        contact.setLastName("lastname");

        //filling our test invoice
        actual.setAmount(500);
        actual.setContact(contact);
        actual.setDescription("Some description");

        for (int i = 0; i < 2; i++) {
            Payment payment = context.newObject(Payment.class);
            payment.setAmount(251);
            payment.setInvoice(actual);
        }
        context.commitChanges();
    }
}
