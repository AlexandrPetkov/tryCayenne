package ap.cayenne.learning.listener;

import ap.cayenne.learning.listeners.OnSaveListener;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.configuration.server.ServerRuntime;
import org.apache.cayenne.query.ObjectSelect;
import org.example.cayenne.persistent.Contact;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LifeCycleListener {
    public static String message = null;
    private ServerRuntime runtime;
    private ObjectContext context;
    private OnSaveListener listener;

    @Before
    public void before(){
        runtime = ServerRuntime.builder().addConfig("cayenne-CayenneModelerTest.xml").build();
        context = runtime.newContext();
        listener = new OnSaveListener();
        runtime.getChannel().getEntityResolver().getCallbackRegistry().addListener(Contact.class, listener);
    }

    @Test
    public void TestLifeCycleListenerMethods(){

        //POST ADD check
        Contact contact = context.newObject(Contact.class);
        Assert.assertEquals("Post ADD", listener.getMessage());

        //filling contact bean
        contact.setName("somename");
        contact.setLastName("someLastname");
        contact.setEmail("some@gmail.com");

        //PRE & POST PERSIST check
        context.commitChanges();
        Assert.assertTrue(listener.isPrePersistCalled());
        Assert.assertEquals("Post PERSIST", listener.getMessage());


        //PRE & POST UPDATE check
        contact.setEmail("another@gmail.com");
        context.commitChanges();
        Assert.assertTrue(listener.isPreUpdateCalled());
        Assert.assertEquals("Post UPDATE", listener.getMessage());

        // POST LOAD check
        Contact loadedContact = ObjectSelect.query(Contact.class).selectFirst(runtime.newContext());
        Assert.assertEquals("Post LOAD", listener.getMessage());

        //PRE REMOVE check
        context.deleteObject(contact);
        Assert.assertEquals("Pre REMOVE", listener.getMessage());

        //POST REMOVE check
        context.commitChanges();
        Assert.assertEquals("Post REMOVE", listener.getMessage());
    }
}
