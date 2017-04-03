package ap.cayenne.learning.functions;

import org.apache.cayenne.ObjectContext;
import org.example.cayenne.persistent.Invoice;

public class InvoiceFunctions {

    public static Invoice createInvoice (ObjectContext context, int amount){

        Invoice invoice = createInvoice(context, amount, null);
        return invoice;
    }

    public static Invoice createInvoice (ObjectContext context, int amount, String description){
        Invoice invoice = context.newObject(Invoice.class);

        invoice.setAmount(amount);
        invoice.setDescription(description);

        return invoice;
    }
}
