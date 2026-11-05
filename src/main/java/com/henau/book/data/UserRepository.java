package com.henau.book.data;

import com.henau.book.model.User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserRepository {

    private static final Map<String, User> USERS =
            new ConcurrentHashMap<>();

    // 根据用户名查找用户，查不到返回 null
    public static User findByUsername(String username) {
        return USERS.get(username);
    }

    // 保存用户：成功返回 true，用户名已存在返回 false
    public static boolean save(User user) {
        User existingUser = USERS.putIfAbsent(
                user.getUsername(),
                user
        );

        return existingUser == null;
    }
}