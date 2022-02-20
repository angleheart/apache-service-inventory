package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

class Statements {

    static PreparedStatement getInvoiceInsert(Connection conn) throws SQLException {
        return conn.prepareStatement(
                "INSERT INTO Invoices(CustomerNumber, CounterPersonNumber," +
                        " PurchaseOrder, VehicleDescription, ShipTo, ReleaseTime, " +
                        "ReleaseCode, Balance, FreightTotal, TaxRate, AccountingPeriod) " +
                        "VALUES (?,?,?,?,?,?,?,?,?,?,?);", Statement.RETURN_GENERATED_KEYS
        );
    }

    static PreparedStatement getInvoiceLineInsert(Connection conn) throws SQLException {
        return conn.prepareStatement(
                "INSERT INTO InvoiceLines(IndexKey, InvoiceNumber, Quantity, LineCode, PartNumber," +
                        " Description, ListPrice, Price, TaxCode) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);"
        );
    }

}
