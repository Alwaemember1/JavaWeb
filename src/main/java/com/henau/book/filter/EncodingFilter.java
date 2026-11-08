package com.henau.book.filter;

import jakarta.servlet.*;

import java.io.IOException;

public class EncodingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        // ① 设置请求编码
        request.setCharacterEncoding("UTF-8");

        // ② 设置响应字符编码
        response.setCharacterEncoding("UTF-8");

        // ③ 继续处理请求
        chain.doFilter(request, response);
    }
}