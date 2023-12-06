package com.example;

import com.example.utils.AuthorsUtils;
import com.example.utils.CustomersUtils;
import com.example.utils.BooksUtils;
import com.example.utils.OrdersUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import com.example.DAO.AuthorDAO;
import com.example.DAO.BookDAO;
import com.example.DAO.CustomerDAO;
import com.example.DAO.OrderDAO;
import com.example.database.DatabaseConnector;

public class App {

    public static void main(String[] args) {
        String jdbcUrl = "jdbc:postgresql://localhost:5432/postgres";
        String username = "postgres";
        String password = "pass123";

        DatabaseConnector databaseConnector = new DatabaseConnector(jdbcUrl, username, password);

        try {
            try (Connection connection = databaseConnector.connect()) {
                Scanner scanner = new Scanner(System.in);
                AuthorDAO authorDAO = new AuthorDAO();
                BookDAO bookDAO = new BookDAO();
                CustomerDAO customerDAO = new CustomerDAO();
                OrderDAO orderDAO = new OrderDAO();

                if (connection != null) {
                    int choice;
                    do {
                        System.out.println("1. Authors");
                        System.out.println("2. Books");
                        System.out.println("3. Customers");
                        System.out.println("4. Orders");
                        System.out.println("5. Exit");
                        System.out.print("Enter your choice: ");
                        choice = scanner.nextInt();

                        switch (choice) {
                            case 1:
                                authorDAO.displayAuthorsTableMetadata(connection);
                                authorSection(connection, scanner, authorDAO);
                                break;
                            case 2:
                                bookSection(connection, scanner, bookDAO);
                                break;
                            case 3:
                                customerSection(connection, scanner, customerDAO);
                                break;
                            case 4:
                                orderSection(connection, scanner, orderDAO, bookDAO);
                                break;
                        }
                    } while (choice != 5);
                } else {
                    System.out.println("Failed to make connection!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void authorSection(Connection connection, Scanner scanner, AuthorDAO authorDAO) {
        int choice;
        do {
            System.out.println("Authors Section:");
            System.out.println("1. Create Author");
            System.out.println("2. Read Authors");
            System.out.println("3. Update Author");
            System.out.println("4. Delete Author");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    AuthorsUtils.createAuthor(connection, scanner, authorDAO);
                    break;
                case 2:
                    AuthorsUtils.readAuthors(connection, authorDAO);
                    break;
                case 3:
                    AuthorsUtils.updateAuthor(connection, scanner, authorDAO);
                    break;
                case 4:
                    AuthorsUtils.deleteAuthor(connection, scanner, authorDAO);
                    break;
            }
        } while (choice != 5);
    }

    private static void customerSection(Connection connection, Scanner scanner, CustomerDAO customerDAO) {
        int choice;
        do {
            System.out.println("Customers Section:");
            System.out.println("1. Create Customer");
            System.out.println("2. Read Customers");
            System.out.println("3. Update Customer");
            System.out.println("4. Delete Customer");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    CustomersUtils.createCustomer(connection, scanner, customerDAO);
                    break;
                case 2:
                    CustomersUtils.readCustomers(connection, customerDAO);
                    break;
                case 3:
                    CustomersUtils.updateCustomer(connection, scanner, customerDAO);
                    break;
                case 4:
                    CustomersUtils.deleteCustomer(connection, scanner, customerDAO);
                    break;
            }
        } while (choice != 5);
    }

    private static void bookSection(Connection connection, Scanner scanner, BookDAO bookDAO) {
        int choice;
        do {
            System.out.println("Books Section:");
            System.out.println("1. Create Book");
            System.out.println("2. Read Books");
            System.out.println("3. Update Book");
            System.out.println("4. Delete Book");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    BooksUtils.createBook(connection, scanner, bookDAO);
                    break;
                case 2:
                    BooksUtils.readBooks(connection, bookDAO);
                    break;
                case 3:
                    BooksUtils.updateBook(connection, scanner, bookDAO);
                    break;
                case 4:
                    BooksUtils.deleteBook(connection, scanner, bookDAO);
                    break;
            }
        } while (choice != 5);
    }

    public static void orderSection(Connection connection, Scanner scanner, OrderDAO orderDAO, BookDAO bookDAO) {
        int choice;
        do {
            System.out.println("Orders Section:");
            System.out.println("1. Create Order");
            System.out.println("2. Read Orders");
            System.out.println("3. Update Order");
            System.out.println("4. Delete Order");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    OrdersUtils.createOrder(connection, scanner, orderDAO, bookDAO);
                    break;
                case 2:
                    OrdersUtils.readOrders(connection, orderDAO);
                    break;
                case 3:
                    OrdersUtils.updateOrder(connection, scanner, orderDAO, bookDAO);
                    break;
                case 4:
                    OrdersUtils.deleteOrder(connection, scanner, orderDAO);
                    break;
            }
        } while (choice != 5);
    }
}
