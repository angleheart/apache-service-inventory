package objects;

import java.util.List;

public class Sequence {

    private final String sequenceName;
    private final String customerNumber;
    private final String vehicleDescription;
    private final String shipTo;
    private final String po;
    private final int counterPersonNumber;
    private final double freightTotal;
    private final List<SequenceLine> lines;

    public Sequence(
            String sequenceName,
            String customerNumber,
            String vehicleDescription,
            String shipTo,
            String po,
            int counterPersonNumber,
            double freightTotal,
            List<SequenceLine> lines) {
        this.sequenceName = sequenceName;
        this.customerNumber = customerNumber;
        this.vehicleDescription = vehicleDescription;
        this.shipTo = shipTo;
        this.po = po;
        this.counterPersonNumber = counterPersonNumber;
        this.freightTotal = freightTotal;
        this.lines = lines;
    }

    public String getSequenceName() {
        return sequenceName;
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

    public Double getFreightTotal() {
        return freightTotal;
    }

    public List<SequenceLine> getLines() {
        return lines;
    }

}
