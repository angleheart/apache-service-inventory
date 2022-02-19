package objects;

import java.util.List;

public class Sequence {

    private String sequenceName;
    private long creationTime;

    private final String customerNumber;
    private final String vehicleDescription;
    private final String shipTo;
    private final String po;
    private final int counterPersonNumber;
    private final double freightTotal;
    private final List<Line> lines;

    public Sequence(
            String customerNumber,
            String vehicleDescription,
            String shipTo,
            String po,
            int counterPersonNumber,
            double freightTotal,
            List<Line> lines) {
        this.customerNumber = customerNumber;
        this.vehicleDescription = vehicleDescription;
        this.shipTo = shipTo;
        this.po = po;
        this.counterPersonNumber = counterPersonNumber;
        this.freightTotal = freightTotal;
        this.lines = lines;
    }

    public void setSequenceName(String sequenceName){
        this.sequenceName = sequenceName;
    }

    public void setCreationTime(long creationTime){
        this.creationTime = creationTime;
    }

    public String getSequenceName() {
        return sequenceName;
    }

    public long getCreationTime(){
        return creationTime;
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

    public List<Line> getLines() {
        return lines;
    }

}
