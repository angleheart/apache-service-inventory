package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {

    private final int serverPort;

    private final String accountingServerIP;
    private final String accountingServerPort;
    private final String vehicleServerIP;
    private final String vehicleServerPort;
    private final String inventoryServerIP;
    private final String inventoryServerPort;
    private final String customerServerIP;
    private final String customerServerPort;

    private final String sequenceServerIP;
    private final String sequenceServerPort;

    private static Config instance;

    public static Config getInstance() {
        return instance;
    }

    public static Config getInstance(String path) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream(path));
        instance = new Config(
                Integer.parseInt(properties.getProperty("serverPort")),
                properties.getProperty("accountingServerIP"),
                properties.getProperty("accountingServerPort"),
                properties.getProperty("vehicleServerIP"),
                properties.getProperty("vehicleServerPort"),
                properties.getProperty("inventoryServerIP"),
                properties.getProperty("inventoryServerPort"),
                properties.getProperty("customerServerIP"),
                properties.getProperty("customerServerPort"),
                properties.getProperty("sequenceServerIP"),
                properties.getProperty("sequenceServerPort")
        );
        return instance;
    }

    public Config(
            int serverPort,
            String accountingServerIP,
            String accountingServerPort,
            String vehicleServerIP,
            String vehicleServerPort,
            String inventoryServerIP,
            String inventoryServerPort,
            String customerServerIP,
            String customerServerPort,
            String sequenceServerIP,
            String sequenceServerPort) {
        this.serverPort = serverPort;
        this.accountingServerIP = accountingServerIP;
        this.accountingServerPort = accountingServerPort;
        this.vehicleServerIP = vehicleServerIP;
        this.vehicleServerPort = vehicleServerPort;
        this.inventoryServerIP = inventoryServerIP;
        this.inventoryServerPort = inventoryServerPort;
        this.customerServerIP = customerServerIP;
        this.customerServerPort = customerServerPort;
        this.sequenceServerIP = sequenceServerIP;
        this.sequenceServerPort = sequenceServerPort;
    }

    public int getServerPort() {
        return serverPort;
    }

    public String getAccountingServerIP() {
        return accountingServerIP;
    }

    public String getServerAddress(Service service) {
        String prefix = "http://";
        return switch (service) {
            case ACCOUNTING -> prefix + accountingServerIP + ":" + accountingServerPort;
            case VEHICLE -> prefix + vehicleServerIP + ":" + vehicleServerPort;
            case INVENTORY -> prefix + inventoryServerIP + ":" + inventoryServerPort;
            case CUSTOMER -> prefix + customerServerIP + ":" + customerServerPort;
            case SEQUENCE -> prefix + sequenceServerIP + ":" + sequenceServerPort;
        };
    }


}
