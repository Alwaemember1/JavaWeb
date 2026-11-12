package com.henau.book.servlet;

import com.henau.book.data.UserRepository;
import com.henau.book.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String nickname = request.getParameter("nickname");

        if (username == null || username.isBlank() || username.trim().isEmpty()) {
            showMessage(request, response, "用户名不能为空");
            return;
        }
        username = username.trim();
        if (username.length() > 20) {
            showMessage(request, response, "用户名长度必须为1～20个字符");
            return;
        }

        if (password == null || password.isBlank()) {
            showMessage(request, response, "密码不能为空");
            return;
        }
        if (password.length() < 6 || password.length() > 32) {
            showMessage(request, response, "密码长度必须为6～32个字符");
            return;
        }
        if (confirmPassword == null || confirmPassword.isEmpty()) {
            showMessage(request, response, "请输入确认密码");
            return;
        }
        if (!password.equals(confirmPassword)) {
            showMessage(request, response, "两次输入的密码不一致");
            return;
        }

        if (nickname == null || nickname.isBlank() || nickname.trim().isEmpty()) {
            showMessage(request, response, "昵称不能为空");
            return;
        }
        nickname = nickname.trim();
        if (nickname.length() > 20) {
            showMessage(request, response, "昵称不能超过20个字符");
            return;
        }

        User user = new User(username, password, nickname);
        boolean saved = UserRepository.save(getServletContext(), user);

        if (!saved) {
            showMessage(request, response, "用户名已存在，请更换用户名");
            return;
        }

        response.sendRedirect(
                request.getContextPath() + "/login?registered=1"
        );
    }

    private void showMessage(HttpServletRequest request, HttpServletResponse response,
                             String message) throws ServletException, IOException {
        request.setAttribute("message", message);
        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }
}
