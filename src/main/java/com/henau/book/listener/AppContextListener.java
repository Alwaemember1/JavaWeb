package com.henau.book.listener;

import com.henau.book.model.Favorite;
import com.henau.book.model.User;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.util.ArrayList;
import java.util.HashMap;
import com.henau.book.data.AvatarRepository;

@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        // 应用启动时初始化一次，供注册和后续登录共同使用。
        event.getServletContext().setAttribute("users", new ArrayList<User>());
        event.getServletContext().setAttribute("favorites", new ArrayList<Favorite>());
        event.getServletContext().setAttribute("avatars", new HashMap<String, AvatarRepository.Avatar>());
    }
}
