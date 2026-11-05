package com.henau.book.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/register.jsp").forward(request, response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if(username == null || username.isEmpty() ){
            request.setAttribute("message", "用户名不能为空");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }
        if(password == null || password.isEmpty() ){
            request.setAttribute("message", "密码不能为空");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }
        request.setAttribute("message", "注册成功");
        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }
}
