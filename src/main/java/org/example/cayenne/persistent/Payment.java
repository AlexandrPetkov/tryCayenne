package org.example.cayenne.persistent;

import org.apache.cayenne.validation.ValidationResult;
import org.example.cayenne.persistent.auto._Payment;

public class Payment extends _Payment {

    private static final long serialVersionUID = 1L;

    @Override
    public void validateForInsert(ValidationResult validationResult) {

        super.validateForInsert(validationResult);
    }
}
