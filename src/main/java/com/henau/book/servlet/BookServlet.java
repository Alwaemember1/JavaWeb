package com.henau.book.servlet;

import com.henau.book.data.BookRepository;
import com.henau.book.model.Book;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/book")
public class BookServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        // 1. 获取全部图书
        List<Book> books = BookRepository.findAll();

        // 2. 放入请求属性
        request.setAttribute("books", books);

        // 3. 转发到列表页
        request.getRequestDispatcher("/book.jsp")
                .forward(request, response);
    }
}