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

    public int getSummaryPaymentsAmount() {
        return summaryPaymentsAmount;
    }

    public int calculatePaymentsSum(){

        int sum = 0;
        List<Payment> payments = this.getPayments();

        for (Payment i : payments){
            sum += i.getAmount();
        }

        return sum;
    }

    public void refreshSummaryPaymentsAmount(){
        summaryPaymentsAmount = calculatePaymentsSum();
    }

    public void refreshDebt(){
        refreshSummaryPaymentsAmount();
        debt = getAmount() - summaryPaymentsAmount;
    }

}
