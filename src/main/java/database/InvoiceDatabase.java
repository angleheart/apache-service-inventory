package database;

import objects.Invoice;
import objects.Line;
import objects.Sequence;

import java.sql.*;
import java.util.Calendar;
import java.util.List;

public record InvoiceDatabase(Connection conn) {

    private void insertLinesNoCommit(List<Line> lines, int invoiceNumber) throws SQLException {
        PreparedStatement prep = conn.prepareStatement(
                "INSERT INTO InvoiceLines(IndexKey, InvoiceNumber, Quantity, LineCode, PartNumber," +
                        " Description, ListPrice, Price, TaxCode) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);"
        );

        int indexKey = 0;

        for (Line line : lines) {
            prep.setInt(1, indexKey);
            prep.setInt(2, indexKey);


            indexKey++;
        }
    }


    public Invoice releaseSequence(Sequence sequence, int releaseCode) throws SQLException {
        try {
            PreparedStatement prep = Statements.getInvoiceInsert(conn);
            prep.setString(1, sequence.getCustomerNumber());
            prep.setInt(2, sequence.getCounterPersonNumber());
            prep.setString(3, sequence.getPo());
            prep.setString(4, sequence.getVehicleDescription());
            prep.setString(5, sequence.getShipTo());
            prep.setTimestamp(6, new Timestamp(Calendar.getInstance().getTime().getTime()));
            prep.setInt(7, releaseCode);
            if (releaseCode == 31) prep.setDouble(8, sequence.getGrandTotal());
            else prep.setDouble(8, 0);
            prep.setDouble(9, sequence.getFreightTotal());
            prep.setDouble(10, sequence.getTaxRate());
            prep.setInt(11, 0);

            prep.executeUpdate();

            ResultSet resultSet = prep.getGeneratedKeys();
            while(resultSet.next())
                System.out.println(resultSet.getInt(0));

        } catch (SQLException sql) {
            sql.printStackTrace();
            conn.rollback();
        }
        return null;
    }



}
