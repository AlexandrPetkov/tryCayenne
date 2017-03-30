package object.selector;

import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.query.ObjectSelect;
import org.example.cayenne.persistent.Contact;

import java.util.List;

public class MyObjectSelector {

    public static List<Contact> getAllContacts(ObjectContext context){
        List<Contact> contacts = null;

        contacts = ObjectSelect.query(Contact.class).select(context);

        return contacts;
    }
}
