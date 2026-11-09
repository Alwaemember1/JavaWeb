package com.henau.book.model;

public class Favorite {
    private String username;
    private int bookId;

    public Favorite(String username, int bookId) {
        this.username = username;
        this.bookId = bookId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
}
