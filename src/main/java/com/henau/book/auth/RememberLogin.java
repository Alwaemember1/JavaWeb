package com.henau.book.auth;

import com.henau.book.model.User;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public final class RememberLogin {
    public static final String CONTEXT_ATTRIBUTE = "rememberLogin";
    private static final String COOKIE_NAME = "rememberLogin";
    private static final int MAX_AGE = 7 * 24 * 60 * 60;

    // Cookie 只保存随机凭据，用户身份和过期时间保存在服务器内存中。
    private final ConcurrentMap<String, LoginToken> tokens = new ConcurrentHashMap<>();

    public static RememberLogin from(ServletContext context) {
        return (RememberLogin) context.getAttribute(CONTEXT_ATTRIBUTE);
    }

    public void update(HttpServletRequest request, HttpServletResponse response, User user, boolean remember) {
        revoke(request);
        if (!remember) {
            clearCookie(request, response);
            return;
        }

        Instant now = Instant.now();
        tokens.values().removeIf(token -> !token.expiresAt().isAfter(now));
        String value = UUID.randomUUID().toString();
        tokens.put(value, new LoginToken(user, now.plusSeconds(MAX_AGE)));
        LoginSession.setCookie(request, response, COOKIE_NAME, value, MAX_AGE);
    }

    public User restore(HttpServletRequest request, HttpServletResponse response) {
        String value = LoginSession.cookieValue(request, COOKIE_NAME);
        if (value == null) {
            return null;
        }

        LoginToken token = tokens.get(value);
        if (token != null && token.expiresAt().isAfter(Instant.now())) {
            return token.user();
        }
        if (token != null) {
            tokens.remove(value, token);
        }
        clearCookie(request, response);
        return null;
    }

    public void forget(HttpServletRequest request, HttpServletResponse response) {
        revoke(request);
        clearCookie(request, response);
    }

    private void revoke(HttpServletRequest request) {
        String value = LoginSession.cookieValue(request, COOKIE_NAME);
        if (value != null) {
            tokens.remove(value);
        }
    }

    private void clearCookie(HttpServletRequest request, HttpServletResponse response) {
        LoginSession.setCookie(request, response, COOKIE_NAME, "", 0);
    }

    private record LoginToken(User user, Instant expiresAt) {
    }
}
