package com.example.DAO;

import com.example.models.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    public void createCustomer(Connection connection, Customer customer) {
        try {
            String query = "INSERT INTO Customers (CustomerID, CustomerName) VALUES (?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, customer.getCustomerID());
                statement.setString(2, customer.getCustomerName());

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateCustomer(Connection connection, Customer customer) {
        try {
            String query = "UPDATE Customers SET CustomerName = ? WHERE CustomerID = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, customer.getCustomerName());
                statement.setInt(2, customer.getCustomerID());

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCustomer(Connection connection, Customer customer) {
        try {
            String query = "DELETE FROM Customers WHERE CustomerID = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, customer.getCustomerID());

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Customer> getCustomers(Connection connection) {
        List<Customer> customers = new ArrayList<>();

        try {
            String query = "SELECT * FROM Customers";
            try (PreparedStatement statement = connection.prepareStatement(query);
                    ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    Customer customer = new Customer(
                            resultSet.getInt("CustomerID"),
                            resultSet.getString("CustomerName"));
                    customers.add(customer);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customers;
    }

    public Customer getCustomerByID(Connection connection, int customerID) {
        Customer customer = null;

        try {
            String query = "SELECT * FROM Customers WHERE CustomerID = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, customerID);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        String customerName = resultSet.getString("CustomerName");
                        customer = new Customer(customerID, customerName);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customer;
    }
}
