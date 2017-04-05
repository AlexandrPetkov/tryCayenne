package ap.cayenne.learning.listeners;

import org.apache.cayenne.LifecycleListener;
import org.apache.cayenne.PersistenceState;
import org.example.cayenne.persistent.Contact;
import org.example.cayenne.persistent.Invoice;
import org.example.cayenne.persistent.Payment;

public class MyLifecycleListener implements LifecycleListener {
    private Invoice currentInvoice = new Invoice();
    private String message = null;
    private boolean isPrePersistCalled = false;
    private boolean isPreUpdateCalled = false;

    public boolean isPrePersistCalled() {
        return isPrePersistCalled;
    }

    private void setPrePersistCalled(boolean prePersistCalled) {
        isPrePersistCalled = prePersistCalled;
    }

    public boolean isPreUpdateCalled() {
        return isPreUpdateCalled;
    }

    private void setPreUpdateCalled(boolean preUpdateCalled) {
        isPreUpdateCalled = preUpdateCalled;
    }

    public String getMessage() {
        return message;
    }
    private void setMessage(String message){
        this.message = message;
        System.out.println(message);
    }

    @Override
    public void postAdd(Object entity) {
        setMessage("Post ADD");
    }

    @Override
    public void prePersist(Object entity) {

        if (entity instanceof Contact){
            setMessage("Pre PERSIST");
            setPrePersistCalled(true);
        }
        if (entity instanceof Payment) {

            doPrePersistPayment((Payment) entity);

         }
    }

    private void doPrePersistPayment(Payment entity) {
        Invoice invoice = entity.getInvoice();

        //this check allows to run the code one time to every invoice (not to every payment in invoice)
        if (!currentInvoice.equals(invoice)){
            currentInvoice = invoice;

            int paymentSum = 0;

            for (Payment p : invoice.getPayments()){
                paymentSum += p.getAmount();
            }

            //if payments sum is greater than invoice amount set all payments of that invoice
            // in persistent state - MODIFIED (that will block adding this payments)
            if (paymentSum > invoice.getAmount()){
                for (Payment p : invoice.getPayments()){
                    p.setPersistenceState(PersistenceState.COMMITTED);
                }
            }
        }
    }

    @Override
    public void postPersist(Object entity) {
        setMessage("Post PERSIST");

    }

    @Override
    public void preRemove(Object entity) {
        setMessage("Pre REMOVE");

    }

    @Override
    public void postRemove(Object entity) {
        setMessage("Post REMOVE");

    }

    @Override
    public void preUpdate(Object entity) {
        setMessage("Pre UPDATE");
        setPreUpdateCalled(true);

    }

    @Override
    public void postUpdate(Object entity) {
        setMessage("Post UPDATE");

    }

    @Override
    public void postLoad(Object entity) {
        setMessage("Post LOAD");

    }
}
