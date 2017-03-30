package ap.cayenne.learning.validations;

import org.example.cayenne.persistent.Invoice;

public class PaymentValidation {

    public static boolean isPaymentsValid (Invoice invoice){
        return invoice.getDebt() < 0;
    }
}
