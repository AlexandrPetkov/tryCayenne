package org.example.cayenne.persistent;

import org.apache.cayenne.validation.BeanValidationFailure;
import org.apache.cayenne.validation.ValidationResult;
import org.example.cayenne.persistent.auto._Payment;

public class Payment extends _Payment {

    private static final long serialVersionUID = 1L;

    @Override
    protected void validateForSave(ValidationResult validationResult) {
        Invoice invoice = this.getInvoice();

        if (isPaymentSumGreaterInvoiceAmount(invoice)){
            validationResult.addFailure(new BeanValidationFailure(this, "Payments sum must be NOT greater than Invoice amount", Payment.class));
        }
        super.validateForSave(validationResult);
    }

    private boolean isPaymentSumGreaterInvoiceAmount(Invoice invoice){
        int paymentSum = 0;

        for (Payment payment : invoice.getPayments()){
            paymentSum += payment.getAmount();
        }

        return paymentSum > invoice.getAmount();
    }
}
