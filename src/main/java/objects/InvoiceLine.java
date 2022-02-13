package objects;

public class InvoiceLine {

    private final int qty;
    private final String mfg;
    private final String partNumber;

    public InvoiceLine(int qty, String mfg, String partNumber) {
        this.qty = qty;
        this.mfg = mfg;
        this.partNumber = partNumber;
    }

    public int getQty() {
        return qty;
    }

    public String getMfg() {
        return mfg;
    }

    public String getPartNumber() {
        return partNumber;
    }

}
