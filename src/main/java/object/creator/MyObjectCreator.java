package object.creator;

import org.apache.cayenne.ObjectContext;
import org.example.cayenne.persistent.Contact;
import org.example.cayenne.persistent.Invoice;
import org.example.cayenne.persistent.Payment;
import ap.cayenne.learning.functions.lang.APRandomFunctions;

import java.util.List;

public class MyObjectCreator {
    /*
    * Creates "Contact" objects and calls method of creation 5 "Invoice" objects to every "Contact"
    * Fills "Contact" objects with random data
    *
    * int quantity - number of objects witch will be created
    * ObjectContext context - need to create new "Contact" object
    *
    * name and lastName generates random charset from 5 to 25 characters
    * email generates random charset from 5 to 25 characters plus "@gmail.com"
    */
    public static void createRandomContactObjects (ObjectContext context, int quantity){
        for (int i = 0; i < quantity; i++) {

            Contact contact = createContact(context);

            createRandomInvoiceObjects(context, contact, 5);
        }
    }

    private static Contact createContact(ObjectContext context) {
        Contact contact =


                context.newObject(Contact.class);

        return contact;
    }

    /*
    * Creates "Invoice" objects and calls method of creation 5 "Payment" objects to every "Invoice"
    * Fills "Invoice" objects with random data
    *
    * int quantity - number of objects witch will be created
    * Contact contact - that object will be added to every created "Invoice" object
    * ObjectContext context - need to create new "Invoice" object
    *
    * description generates random charset from 20 to 255 characters
    * amount generates random value in range from 0 to 500 inclusive
    */
    public static void createRandomInvoiceObjects (ObjectContext context, Contact contact, int quantity){
        for (int i = 0; i < quantity; i++) {
            Invoice invoice = createInvoice(context, contact);

            createRandomPaymentObjects(context, invoice, 5);

        }
    }

    private static Invoice createInvoice(ObjectContext context, Contact contact) {
        Invoice invoice = context.newObject(Invoice.class);

        invoice.setContact(contact);
        invoice.setDescription(APRandomFunctions.generateRandomString(20, 255));
        invoice.setAmount((int)Math.random() * 500);
        return invoice;
    }

    /*
     * Creates and fills "Payment" objects with random data
     *
     * int quantity - number of objects witch will be created
     * Invoice invoice - that object will be added to every created "Payment" object
     * ObjectContext context - need to create new "Payment" object
     *
     * amount generates random value in range from 0 to 100 inclusive
     */
    public static void createRandomPaymentObjects (ObjectContext context, Invoice invoice, int quantity){
        for (int i = 0; i < quantity; i++) {
            createPayment(context, invoice);
        }
    }

    private static Payment createPayment(ObjectContext context, Invoice invoice) {
        Payment payment = context.newObject(Payment.class);

        payment.setInvoice(invoice);
        payment.setAmount((int) Math.random() * 100);

        return payment;
    }
}
