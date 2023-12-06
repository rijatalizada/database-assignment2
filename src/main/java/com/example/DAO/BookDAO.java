package com.example.DAO;

import com.example.models.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    public void createBook(Connection connection, Book book) {
        try {
            String query = "INSERT INTO Books (BookID, Title, AuthorID, StockQuantity) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, book.getBookID());
                statement.setString(2, book.getTitle());
                statement.setInt(3, book.getAuthorID());
                statement.setInt(4, book.getStockQuantity());

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateBook(Connection connection, Book book) {
        try {
            String query = "UPDATE Books SET Title = ?, AuthorID = ?, StockQuantity = ? WHERE BookID = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, book.getTitle());
                statement.setInt(2, book.getAuthorID());
                statement.setInt(3, book.getStockQuantity());
                statement.setInt(4, book.getBookID());

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteBook(Connection connection, Book book) {
        try {
            String query = "DELETE FROM Books WHERE BookID = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, book.getBookID());

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Book> getBooks(Connection connection) {
        List<Book> books = new ArrayList<>();

        try {
            String query = "SELECT b.*, a.AuthorName FROM Books b JOIN Authors a ON b.AuthorID = a.AuthorID";
            try (PreparedStatement statement = connection.prepareStatement(query);
                    ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    Book book = new Book(
                            resultSet.getInt("BookID"),
                            resultSet.getString("Title"),
                            resultSet.getInt("AuthorID"),
                            resultSet.getInt("StockQuantity"));

                    book.setAuthorName(resultSet.getString("AuthorName"));

                    books.add(book);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

    public Book getBookByID(Connection connection, int bookID) {
        Book book = null;

        try {
            String query = "SELECT * FROM Books WHERE BookID = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, bookID);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        String title = resultSet.getString("Title");
                        int authorID = resultSet.getInt("AuthorID");
                        int stockQuantity = resultSet.getInt("StockQuantity");

                        book = new Book(bookID, title, authorID, stockQuantity);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return book;
    }
}
