package com.henau.book.servlet;
import com.henau.book.data.BookRepository;
import com.henau.book.data.FavoriteRepository;
import com.henau.book.model.Book;
import com.henau.book.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/favorites")
public class FavoriteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null
                || session.getAttribute("loginUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        User user = (User) session.getAttribute("loginUser");

        // ① 查询当前用户收藏的完整图书对象
        List<Book> books = FavoriteRepository.findBooksByUsername(
                getServletContext(),
                user.getUsername()
        );

        // ② 将集合放入请求，属性名称统一叫 favoriteBooks
        request.setAttribute("favoriteBooks", books);

        // ③ 转发到收藏页面
        request.getRequestDispatcher("/favorites.jsp")
                .forward(request, response);
    }

    @Override
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
        // 先处理取消分支，结束后不再执行添加。
        String action = request.getParameter("action");

        if ("remove".equals(action)) {

            // ③ 调用删除方法，身份必须来自 Session 中的 user
            boolean removed = FavoriteRepository.remove(
                    getServletContext(),
                    user.getUsername(),
                    bookId
            );

            String removeResult = removed ? "removed" : "missing";

            response.sendRedirect(
                    request.getContextPath()
                            + "/favorites?result=" + removeResult
            );
            return;
        }

        boolean saved = FavoriteRepository.add(
                getServletContext(), user.getUsername(), bookId
        );
        String result = saved ? "added" : "exists";

        response.sendRedirect(
                request.getContextPath() + "/book?favorite=" + result
        );
    }
}
