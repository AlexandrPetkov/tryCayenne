import objectCreator.MyObjectCreator;
import objectManipulator.MyObjectManipulator;
import objectSelector.MyObjectSelector;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.configuration.server.ServerRuntime;
import org.example.cayenne.persistent.Contact;
import org.example.cayenne.persistent.Invoice;
import org.example.cayenne.persistent.Payment;

import java.util.List;


public class Main {
    public static void main(String[] args) throws InterruptedException {
        //getting context
        ServerRuntime testRuntime = ServerRuntime.builder()
                .addConfig("cayenne-CayenneModelerTest.xml").build();
        ObjectContext context = testRuntime.newContext();

        /*//creating and filling out DB with objects
        MyObjectCreator.createRandomContactObjects(context, 5);
        */

        List<Contact> contacts = MyObjectSelector.getAllContacts(context);
        setNewValuesForAllDBObjects(contacts);

        //saving created objects in DB
        context.commitChanges();

        contacts = MyObjectManipulator.calculateDebtsAndPays(contacts);
        contacts = MyObjectManipulator.sortContactsByDebt(contacts, true);

        System.out.println("Contact name\t\t\tEmail\t\t\t\t\tActual Debt\t\tSummary Payed");
        for (Contact contact : contacts){
            System.out.println(contact);
        }
    }

    /**
     * Creates 5 "Contact", 25 "Invoice" and 125 "Payment" objects
     * every "Contact" contains 5 "Invoices", every "Invoice" - 5 "Payments"
     */
    private static void createRandomTestObjects (ObjectContext context){
        MyObjectCreator.createRandomContactObjects(context, 5);
    }

    /*
     * Sets new random values to all non-key fields of all DB objects
     * starts with contacts and ends with payments
     */
    private static void setNewValuesForAllDBObjects (List<Contact> contacts){
        for (Contact contact : contacts){
            contact = MyObjectManipulator.setContactWithRandomValues(contact);

            for (Invoice invoice : contact.getInvoices()){
                invoice = MyObjectManipulator.setInvoiceWithRandomValues(invoice);

                for (Payment payment : invoice.getPayments()){
                    payment = MyObjectManipulator.setPaymentWithRandomValues(payment);
                }
            }
        }

    }





}
