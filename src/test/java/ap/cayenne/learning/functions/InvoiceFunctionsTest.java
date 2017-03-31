package ap.cayenne.learning.functions;

import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.configuration.server.ServerRuntime;
import org.apache.cayenne.query.SelectById;
import org.apache.cayenne.validation.ValidationResult;
import org.example.cayenne.persistent.Contact;
import org.example.cayenne.persistent.Invoice;
import org.example.cayenne.persistent.Payment;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;


public class InvoiceFunctionsTest {
    ObjectContext context = ServerRuntime.builder()
            .addConfig("cayenne-CayenneModelerTest.xml").build().newContext();
    Object object;

    /*@After
    public void clearDB(){
        context.deleteObject(object);
        context.commitChanges();
    }*/

    @Test
    public void createInvoice (){
        Invoice actual = new Invoice();

        //we cant commit invoice without any contact related
        Contact contact = new Contact();

        //its for delete contact from DB after test
        object = contact;

        //contact must contain some data
        context.registerNewObject(contact);
        contact.setEmail("1221111");
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

        //its for delete contact from DB after test
        object = contact;

        //contact must contain some data
        context.registerNewObject(contact);
        contact.setEmail("1221111");
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

        int sumAmount = 0;

        for (Payment p : actual.getPayments()){
            sumAmount += p.getAmount();
        }

        context.commitChanges();
    }
}
