package ap.cayenne.learning.validations;

import object.manipulator.MyObjectManipulator;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.configuration.server.ServerRuntime;
import org.example.cayenne.persistent.Invoice;
import org.example.cayenne.persistent.Payment;
import org.junit.After;
import org.junit.Before;

public class InsertValidationTest {
    ObjectContext context = ServerRuntime.builder()
            .addConfig("cayenne-CayenneModelerTest.xml").build().newContext();
    Invoice invoice = new Invoice();

    @Before
    public void before (){

        invoice = MyObjectManipulator.setInvoiceWithRandomValues(invoice);

        for (int i = 0; i < 5; i++) {
            Payment payment = new Payment();
            payment = MyObjectManipulator.setPaymentWithRandomValues(payment);
            payment.setInvoice(invoice);
        }

        context.registerNewObject(invoice);
    }

    @After
    public void after(){
        context.invalidateObjects(invoice);
        //deleteAllRecords();
    }

    public void validatePaymentsOnInsert(){


    }
}
