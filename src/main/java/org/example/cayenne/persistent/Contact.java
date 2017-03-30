package org.example.cayenne.persistent;

import org.example.cayenne.persistent.auto._Contact;

public class Contact extends _Contact {

    private static final long serialVersionUID = 1L;
    private int summaryDebt;
    private int summaryPayed;


  /*  public void validateForInsert(ValidationResult validationResult){



        validationResult.addFailure(new ValidationFailure() {
            @Override
            public Object getSource() {
                return null;
            }

            @Override
            public Object getError() {
                return null;
            }

            @Override
            public String getDescription() {
                return null;
            }
        });
        System.out.println(validationResult);
    }
*/


    public int getSummaryDebt() {
        return summaryDebt;
    }

    public int getSummaryPayed() {
        return summaryPayed;
    }

    public int calculateSummaryDebt() {
        int summaryDebt = 0;

        for (Invoice invoice : getInvoices()){

            invoice.refreshDebt();
            summaryDebt += invoice.getDebt();
        }

        return summaryDebt;
    }

    public void refreshSummaryDebt(){
        summaryDebt = calculateSummaryDebt();
    }

    public int calculateSummaryPayed() {
        int sumPayed = 0;

        for (Invoice invoice : getInvoices()){

            invoice.refreshSummaryPaymentsAmount();
            sumPayed += invoice.getSummaryPaymentsAmount();
        }

        return sumPayed;
    }

    public void refreshSummaryPayed(){
        summaryPayed = calculateSummaryPayed();
    }


    @Override
    public String toString() {

        return String.format("%-21s\t%-20s\t%-14d\t%-13d", getName() + " "
                + getLastName(), getEmail(), getSummaryDebt(), getSummaryPayed());
    }
}
