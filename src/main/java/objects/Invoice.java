package objects;

import java.util.List;

public class Invoice extends Sequence {

    private final int invoiceNumber;
    private final long releaseTime;
    private final int releaseCode;
    private final double balance;
    private final int accountingPeriod;

    public Invoice(
            int invoiceNumber,
            long releaseTime,
            int releaseCode,
            double balance,
            int accountingPeriod,
            String customerNumber,
            String vehicleDescription,
            String shipTo,
            String po,
            int counterPersonNumber,
            double freightTotal,
            double taxRate,
            List<Line> lines) {
        super(customerNumber, vehicleDescription, shipTo, po, counterPersonNumber, freightTotal, taxRate, lines);
        this.invoiceNumber = invoiceNumber;
        this.releaseTime = releaseTime;
        this.releaseCode = releaseCode;
        this.balance = balance;
        this.accountingPeriod = accountingPeriod;
    }

    public int getInvoiceNumber() {
        return invoiceNumber;
    }

    public long getReleaseTime() {
        return releaseTime;
    }

    public int getReleaseCode() {
        return releaseCode;
    }

    public double getBalance() {
        return balance;
    }

    public int getAccountingPeriod(){
        return accountingPeriod;
    }

}
