package com.example.utils;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

import com.example.DAO.AuthorDAO;
import com.example.models.Author;

public class AuthorsUtils {
    public static void createAuthor(Connection connection, Scanner scanner, AuthorDAO authorDAO) {
        System.out.print("Enter AuthorID: ");
        int authorID = scanner.nextInt();
        System.out.print("Enter AuthorName: ");
        String authorName = scanner.next();

        Author author = new Author(authorID, authorName);
        authorDAO.createAuthor(connection, author);
        System.out.println("Author created successfully!");
    }

    public static void readAuthors(Connection connection, AuthorDAO authorDAO) {
        List<Author> authors = authorDAO.getAuthors(connection);
        System.out.println("Authors:");
        for (Author author : authors) {
            System.out.println("AuthorID: " + author.getAuthorID() + ", AuthorName: " + author.getAuthorName());
        }
    }

    public static void updateAuthor(Connection connection, Scanner scanner, AuthorDAO authorDAO) {
        System.out.print("Enter the AuthorID to update: ");
        int authorID = scanner.nextInt();

        Author existingAuthor = authorDAO.getAuthorByID(connection, authorID);
        if (existingAuthor == null) {
            System.out.println("Author not found with ID: " + authorID);
            return;
        }

        System.out.print("Enter new AuthorName: ");
        String newAuthorName = scanner.next();

        Author updatedAuthor = new Author(authorID, newAuthorName);
        authorDAO.updateAuthor(connection, updatedAuthor);
        System.out.println("Author updated successfully!");
    }

    public static void deleteAuthor(Connection connection, Scanner scanner, AuthorDAO authorDAO) {
        System.out.print("Enter the AuthorID to delete: ");
        int authorID = scanner.nextInt();

        Author existingAuthor = authorDAO.getAuthorByID(connection, authorID);
        if (existingAuthor == null) {
            System.out.println("Author not found with ID: " + authorID);
            return;
        }

        authorDAO.deleteAuthor(connection, existingAuthor);
        System.out.println("Author deleted successfully!");
    }

}
