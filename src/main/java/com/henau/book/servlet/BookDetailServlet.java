package com.henau.book.servlet;

import com.henau.book.data.BookRepository;
import com.henau.book.model.Book;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/book/detail")
public class BookDetailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        // 1. 读取浏览器传来的图书 ID
        String idText = request.getParameter("id");

        // 2. 转换成整数
        int id;
        try {
            id = Integer.parseInt(idText);
        } catch (NumberFormatException e) {
            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "图书编号格式不正确"
            );
            return;
        }

        // 3. 按 ID 查找图书
        Book book = BookRepository.findById(id);

        // 4. 没找到时结束请求
        if (book == null) {
            response.sendError(
                    HttpServletResponse.SC_NOT_FOUND,
                    "图书不存在"
            );
            return;
        }

        // 5. 将找到的对象交给 JSP
        request.setAttribute("book", book);

        request.getRequestDispatcher("/book-detail.jsp")
                .forward(request, response);
    }
}