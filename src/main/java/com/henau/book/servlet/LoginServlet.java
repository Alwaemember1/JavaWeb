package com.henau.book.servlet;

import com.henau.book.data.UserRepository;
import com.henau.book.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Cookie;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
        readLastLoginTime(request);

        request.getRequestDispatcher("/login.jsp")
                .forward(request, response);
    }

    private void readLastLoginTime(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("lastlogintime".equals(cookie.getName())) {
                    request.setAttribute(
                            "lastlogintime",
                            cookie.getValue()
                    );
                    break;
                }
            }
        }
    }
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        readLastLoginTime(request);

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


        User user = UserRepository.findByUsername(
                getServletContext(),
                username
        );


        if (user == null || !user.getPassword().equals(password)) {
            request.setAttribute("message", "用户名或密码不正确");

            request.getRequestDispatcher("/login.jsp")
                    .forward(request, response);
            return;
        }


        HttpSession session = request.getSession();
        session.setAttribute("loginUser", user);
        String currentTime = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(new Date());
        Cookie cookie = new Cookie("lastlogintime", currentTime);
        cookie.setMaxAge(60*60*24*7);
        cookie.setPath(request.getContextPath()+"/");
        response.addCookie(cookie);


        response.sendRedirect(request.getContextPath() + "/book");
    }
}