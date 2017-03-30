package objectManipulator;

import org.example.cayenne.persistent.Contact;
import org.example.cayenne.persistent.Invoice;
import org.example.cayenne.persistent.Payment;
import randomGenerator.MyRandomGenerator;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MyObjectManipulator {

    public static List<Contact> sortContactsByDebt(List<Contact> contacts, boolean isDesc){
        Collections.sort(contacts, new Comparator<Contact>() {
            @Override
            public int compare(Contact o1, Contact o2) {
                int result = o2.getSummaryDebt() - o1.getSummaryDebt();

                return isDesc ? result : result * -1;
            }
        });

        return contacts;
    }

    public static List<Contact> calculateDebtsAndPays(List<Contact> contacts){
        for (Contact contact : contacts){
            calculateContactDebtAndPay(contact);
        }
        return contacts;
    }

    private static Contact calculateContactDebtAndPay(Contact contact) {
        contact.refreshSummaryPayed();
        contact.refreshSummaryDebt();

        return contact;
    }


    public static Payment setPaymentWithRandomValues (Payment payment){

        payment.setAmount((int) (Math.random() * 100));

        return payment;
    }


    public static Invoice setInvoiceWithRandomValues (Invoice invoice){

        invoice.setDescription(MyRandomGenerator.generateRandomString(20, 255));
        invoice.setAmount((int)(Math.random() * 500));

        return invoice;
    }

    public static Contact setContactWithRandomValues (Contact contact){

        contact.setName(MyRandomGenerator.generateRandomString(5, 10));
        contact.setLastName(MyRandomGenerator.generateRandomString(5, 10));
        contact.setEmail(MyRandomGenerator.generateRandomString(5, 10) + "@gmail.com");

        return contact;
    }
}
