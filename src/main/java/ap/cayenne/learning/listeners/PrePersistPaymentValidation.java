package ap.cayenne.learning.listeners;

import org.apache.cayenne.annotation.PrePersist;
import org.apache.cayenne.annotation.PreUpdate;
import org.example.cayenne.persistent.Payment;

public class PrePersistPaymentValidation {

    @PreUpdate(Payment.class)
    @PrePersist(Payment.class)
    public void preSave(Payment payment){

    }
}
