package objectManipulator;

import org.example.cayenne.persistent.Contact;
import org.example.cayenne.persistent.Invoice;
import org.example.cayenne.persistent.Payment;
import randomGenerator.MyRandomGenerator;

import java.util.List;

public class MyObjectManipulator {

    public static List<Contact> sortContactsByDebt(List<Contact> contacts, boolean isDesc){


        return contacts;
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
