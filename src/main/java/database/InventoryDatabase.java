package database;

import objects.Invoice;
import objects.InvoiceLine;
import objects.Part;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public record InventoryDatabase(Connection conn) {

    public List<Part> getParts(String mfg, String partNumber) throws SQLException {
        partNumber = partNumber.replaceAll("/", "");
        partNumber = partNumber.replaceAll("-", "");
        partNumber = partNumber.replaceAll(" ", "");

        List<Part> toReturn = new ArrayList<>();
        PreparedStatement prepStatement;
        ResultSet resultSet;

        if (mfg.equals("*")) {
            prepStatement = conn.prepareStatement(
                    "SELECT * FROM Parts WHERE " +
                            "REPLACE(REPLACE(PartNumber, '-', ''), '/', '') LIKE ?;"
            );
            prepStatement.setString(1, partNumber);
        } else {
            prepStatement = conn.prepareStatement(
                    "SELECT * FROM Parts WHERE LineCode = ? AND " +
                            "REPLACE(REPLACE(PartNumber, '-', ''), '/', '') LIKE ?;"
            );
            prepStatement.setString(1, mfg);
            prepStatement.setString(2, partNumber);
        }
        resultSet = prepStatement.executeQuery();

        while (resultSet.next())
            toReturn.add(
                    new Part(
                            resultSet.getString("LineCode"),
                            resultSet.getString("PartNumber"),
                            resultSet.getString("Description"),
                            resultSet.getDouble("Cost"),
                            resultSet.getInt("StockQuantity"),
                            resultSet.getInt("AvailableQuantity")
                    )
            );
        return toReturn;
    }

    public void updateForInvoice(Invoice invoice) throws SQLException {
        updateForInvoice(invoice, false);
    }

    public void updateForInvoice(Invoice invoice, boolean rollback) throws SQLException {

        PreparedStatement prepStatement = conn.prepareStatement(
                "UPDATE Parts " +
                        "SET AvailableQuantity = AvailableQuantity - ? " +
                        "WHERE LineCode = ? AND PartNumber = ?;"

        );

        for (InvoiceLine line : invoice.getInvoiceLines()) {
            int quantity = line.getQty();
            if (rollback)
                quantity *= -1;
            prepStatement.setInt(1, quantity);
            prepStatement.setString(2, line.getMfg());
            prepStatement.setString(3, line.getPartNumber());
            prepStatement.execute();
        }
    }

    public Part updatePart(Part part) throws SQLException {
        PreparedStatement prepStatement = conn.prepareStatement(
                "UPDATE Parts SET AvailableQuantity = ?, StockQuantity = ?, " +
                        "Cost = ?, Description = ? " +
                        " WHERE LineCode = ? AND PartNumber = ?;"
        );
        prepStatement.setInt(1, part.getAvailableQuantity());
        prepStatement.setInt(2, part.getStockQuantity());
        prepStatement.setDouble(3, part.getCost());
        prepStatement.setString(4, part.getDescription());
        prepStatement.setString(5, part.getLineCode());
        prepStatement.setString(6, part.getPartNumber());
        prepStatement.executeUpdate();
        return getParts(part.getLineCode(), part.getPartNumber()).get(0);
    }

}
