package com.henau.book.servlet;

import jakarta.servlet.Servlet;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@WebServlet("/hello")
public class HelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 处理GET请求
        String username = request.getParameter("username");
        String className = request.getParameter("className");
        response.setContentType("text/html;charset=UTF-8");

        request.setAttribute("username", username);
        request.setAttribute("className", className);
        request.getRequestDispatcher("/result.jsp").forward(request, response);
        }
    }

