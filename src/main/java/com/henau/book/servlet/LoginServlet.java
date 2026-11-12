package com.henau.book.servlet;

import com.henau.book.auth.LoginSession;
import com.henau.book.auth.RememberLogin;
import com.henau.book.data.UserRepository;
import com.henau.book.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (LoginSession.currentUser(request) != null) {
            response.sendRedirect(request.getContextPath() + "/book");
            return;
        }
        showLogin(request, response, null);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            showLogin(request, response, "用户名和密码不能为空");
            return;
        }

        User user = UserRepository.findByUsername(getServletContext(), username.trim());
        if (user == null || !user.getPassword().equals(password)) {
            showLogin(request, response, "用户名或密码不正确");
            return;
        }

        boolean remember = "1".equals(request.getParameter("remember"));
        RememberLogin.from(getServletContext()).update(request, response, user, remember);
        LoginSession.signIn(request, response, user);
        response.sendRedirect(request.getContextPath() + "/book");
    }

    private void showLogin(HttpServletRequest request, HttpServletResponse response, String message)
            throws ServletException, IOException {
        request.setAttribute("message", message);
        request.setAttribute("lastLoginTime", LoginSession.lastLoginTime(request));
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }
}
