package com.henau.book.auth;

import com.henau.book.model.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class LoginSession {
    private static final String LAST_LOGIN_COOKIE = "lastlogintime";
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");

    private LoginSession() {
    }

    public static User currentUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session == null ? null : (User) session.getAttribute("loginUser");
    }

    public static String lastLoginTime(HttpServletRequest request) {
        return cookieValue(request, LAST_LOGIN_COOKIE);
    }

    public static void signIn(HttpServletRequest request, HttpServletResponse response, User user) {
        HttpSession session = request.getSession();
        if (!session.isNew()) {
            request.changeSessionId();
        }
        session.setAttribute("loginUser", user);
        session.setAttribute("previousLoginTime", lastLoginTime(request));
        setCookie(request, response, LAST_LOGIN_COOKIE, LocalDateTime.now().format(TIME_FORMAT),
                7 * 24 * 60 * 60);
    }

    static String cookieValue(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    static void setCookie(HttpServletRequest request, HttpServletResponse response,
                          String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath(request.getContextPath() + "/");
        cookie.setMaxAge(maxAge);
        cookie.setHttpOnly(true);
        cookie.setSecure(request.isSecure());
        cookie.setAttribute("SameSite", "Lax");
        response.addCookie(cookie);
    }
}
