package com.henau.book.servlet;

import com.henau.book.data.AvatarRepository;
import com.henau.book.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // LoginFilter 保证访问这里时 Session 中已有 loginUser。
        User user = (User) request.getSession(false).getAttribute("loginUser");
        request.setAttribute("hasAvatar",
                AvatarRepository.find(getServletContext(), user.getUsername()) != null);
        request.getRequestDispatcher("/WEB-INF/profile.jsp")
                .forward(request, response);
    }
}
