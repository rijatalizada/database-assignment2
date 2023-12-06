package com.example.utils;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import com.example.DAO.BookDAO;
import com.example.DAO.OrderDAO;
import com.example.models.Book;
import com.example.models.Order;
import com.example.models.OrderDetail;

public class OrdersUtils {
    public static void createOrder(Connection connection, Scanner scanner, OrderDAO orderDAO, BookDAO bookDAO) {
        System.out.print("Enter OrderDetailID: ");
        int orderDetailID = scanner.nextInt();
        System.out.print("Enter OrderID: ");
        int orderID = scanner.nextInt();
        System.out.print("Enter CustomerID: ");
        int customerID = scanner.nextInt();

        LocalDateTime currentDateTime = LocalDateTime.now();
        String orderDate = currentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        List<Book> books = bookDAO.getBooks(connection);
        System.out.println("Available Books:");
        for (Book book : books) {
            System.out.println("BookID: " + book.getBookID() + ", Title: " + book.getTitle() +
                    ", StockQuantity: " + book.getStockQuantity());
        }

        System.out.print("Enter BookID to add to the order: ");
        int bookID = scanner.nextInt();
        System.out.print("Enter Quantity: ");
        int quantity = scanner.nextInt();

        Order order = new Order(orderID, customerID, orderDate);
        OrderDetail orderDetail = new OrderDetail(orderDetailID, orderID, bookID, quantity);

        orderDAO.createOrder(connection, order, orderDetail);
        System.out.println("Order created successfully!");
    }

    public static void readOrders(Connection connection, OrderDAO orderDAO) {
        List<Order> orders = orderDAO.getOrders(connection);
        System.out.println("Orders:");
        for (Order order : orders) {
            System.out.println("OrderID: " + order.getOrderID() + ", CustomerID: " + order.getCustomerID() +
                    ", OrderDate: " + order.getOrderDate());
        }
    }

    public static void updateOrder(Connection connection, Scanner scanner, OrderDAO orderDAO, BookDAO bookDAO) {
        System.out.print("Enter the OrderID to update: ");
        int orderID = scanner.nextInt();

        Order existingOrder = orderDAO.getOrderByID(connection, orderID);
        if (existingOrder == null) {
            System.out.println("Order not found with ID: " + orderID);
            return;
        }

        System.out.print("Enter new OrderDate (YYYY-MM-DD): ");
        String newOrderDate = scanner.next();

        existingOrder.setOrderDate(newOrderDate);
        orderDAO.updateOrder(connection, existingOrder);
        System.out.println("Order updated successfully!");
    }

    public static void deleteOrder(Connection connection, Scanner scanner, OrderDAO orderDAO) {
        System.out.print("Enter the OrderID to delete: ");
        int orderID = scanner.nextInt();

        Order existingOrder = orderDAO.getOrderByID(connection, orderID);
        if (existingOrder == null) {
            System.out.println("Order not found with ID: " + orderID);
            return;
        }

        orderDAO.deleteOrder(connection, existingOrder);
        System.out.println("Order deleted successfully!");
    }

}