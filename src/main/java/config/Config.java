package config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {

    private final int serverPort;
    private final String databaseUrl;
    private final String databaseName;
    private final String databaseUsername;
    private final String databasePassword;

    private final String customerServiceIP;
    private final String customerServicePort;

    private Config(
            int serverPort,
            String databaseUrl,
            String databaseName,
            String databaseUsername,
            String databasePassword,
            String customerServiceIP,
            String customerServicePort) {
        this.serverPort = serverPort;
        this.databaseUrl = databaseUrl;
        this.databaseName = databaseName;
        this.databaseUsername = databaseUsername;
        this.databasePassword = databasePassword;
        this.customerServiceIP = customerServiceIP;
        this.customerServicePort = customerServicePort;
    }

    private static Config instance;

    public static Config getInstance() {
        return instance;
    }

    public static Config getInstance(String path) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream(path));
        instance = new Config(
                Integer.parseInt(properties.getProperty("serverPort")),
                properties.getProperty("databaseUrl"),
                properties.getProperty("databaseName"),
                properties.getProperty("databaseUsername"),
                properties.getProperty("databasePassword"),
                properties.getProperty("customerServiceIP"),
                properties.getProperty("customerServicePort")
        );
        return instance;
    }

    public DataSource getDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(databaseUrl + "/" + databaseName);
        config.setUsername(databaseUsername);
        config.setPassword(databasePassword);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        return new HikariDataSource(config);
    }

    public int getServerPort() {
        return serverPort;
    }

    public String getCustomerServiceIP() {
        return customerServiceIP;
    }

    public String getCustomerServicePort() {
        return customerServicePort;
    }

}
