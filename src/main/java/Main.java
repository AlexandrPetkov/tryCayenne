import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.configuration.server.ServerRuntime;
import org.example.cayenne.persistent.Contact;
import org.example.cayenne.persistent.Invoice;
import org.example.cayenne.persistent.Payment;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //getting context
        ServerRuntime testRuntime = ServerRuntime.builder()
                .addConfig("cayenne-CayenneModelerTest.xml").build();
        ObjectContext context = testRuntime.newContext();

        //creating and filling out DB with objects
        for (int i = 0; i < 5; i++) {
            Contact contact = context.newObject(Contact.class);

            contact.setName("name:" + i);
            contact.setLastName("lastname:" + i);
            contact.setEmail(contact.getName() + "." + contact.getLastName() + "@gmail.com");

            for (int j = 0; j < 5; j++) {
                Invoice invoice = context.newObject(Invoice.class);

                invoice.setContact(contact);
                invoice.setDescription("description #" + j);

                for (int k = 0; k < 5; k++) {
                    Payment payment = context.newObject(Payment.class);

                    payment.setInvoice(invoice);
                    payment.setAmount(100+j);
                }

                invoice.setAmount(paymentsSum(invoice.getPayments()));
            }
        }

        //saving created objects in DB
        context.commitChanges();
    }

    /**
     * counts and returns sum of payments amounts
     */
    private static int paymentsSum (List<Payment> payments){
        int sum = 0;

        for (int i = 0; i < payments.size(); i++) {
            sum+=payments.get(i).getAmount();
        }

        return sum;
    }
}
