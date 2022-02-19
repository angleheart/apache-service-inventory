package objects;

import util.NumberUtil;

public class Line {

    private final int qty;
    private final String mfg;
    private final String partNumber;
    private final String description;
    private final double listPrice;
    private final double unitPrice;
    private final String tx;

    public Line(
            int qty,
            String mfg,
            String partNumber,
            String description,
            double listPrice,
            double unitPrice,
            String tx) {
        this.qty = qty;
        this.mfg = mfg;
        this.partNumber = partNumber;
        this.description = description;
        this.listPrice = listPrice;
        this.unitPrice = unitPrice;
        this.tx = tx;
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

    public String getDescription() {
        return description;
    }

    public double getListPrice() {
        return listPrice;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public double getExtendedPrice(){
        return NumberUtil.currencyRound(qty * unitPrice);
    }

    public String getTx() {
        return tx;
    }

    public boolean isTaxed(){
        return tx.equalsIgnoreCase("T");
    }

}
