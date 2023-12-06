package com.example.utils;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

import com.example.DAO.BookDAO;
import com.example.models.Book;

public class BooksUtils {

    public static void createBook(Connection connection, Scanner scanner, BookDAO bookDAO) {
        System.out.print("Enter BookID: ");
        int bookID = scanner.nextInt();
        System.out.print("Enter Title: ");
        String title = scanner.next();
        System.out.print("Enter AuthorID: ");
        int authorID = scanner.nextInt();
        System.out.print("Enter StockQuantity: ");
        int stockQuantity = scanner.nextInt();

        Book book = new Book(bookID, title, authorID, stockQuantity);
        bookDAO.createBook(connection, book);
        System.out.println("Book created successfully!");
    }

    public static void readBooks(Connection connection, BookDAO bookDAO) {
        List<Book> books = bookDAO.getBooks(connection);
        System.out.println("Books:");
        for (Book book : books) {
            System.out.println("BookID: " + book.getBookID() + ", Title: " + book.getTitle() +
                    ", AuthorID: " + book.getAuthorID() + ", StockQuantity: " + book.getStockQuantity());
        }
    }

    public static void updateBook(Connection connection, Scanner scanner, BookDAO bookDAO) {
        System.out.print("Enter the BookID to update: ");
        int bookID = scanner.nextInt();

        Book existingBook = bookDAO.getBookByID(connection, bookID);
        if (existingBook == null) {
            System.out.println("Book not found with ID: " + bookID);
            return;
        }

        System.out.print("Enter new Title: ");
        String newTitle = scanner.next();
        System.out.print("Enter new AuthorID: ");
        int newAuthorID = scanner.nextInt();
        System.out.print("Enter new StockQuantity: ");
        int newStockQuantity = scanner.nextInt();

        // Create a new Book object with the updated information
        Book updatedBook = new Book(bookID, newTitle, newAuthorID, newStockQuantity);
        bookDAO.updateBook(connection, updatedBook);
        System.out.println("Book updated successfully!");
    }

    public static void deleteBook(Connection connection, Scanner scanner, BookDAO bookDAO) {
        System.out.print("Enter the BookID to delete: ");
        int bookID = scanner.nextInt();

        // Check if the book with the given ID exists
        Book existingBook = bookDAO.getBookByID(connection, bookID);
        if (existingBook == null) {
            System.out.println("Book not found with ID: " + bookID);
            return;
        }

        bookDAO.deleteBook(connection, existingBook);
        System.out.println("Book deleted successfully!");
    }

}
