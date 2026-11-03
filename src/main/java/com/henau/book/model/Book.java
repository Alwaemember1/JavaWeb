package com.henau.book.model;

public class Book {

    private int id;
    private String title;
    private String author;
    private String description;
    private boolean available;

    public Book(int id, String title, String author,
                String description, boolean available) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.description = description;
        this.available = available;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public boolean isAvailable() {
        return available;
    }
}