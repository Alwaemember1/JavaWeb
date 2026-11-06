package com.henau.book.model;

// 课程本机演示使用测试密码，不用于真实账号或公开服务。
public class User {

    private final String username;
    private final String password;
    private final String nickname;

    public User(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }
}
