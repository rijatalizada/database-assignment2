package com.example.DAO;

import com.example.models.Author;
import com.example.models.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorDAO {
    public List<Author> getAuthors(Connection connection) {
        List<Author> authors = new ArrayList<>();

        try {
            String query = "SELECT * FROM Authors";
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(query)) {
                    while (resultSet.next()) {
                        int authorID = resultSet.getInt("AuthorID");
                        String authorName = resultSet.getString("AuthorName");

                        Author author = new Author(authorID, authorName);
                        authors.add(author);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return authors;
    }

    public Author getAuthorByID(Connection connection, int authorID) {
        Author author = null;

        try {
            String query = "SELECT * FROM Authors WHERE AuthorID = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, authorID);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        String authorName = resultSet.getString("AuthorName");
                        author = new Author(authorID, authorName);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return author;
    }

    public List<Book> getBooksByAuthor(Connection connection, Author author) {
        List<Book> books = new ArrayList<>();

        try {
            // Use PreparedStatement to avoid SQL injection
            String query = "SELECT * FROM Books WHERE AuthorID = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, author.getAuthorID());

                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        Book book = new Book(
                                resultSet.getInt("BookID"),
                                resultSet.getString("Title"),
                                resultSet.getInt("AuthorID"),
                                resultSet.getInt("StockQuantity"));
                        books.add(book);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

    public void createAuthor(Connection connection, Author author) {
        try {
            String query = "INSERT INTO Authors (AuthorID, AuthorName) VALUES (?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, author.getAuthorID());
                statement.setString(2, author.getAuthorName());

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateAuthor(Connection connection, Author author) {
        try {
            String query = "UPDATE Authors SET AuthorName = ? WHERE AuthorID = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, author.getAuthorName());
                statement.setInt(2, author.getAuthorID());

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAuthor(Connection connection, Author author) {
        try {
            String query = "DELETE FROM Authors WHERE AuthorID = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, author.getAuthorID());

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayAuthorsTableMetadata(Connection connection) {
        try {
            DatabaseMetaData metaData = connection.getMetaData();

            ResultSet tableResultSet = metaData.getTables(null, null, "Authors", null);
            while (tableResultSet.next()) {
                String tableName = tableResultSet.getString("TABLE_NAME");
                System.out.println("Table Name: " + tableName);

                ResultSet columnResultSet = metaData.getColumns(null, null, tableName, null);
                System.out.println("Column Details:");
                while (columnResultSet.next()) {
                    String columnName = columnResultSet.getString("COLUMN_NAME");
                    String dataType = columnResultSet.getString("TYPE_NAME");
                    int columnSize = columnResultSet.getInt("COLUMN_SIZE");
                    System.out.println(
                            "Column Name: " + columnName + ", Data Type: " + dataType + ", Size: " + columnSize);
                }

                ResultSet primaryKeyResultSet = metaData.getPrimaryKeys(null, null, tableName);
                System.out.println("Primary Key Details:");
                while (primaryKeyResultSet.next()) {
                    String primaryKeyColumnName = primaryKeyResultSet.getString("COLUMN_NAME");
                    System.out.println("Primary Key Column Name: " + primaryKeyColumnName);
                }

                ResultSet foreignKeyResultSet = metaData.getImportedKeys(null, null, tableName);
                System.out.println("Foreign Key Details:");
                while (foreignKeyResultSet.next()) {
                    String foreignKeyColumnName = foreignKeyResultSet.getString("FKCOLUMN_NAME");
                    String referencedTableName = foreignKeyResultSet.getString("PKTABLE_NAME");
                    String referencedColumnName = foreignKeyResultSet.getString("PKCOLUMN_NAME");
                    System.out.println("Foreign Key Column Name: " + foreignKeyColumnName +
                            ", Referenced Table: " + referencedTableName + ", Referenced Column: "
                            + referencedColumnName);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
