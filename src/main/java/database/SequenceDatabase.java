package database;

import objects.Sequence;
import objects.Line;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public record SequenceDatabase(Connection conn) {

    public List<Sequence> getAll() throws SQLException {
        List<Sequence> sequences = new ArrayList<>();
        ResultSet resultSet = conn.createStatement().executeQuery(
                "SELECT SaveName FROM Sequences;"
        );
        while(resultSet.next())
            sequences.add(get(resultSet.getString("SaveName")));
        return sequences;
    }

    public Sequence get(String sequenceName) throws SQLException {

        PreparedStatement prep = conn.prepareStatement(
                "SELECT * FROM Sequences WHERE SaveName = ? ORDER BY CreationTime DESC;"
        );

        PreparedStatement prepLines = conn.prepareStatement(
                "SELECT * FROM SequenceLines WHERE SaveName = ? ORDER BY IndexKey;"
        );

        prep.setString(1, sequenceName);
        prepLines.setString(1, sequenceName);

        ResultSet resultSet = prep.executeQuery();
        ResultSet lineResultSet = prepLines.executeQuery();

        List<Line> lines = new ArrayList<>();

        while(lineResultSet.next())
            lines.add(new Line(
                    lineResultSet.getInt("IndexKey"),
                    lineResultSet.getInt("Quantity"),
                    lineResultSet.getString("LineCode"),
                    lineResultSet.getString("PartNumber"),
                    lineResultSet.getString("Description"),
                    lineResultSet.getDouble("ListPrice"),
                    lineResultSet.getDouble("UnitPrice"),
                    lineResultSet.getString("TaxCode")
            ));

        resultSet.next();
        Sequence sequence = new Sequence(
                resultSet.getString("CustomerNumber"),
                resultSet.getString("VehicleDescription"),
                resultSet.getString("ShipTo"),
                resultSet.getString("PurchaseOrder"),
                resultSet.getInt("CounterPersonNumber"),
                resultSet.getDouble("FreightTotal"),
                lines
        );
        sequence.setCreationTime(resultSet.getTimestamp("CreationTime").getTime());
        sequence.setSequenceName(resultSet.getString("SaveName"));
        return sequence;
    }

    public void hold(Sequence sequence) throws SQLException {
        try{
            killWithoutCommit(sequence.getSequenceName());

            List<PreparedStatement> prepStatements = new ArrayList<>();

            PreparedStatement prepMain = conn.prepareStatement(
                    "INSERT INTO Sequences(SaveName, CreationTime, CustomerNumber, CounterPersonNumber, PurchaseOrder, " +
                            "VehicleDescription, ShipTo, FreightTotal) VALUES (?, ?, ?, ?, ?, ?, ?, ?);"
            );
            prepMain.setString(1, sequence.getSequenceName());
            prepMain.setTimestamp(2, new Timestamp(Calendar.getInstance().getTime().getTime()));
            prepMain.setString(3, sequence.getCustomerNumber());
            prepMain.setInt(4, sequence.getCounterPersonNumber());
            prepMain.setString(5, sequence.getPo());
            prepMain.setString(6, sequence.getVehicleDescription());
            prepMain.setString(7, sequence.getShipTo());
            prepMain.setDouble(8, sequence.getFreightTotal());
            prepStatements.add(prepMain);

            for(Line line : sequence.getLines()){
                PreparedStatement prep = conn.prepareStatement(
                        "INSERT INTO SequenceLines(IndexKey, SaveName, Quantity, LineCode, PartNumber, " +
                                "Description, ListPrice, UnitPrice, TaxCode) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?);"
                );
                prep.setInt(1, line.getIndexKey());
                prep.setString(2, sequence.getSequenceName());
                prep.setInt(3, line.getQty());
                prep.setString(4, line.getMfg());
                prep.setString(5, line.getPartNumber());
                prep.setString(6, line.getDescription());
                prep.setDouble(7, line.getListPrice());
                prep.setDouble(8, line.getUnitPrice());
                prep.setString(9, line.getTx());
                prepStatements.add(prep);
            }

            for(PreparedStatement prep : prepStatements)
                prep.execute();
            conn.commit();

        }catch(SQLException sql){
            try{
                sql.printStackTrace();
                conn.rollback();
                System.out.println("Rollback success");
            } catch (SQLException sqlException) {
                System.out.println("Rollback failed");
                throw new SQLException();
            }
        }
    }

    private void killWithoutCommit(String sequenceName) throws SQLException {
        PreparedStatement prepLineDelete = conn.prepareStatement(
                "DELETE FROM SequenceLines WHERE SaveName = ?;"
        );
        PreparedStatement prepDelete = conn.prepareStatement(
                "DELETE FROM Sequences WHERE SaveName = ?;"
        );
        prepLineDelete.setString(1, sequenceName);
        prepDelete.setString(1, sequenceName);

        prepLineDelete.execute();
        prepDelete.execute();
    }

    public void kill(String sequenceName) throws SQLException {
        try {
            killWithoutCommit(sequenceName);
            conn.commit();
        } catch (SQLException sql) {
            try{
                sql.printStackTrace();
                conn.rollback();
                System.out.println("Rollback success");
            } catch (SQLException sql2) {
                System.out.println("Rollback failed");
                throw new SQLException();
            }
        }
    }
}
