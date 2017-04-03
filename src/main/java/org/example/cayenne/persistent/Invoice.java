package org.example.cayenne.persistent;

import org.apache.cayenne.validation.BeanValidationFailure;
import org.apache.cayenne.validation.ValidationResult;
import org.example.cayenne.persistent.auto._Invoice;

import java.util.List;

public class Invoice extends _Invoice {

    private static final long serialVersionUID = 1L;

   @Override
    public void validateForInsert(ValidationResult validationResult) {
       int paymentsSum = 0;
       List<Payment> payments = this.getPayments();

       //counting sum of all payments of current invoice
       for (int i = 0; i < payments.size(); i++) {
           paymentsSum += payments.get(i).getAmount();
       }

       // checking if payments sum is greater than the invoice amount
        if (paymentsSum > this.getAmount()) {
            validationResult.addFailure(new BeanValidationFailure(this, AMOUNT.getName(), "Payments sum is greater than invoice amount"));
        }

        super.validateForInsert(validationResult);

    }
}
