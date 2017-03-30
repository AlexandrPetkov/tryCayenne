package ap.cayenne.learning.functions;

import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.configuration.server.ServerRuntime;
import org.apache.cayenne.query.SelectById;
import org.example.cayenne.persistent.Contact;
import org.junit.*;

import java.util.LinkedList;
import java.util.List;

public class ContactFunctionsTest {
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

        context.deleteObjects(creates);

    }

    @Ignore
    @Test()
    public void test1() {
        Contact contact = ContactFunctions.createContact("Name", "LastName", "Email", context);
        creates.add(contact);

        Contact actual = SelectById.query(Contact.class, contact.getObjectId()).selectOne(testRuntime.newContext());
        Assert.assertEquals(contact.getObjectId(), actual.getObjectId());
    }

}
