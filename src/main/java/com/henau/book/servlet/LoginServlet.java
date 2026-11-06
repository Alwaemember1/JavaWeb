package com.henau.book.servlet;

import com.henau.book.data.UserRepository;
import com.henau.book.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    // 打开登录页面
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/login.jsp")
                .forward(request, response);
    }

    // 处理登录提交
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username == null || username.isBlank()
                || password == null || password.isBlank()) {

            request.setAttribute("message", "用户名和密码不能为空");

            request.getRequestDispatcher("/login.jsp")
                    .forward(request, response);
            return;
        }

        username = username.trim();

        // 1. 从应用共享的用户集合中查找
        User user = UserRepository.findByUsername(
                getServletContext(),
                username
        );

        // 2. 检查用户是否存在、密码是否正确
        if (user == null || !user.getPassword().equals(password)) {
            request.setAttribute("message", "用户名或密码不正确");

            request.getRequestDispatcher("/login.jsp")
                    .forward(request, response);
            return;
        }

        // 3. 验证通过，将用户对象保存到当前访问者的 Session
        HttpSession session = request.getSession();
        session.setAttribute("loginUser", user);

        // 4. 让浏览器重新访问图书列表
        response.sendRedirect(request.getContextPath() + "/book");
    }
}