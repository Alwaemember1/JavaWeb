package com.henau.book.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/profile")
public class LoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        // ① 只取得已有 Session，没有就返回 null
        HttpSession session = req.getSession(false);

        // ② 没有 Session，或没有登录用户，均视为未登录
        if (session == null
                || session.getAttribute("loginUser") == null) {

            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // ③ 已登录，继续处理请求
        chain.doFilter(request, response);
    }
}