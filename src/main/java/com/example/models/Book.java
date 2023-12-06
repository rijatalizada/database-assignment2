package com.example.models;

public class Book {
    private int bookID;
    private String title;
    private int authorID;
    private int stockQuantity;
    private String authorName;

    public Book() {
    }

    public Book(int bookID, String title, int authorID, int stockQuantity) {
        this.bookID = bookID;
        this.title = title;
        this.authorID = authorID;
        this.stockQuantity = stockQuantity;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAuthorID() {
        return authorID;
    }

    public void setAuthorID(int authorID) {
        this.authorID = authorID;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
}
