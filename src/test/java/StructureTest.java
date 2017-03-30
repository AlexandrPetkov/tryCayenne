import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.configuration.server.ServerRuntime;
import org.apache.cayenne.query.SelectById;
import org.example.cayenne.persistent.Contact;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

public class StructureTest {
    private ServerRuntime testRuntime;
    private ObjectContext context;

    private List<Contact> creates = new LinkedList<>();

    @Before
    public void before() {
        testRuntime = ServerRuntime.builder()
                .addConfig("cayenne-CayenneModelerTest.xml").build();

        context = testRuntime.newContext();
    }

    @After
    public void after() {

        context.deleteObject(creates);

    }

    @Test
    public void test1() {
        Contact contact = context.newObject(Contact.class);
        contact.setName("Name");
        contact.setLastName("LastName");
        contact.setEmail("Name.LastName@LastName.com");

        context.commitChanges();
        creates.add(contact);

        Contact actual = SelectById.query(Contact.class, contact.getObjectId()).selectOne(testRuntime.newContext());
        Assert.assertEquals(contact, actual);
    }



    @Test
    public void test2() {
        System.out.println("test2");
    }

}
