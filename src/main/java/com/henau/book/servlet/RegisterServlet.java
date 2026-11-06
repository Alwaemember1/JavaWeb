package com.henau.book.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.henau.book.model.User;
import com.henau.book.data.UserRepository;
import com.henau.book.util.PasswordUtil;
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
        String nickname = request.getParameter("nickname");
        if(username == null || username.isBlank() ){
            request.setAttribute("message", "用户名不能为空");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }
        if(password == null || password.isBlank() ){
            request.setAttribute("message", "密码不能为空");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }
        if(nickname == null || nickname.isBlank() ){
            request.setAttribute("message", "昵称不能为空");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }
        // 1. 处理密码
        String passwordHash;
        try {
            passwordHash = PasswordUtil.hash(password);
        } catch (IllegalStateException e) {
            throw new ServletException("注册时密码处理失败", e);
        }

// 2. 把注册信息封装成用户对象
        User user = new User(username, passwordHash, nickname);

// 3. 尝试保存，由仓库判断用户名是否重复
        boolean saved = UserRepository.save(user);

// 4. 根据保存结果反馈
        if (!saved) {
            request.setAttribute("message", "用户名已存在，请更换用户名");

            request.getRequestDispatcher("/register.jsp")
                    .forward(request, response);
            return;
        }

        request.setAttribute("message", "注册成功，用户已保存");

        request.getRequestDispatcher("/register.jsp")
                .forward(request, response); }
}
