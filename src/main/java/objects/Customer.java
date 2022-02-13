package objects;

public class Customer {

    private final String number;
    private final String name;
    private final String address;
    private final String city;
    private final String state;
    private final String zip;
    private final String phone;
    private final boolean taxable;

    public Customer(
            String number,
            String name,
            String address,
            String city,
            String state,
            String zip,
            String phone,
            boolean taxable
    ) {
        this.number = number;
        this.name = name;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phone = phone;
        this.taxable = taxable;
    }

    public String getCityStateZip() {
        StringBuilder str = new StringBuilder();
        if (!city.equalsIgnoreCase(""))
            str.append(city);
        if (!state.equalsIgnoreCase("") || !zip.equalsIgnoreCase(""))
            str.append(",");
        if (!state.equalsIgnoreCase(""))
            str.append(" ").append(state);
        if (!zip.equalsIgnoreCase(""))
            str.append(" ").append(zip);
        return str.toString();
    }

    public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZip() {
        return zip;
    }

    public String getPhone() {
        return phone;
    }

    public boolean isTaxable() {
        return taxable;
    }

}
