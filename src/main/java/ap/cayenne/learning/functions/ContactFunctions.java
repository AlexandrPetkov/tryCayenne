package ap.cayenne.learning.functions;

import org.apache.cayenne.ObjectContext;
import org.example.cayenne.persistent.Contact;

public class ContactFunctions {
    public static Contact createContact(String name, String lastName, String email, ObjectContext context) {
        Contact contact = context.newObject(Contact.class);
        contact.setName(name);
        contact.setLastName(lastName);
        contact.setEmail(email);

        return contact;
    }
}
