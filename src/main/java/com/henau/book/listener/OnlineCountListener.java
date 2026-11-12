package com.henau.book.listener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

/**
 * 统计当前 Web 应用中尚未失效的 HttpSession 数量。
 * 注意：这是在线会话数，不是独立用户数，也不是当前正在浏览页面的人数。
 */
@WebListener
public class OnlineCountListener implements ServletContextListener, HttpSessionListener {

    private static final String COUNT_KEY = "onlineCount";

    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext context = event.getServletContext();
        synchronized (context) {
            context.setAttribute(COUNT_KEY, 0);
        }
    }

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        ServletContext context = event.getSession().getServletContext();
        synchronized (context) {
            Integer count = (Integer) context.getAttribute(COUNT_KEY);
            context.setAttribute(COUNT_KEY, (count == null ? 0 : count) + 1);
        }
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        ServletContext context = event.getSession().getServletContext();
        synchronized (context) {
            Integer count = (Integer) context.getAttribute(COUNT_KEY);
            int current = count == null ? 0 : count;
            context.setAttribute(COUNT_KEY, Math.max(0, current - 1));
        }
    }
}
