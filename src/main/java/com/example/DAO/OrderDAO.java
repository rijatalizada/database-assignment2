package com.example.DAO;

import com.example.models.Order;
import com.example.models.OrderDetail;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    public void createOrder(Connection connection, Order order, OrderDetail orderDetail) {
        try {
            connection.setAutoCommit(false);

            String orderQuery = "INSERT INTO Orders (OrderID, CustomerID, OrderDate) VALUES (?, ?, ?)";
            try (PreparedStatement orderStatement = connection.prepareStatement(orderQuery,
                    Statement.RETURN_GENERATED_KEYS)) {
                orderStatement.setInt(1, order.getOrderID());
                orderStatement.setInt(2, order.getCustomerID());
                orderStatement.setString(3, order.getOrderDate());

                orderStatement.executeUpdate();

                ResultSet generatedKeys = orderStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    order.setOrderID(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Failed to retrieve generated OrderID.");
                }
            }

            String orderDetailQuery = "INSERT INTO OrderDetails (OrderID, BookID, Quantity) VALUES (?, ?, ?)";
            try (PreparedStatement orderDetailStatement = connection.prepareStatement(orderDetailQuery)) {
                orderDetailStatement.setInt(1, order.getOrderID());
                orderDetailStatement.setInt(2, orderDetail.getBookID());
                orderDetailStatement.setInt(3, orderDetail.getQuantity());

                orderDetailStatement.executeUpdate();
            }

            String updateStockQuery = "UPDATE Books SET StockQuantity = StockQuantity - ? WHERE BookID = ?";
            try (PreparedStatement updateStockStatement = connection.prepareStatement(updateStockQuery)) {
                updateStockStatement.setInt(1, orderDetail.getQuantity());
                updateStockStatement.setInt(2, orderDetail.getBookID());

                updateStockStatement.executeUpdate();
            }

            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            try {
                connection.rollback();
                connection.setAutoCommit(true);
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }

            e.printStackTrace();
        }
    }

    public void updateOrder(Connection connection, Order order) {
        try {

            connection.setAutoCommit(false);

            String orderUpdateQuery = "UPDATE Orders SET CustomerID = ?, OrderDate = ? WHERE OrderID = ?";
            try (PreparedStatement orderUpdateStatement = connection.prepareStatement(orderUpdateQuery)) {
                orderUpdateStatement.setInt(1, order.getCustomerID());
                orderUpdateStatement.setString(2, order.getOrderDate());
                orderUpdateStatement.setInt(3, order.getOrderID());

                orderUpdateStatement.executeUpdate();
            }

            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            try {

                connection.rollback();
                connection.setAutoCommit(true);
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }

            e.printStackTrace();
        }
    }

    public void deleteOrder(Connection connection, Order order) {
        try {
            // Start a transaction to ensure atomicity
            connection.setAutoCommit(false);

            // Step 1: Retrieve the book associated with the order
            String bookQuery = "SELECT BookID, Quantity FROM OrderDetails WHERE OrderID = ?";
            try (PreparedStatement bookStatement = connection.prepareStatement(bookQuery)) {
                bookStatement.setInt(1, order.getOrderID());

                try (ResultSet resultSet = bookStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int bookID = resultSet.getInt("BookID");
                        int quantity = resultSet.getInt("Quantity");

                        // Step 2: Delete the order
                        String orderDeleteQuery = "DELETE FROM Orders WHERE OrderID = ?";
                        try (PreparedStatement orderDeleteStatement = connection.prepareStatement(orderDeleteQuery)) {
                            orderDeleteStatement.setInt(1, order.getOrderID());

                            orderDeleteStatement.executeUpdate();
                        }

                        // Step 3: Delete the associated book from OrderDetails
                        String orderDetailDeleteQuery = "DELETE FROM OrderDetails WHERE OrderID = ?";
                        try (PreparedStatement orderDetailDeleteStatement = connection
                                .prepareStatement(orderDetailDeleteQuery)) {
                            orderDetailDeleteStatement.setInt(1, order.getOrderID());

                            orderDetailDeleteStatement.executeUpdate();
                        }

                        // Step 4: Update StockQuantity for the associated book
                        String updateStockQuery = "UPDATE Books SET StockQuantity = StockQuantity + ? WHERE BookID = ?";
                        try (PreparedStatement updateStockStatement = connection.prepareStatement(updateStockQuery)) {
                            updateStockStatement.setInt(1, quantity);
                            updateStockStatement.setInt(2, bookID);

                            updateStockStatement.executeUpdate();
                        }
                    }
                }
            }

            // Commit the transaction
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            try {
                // Rollback the transaction in case of an exception
                connection.rollback();
                connection.setAutoCommit(true);
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }

            e.printStackTrace(); // Handle the exception according to your application's error-handling strategy
        }
    }

    public List<Order> getOrders(Connection connection) {
        List<Order> orders = new ArrayList<>();

        try {
            String query = "SELECT * FROM Orders";
            try (PreparedStatement statement = connection.prepareStatement(query);
                    ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    Order order = new Order(
                            resultSet.getInt("OrderID"),
                            resultSet.getInt("CustomerID"),
                            resultSet.getString("OrderDate")

                    );
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    public Order getOrderByID(Connection connection, int orderID) {
        Order order = null;

        try {
            String query = "SELECT * FROM Orders WHERE OrderID = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, orderID);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        int customerID = resultSet.getInt("CustomerID");
                        String orderDate = resultSet.getString("OrderDate");

                        order = new Order(orderID, customerID, orderDate);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return order;
    }
}
