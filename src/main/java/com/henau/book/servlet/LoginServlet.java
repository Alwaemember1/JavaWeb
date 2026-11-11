package com.henau.book.servlet;

import com.henau.book.data.UserRepository;
import com.henau.book.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/login.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username == null || username.isBlank()
                || password == null || password.isBlank()) {
            showError(request, response, "用户名和密码不能为空");
            return;
        }

        username = username.trim();

        User user = UserRepository.findByUsername(
                getServletContext(), username);

        if (user == null || !user.getPassword().equals(password)) {
            showError(request, response, "用户名或密码不正确");
            return;
        }

        // 每个用户名有自己的浏览器 Cookie 名称
        String encodedUsername = Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(
                    username.getBytes(StandardCharsets.UTF_8));

        String cookieName = "lastlogin_" + encodedUsername;
        String previousTime = null;

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (cookieName.equals(c.getName())) {
                    try {
                        long millis = Long.parseLong(c.getValue());
                        if (millis > 0
                                && millis <= System.currentTimeMillis()) {
                            previousTime = new SimpleDateFormat(
                                    "yyyy-MM-dd HH:mm:ss")
                                    .format(new Date(millis));
                        }
                    } catch (NumberFormatException ignored) {
                        // 无效 Cookie 不影响登录
                    }
                    break;
                }
            }
        }

        // 登录成功时更新 Session
        HttpSession oldSession = request.getSession(false);
        if (oldSession != null) {
            oldSession.invalidate();
        }
        HttpSession session = request.getSession(true);
        session.setAttribute("loginUser", user);
        session.setAttribute("previousLoginTime", previousTime);

        // 再把本次登录时间写入 Cookie
        Cookie cookie = new Cookie(
                cookieName,
                String.valueOf(System.currentTimeMillis()));

        cookie.setHttpOnly(true);
        cookie.setMaxAge(7 * 24 * 60 * 60);
        cookie.setPath(
                request.getContextPath().isEmpty()
                        ? "/"
                        : request.getContextPath() + "/");

        response.addCookie(cookie);
        response.sendRedirect(request.getContextPath() + "/book");
    }

    private void showError(HttpServletRequest request,
                           HttpServletResponse response,
                           String message)
            throws ServletException, IOException {
        request.setAttribute("message", message);
        request.getRequestDispatcher("/login.jsp")
                .forward(request, response);
    }
}