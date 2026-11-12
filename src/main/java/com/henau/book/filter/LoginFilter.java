package com.henau.book.filter;

import com.henau.book.auth.LoginSession;
import com.henau.book.auth.RememberLogin;
import com.henau.book.model.User;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Set;

public class LoginFilter implements Filter {
    private static final Set<String> PUBLIC_PATHS = Set.of("/login", "/book", "/book/detail");

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String path = req.getServletPath();
        User user = LoginSession.currentUser(req);

        if (user == null && !("/login".equals(path) && "POST".equals(req.getMethod()))) {
            user = RememberLogin.from(req.getServletContext()).restore(req, resp);
            if (user != null) {
                LoginSession.signIn(req, resp, user);
            }
        }

        if (user == null && !PUBLIC_PATHS.contains(path)) {
            resp.sendRedirect(req.getContextPath() + "/login?required=1");
            return;
        }
        chain.doFilter(request, response);
    }
}
