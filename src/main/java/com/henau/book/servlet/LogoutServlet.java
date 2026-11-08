package com.henau.book.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws IOException {
        // 1. 获取 Session 对象
        HttpSession session =request.getSession(false);
        if (session != null) {
            // 2. 销毁 Session
            session.invalidate();
        }
        // 3. 重定向到登录页
        response.sendRedirect(request.getContextPath() + "/login");

    }
}
