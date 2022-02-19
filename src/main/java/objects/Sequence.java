package objects;

import util.NumberUtil;

import java.util.List;

public class Sequence {

    private final String customerNumber;
    private final String vehicleDescription;
    private final String shipTo;
    private final String po;
    private final int counterPersonNumber;
    private final double freightTotal;
    private final double taxRate;
    private final List<Line> lines;

    public Sequence(
            String customerNumber,
            String vehicleDescription,
            String shipTo,
            String po,
            int counterPersonNumber,
            double freightTotal,
            double taxRate,
            List<Line> lines
    ) {
        this.customerNumber = customerNumber;
        this.vehicleDescription = vehicleDescription;
        this.shipTo = shipTo;
        this.po = po;
        this.counterPersonNumber = counterPersonNumber;
        this.freightTotal = freightTotal;
        this.taxRate = taxRate;
        this.lines = lines;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public String getVehicleDescription() {
        return vehicleDescription;
    }

    public String getShipTo() {
        return shipTo;
    }

    public String getPo() {
        return po;
    }

    public int getCounterPersonNumber() {
        return counterPersonNumber;
    }

    public double getFreightTotal() {
        return freightTotal;
    }

    public List<Line> getLines() {
        return lines;
    }

    public double getTaxableNet() {
        double netTaxable = 0;
        for (Line line : getLines())
            if (line.isTaxed())
                netTaxable += line.getExtendedPrice();
        return netTaxable;
    }

    public double getNonTaxableNet() {
        double netNonTaxable = 0;
        for (Line line : getLines())
            if (!line.isTaxed())
                netNonTaxable += line.getExtendedPrice();
        return netNonTaxable;
    }

    public double getSubTotal(){
        return getTaxableNet() + getNonTaxableNet() - getFreightTotal();
    }

    public double getTaxTotal(){
        return NumberUtil.currencyRound(getTaxableNet()*taxRate);
    }

    public double getGrandTotal(){
        return getSubTotal() + getTaxTotal() + getFreightTotal();
    }

}
