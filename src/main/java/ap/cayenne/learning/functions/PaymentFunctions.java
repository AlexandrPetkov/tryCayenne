package ap.cayenne.learning.functions;

import org.apache.cayenne.ObjectContext;
import org.example.cayenne.persistent.Payment;

public class PaymentFunctions {

    public static Payment createPayment(ObjectContext context){
        Payment payment;

        payment = createPayment(0, context);

        return payment;
    }

    public static Payment createPayment (int amount, ObjectContext context){
        Payment payment;

        payment = context.newObject(Payment.class);
        payment.setAmount(amount);

        return payment;
    }
}
