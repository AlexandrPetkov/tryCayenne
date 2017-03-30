package org.example.cayenne.persistent;

import org.example.cayenne.persistent.auto._Invoice;

import java.util.List;

public class Invoice extends _Invoice {

    private static final long serialVersionUID = 1L;
    private int debt;
    private int summaryPaymentsAmount;

    public int getDebt() {
        return debt;
    }

    public void setDebt(int debt) {
        this.debt = debt;
    }

    public int getSummaryPaymentsAmount() {
        return summaryPaymentsAmount;
    }

    public static int calculatePaymentsSum(List<Payment> payments){

        int sum = 0;

        for (Payment i : payments){
            sum += i.getAmount();
        }

        return sum;
    }

    public void refreshSummaryPaymentsAmount(){
        summaryPaymentsAmount = calculatePaymentsSum(getPayments());
    }

    public void refreshDebt(){
        refreshSummaryPaymentsAmount();
        debt = getAmount() - summaryPaymentsAmount;
    }

}
