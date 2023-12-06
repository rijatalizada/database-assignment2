package com.example.utils;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

import com.example.DAO.CustomerDAO;
import com.example.models.Customer;

public class CustomersUtils {
    public static void createCustomer(Connection connection, Scanner scanner, CustomerDAO customerDAO) {
        System.out.print("Enter CustomerID: ");
        int customerID = scanner.nextInt();
        System.out.print("Enter CustomerName: ");
        String customerName = scanner.next();

        Customer customer = new Customer(customerID, customerName);
        customerDAO.createCustomer(connection, customer);
        System.out.println("Customer created successfully!");
    }

    public static void readCustomers(Connection connection, CustomerDAO customerDAO) {
        List<Customer> customers = customerDAO.getCustomers(connection);
        System.out.println("Customers:");
        for (Customer customer : customers) {
            System.out.println(
                    "CustomerID: " + customer.getCustomerID() + ", CustomerName: " + customer.getCustomerName());
        }
    }

    public static void updateCustomer(Connection connection, Scanner scanner, CustomerDAO customerDAO) {
        System.out.print("Enter the CustomerID to update: ");
        int customerID = scanner.nextInt();

        Customer existingCustomer = customerDAO.getCustomerByID(connection, customerID);
        if (existingCustomer == null) {
            System.out.println("Customer not found with ID: " + customerID);
            return;
        }

        System.out.print("Enter new CustomerName: ");
        String newCustomerName = scanner.next();

        Customer updatedCustomer = new Customer(customerID, newCustomerName);
        customerDAO.updateCustomer(connection, updatedCustomer);
        System.out.println("Customer updated successfully!");
    }

    public static void deleteCustomer(Connection connection, Scanner scanner, CustomerDAO customerDAO) {
        System.out.print("Enter the CustomerID to delete: ");
        int customerID = scanner.nextInt();

        Customer existingCustomer = customerDAO.getCustomerByID(connection, customerID);
        if (existingCustomer == null) {
            System.out.println("Customer not found with ID: " + customerID);
            return;
        }

        customerDAO.deleteCustomer(connection, existingCustomer);
        System.out.println("Customer deleted successfully!");
    }
}
