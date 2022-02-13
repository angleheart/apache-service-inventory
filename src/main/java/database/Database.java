package database;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class Database implements AutoCloseable {

    private static DataSource dataSource;
    private final Connection conn;

    public static void setDataSource(DataSource newDataSource) throws SQLException {
        dataSource = newDataSource;
        dataSource.getConnection().close();
    }

    public Database() throws SQLException {
        this.conn = dataSource.getConnection();
    }

    public Connection getConnection(){
        return this.conn;
    }

    @Override
    public void close() throws SQLException {
        conn.close();
    }

}
