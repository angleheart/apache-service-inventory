package database;

import objects.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public record CustomerDatabase(Connection conn) {

    public void add(Customer customer) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(
                "INSERT INTO Customers(CustomerNumber, Name, Address, City, State, Zip, PhoneNumber) " +
                        "VALUES(?, ?, ?, ?, ?, ?, ?);"
        );
        preparedStatement.setString(1, customer.getNumber());
        preparedStatement.setString(2, customer.getName());
        preparedStatement.setString(3, customer.getAddress());
        preparedStatement.setString(4, customer.getCity());
        preparedStatement.setString(5, customer.getState());
        preparedStatement.setString(6, customer.getZip());
        preparedStatement.setString(7, customer.getPhone());

        preparedStatement.execute();
    }

    public List<Customer> query(String query) throws SQLException {
        List<Customer> customers = new ArrayList<>();
        PreparedStatement prepStatement;
        ResultSet resultSet;

        if(query.equals("*")){
            prepStatement = conn.prepareStatement(
                    "SELECT * FROM Customers;"
            );
        }else{
            prepStatement = conn.prepareStatement(
                    "SELECT * FROM Customers WHERE CustomerNumber = ?;"
            );
            prepStatement.setString(1, query);
        }
        resultSet = prepStatement.executeQuery();

        if (!resultSet.next()) {
            prepStatement = conn.prepareStatement(
                    "SELECT * FROM Customers WHERE Name LIKE ? ORDER BY Name;"
            );
            prepStatement.setString(1, query + "%");
            resultSet = prepStatement.executeQuery();

            if(!resultSet.next()){
                prepStatement = conn.prepareStatement(
                        "SELECT * FROM Customers WHERE Name LIKE ? ORDER BY Name;"
                );
                prepStatement.setString(1, "%" + query + "%");
                resultSet = prepStatement.executeQuery();
                if(!resultSet.next()){
                    return customers;
                }
            }
        }
        do{
            customers.add(
                    new Customer(
                            resultSet.getString("CustomerNumber"),
                            resultSet.getString("Name"),
                            resultSet.getString("Address"),
                            resultSet.getString("City"),
                            resultSet.getString("State"),
                            resultSet.getString("Zip"),
                            resultSet.getString("PhoneNumber"),
                            resultSet.getBoolean("Taxable")
                    )
            );
        } while (resultSet.next());
        return customers;
    }

}
