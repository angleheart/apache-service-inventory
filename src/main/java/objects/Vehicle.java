package objects;

public class Vehicle {

    private final String year;
    private final String make;
    private final String model;
    private final String engine;

    public Vehicle(String year, String make, String model, String engine) {
        this.year = year;
        this.make = make;
        this.model = model;
        this.engine = engine;
    }

    public String getYear() {
        return year;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getEngine() {
        return engine;
    }

}
