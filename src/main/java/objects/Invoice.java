package objects;

import java.util.List;

public class Invoice {

    private final List<InvoiceLine> invoiceLines;

    public Invoice(List<InvoiceLine> invoiceLines){
        this.invoiceLines = invoiceLines;
    }

    public List<InvoiceLine> getInvoiceLines() {
        return invoiceLines;
    }

}
