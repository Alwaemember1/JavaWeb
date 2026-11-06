package com.henau.book.model;

public class User {

    private final String username;
    private final String passwordHash;
    private final String nickname;

    public User(String username,
                String passwordHash,
                String nickname) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.nickname = nickname;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getNickname() {
        return nickname;
    }
}