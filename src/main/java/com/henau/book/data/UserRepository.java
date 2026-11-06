package com.henau.book.data;

import com.henau.book.model.User;
import jakarta.servlet.ServletContext;

import java.util.List;

public class UserRepository {

    @SuppressWarnings("unchecked")
    private static List<User> getUsers(ServletContext context) {
        // 集合由 AppContextListener 在应用启动时创建。
        return (List<User>) context.getAttribute("users");
    }

    public static User findByUsername(ServletContext context, String username) {
        // 共享集合的读取和保存使用同一把锁，避免多个请求互相干扰。
        synchronized (context) {
            for (User user : getUsers(context)) {
                if (user.getUsername().equals(username)) {
                    return user;
                }
            }
            return null;
        }
    }

    public static boolean save(ServletContext context, User newUser) {
        // 检查重名和加入集合必须一起执行。
        synchronized (context) {
            List<User> users = getUsers(context);
            for (User user : users) {
                if (user.getUsername().equals(newUser.getUsername())) {
                    return false;
                }
            }
            users.add(newUser);
            return true;
        }
    }
}
