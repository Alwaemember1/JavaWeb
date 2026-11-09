package com.henau.book.servlet;
import com.henau.book.data.BookRepository;
import com.henau.book.data.FavoriteReposity;
import com.henau.book.model.Book;
import com.henau.book.model.User;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/favorites")
public class FavoriteServlet extends HttpServlet {
    @Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("text/plain;charset=UTF-8");
        response.getWriter().println("已进入收藏夹");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
       HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loginUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

       User user = (User) session.getAttribute("loginUser");
        String idText = request.getParameter("bookId");
        int bookId;
        try {
            bookId = Integer.parseInt(idText);
        } catch (NumberFormatException e) {
            response.sendError(400, "图书编号格式不正确");
            return;
        }

        Book book = BookRepository.findById(bookId);
        if (book == null) {
            response.sendError(404, "图书不存在");
            return;
        }
        boolean saved = FavoriteReposity.add(getServletContext(), user.getUsername(), bookId);
        String result = saved ? "收藏成功" : "已收藏过该图书";
        response.sendRedirect(request.getContextPath() + "/book/?favorite=" + result);
    }
}

