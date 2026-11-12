<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <%@ include file="/WEB-INF/head.jspf" %>
    <title>用户登录 · 书香校园</title>
</head>
<body>
<%@ include file="/WEB-INF/nav.jspf" %>
<main class="container main-content auth-page">
    <div class="auth-panel">
        <div class="auth-art" aria-hidden="true"></div>
        <div class="auth-content">
            <h1>用户登录</h1>
            <c:if test="${param.registered eq '1'}">
                <p class="notice notice-success" role="status">注册成功，请登录。</p>
            </c:if>
            <c:if test="${param.required eq '1'}">
                <p class="notice" role="status">请先登录，再访问该功能。</p>
            </c:if>
            <c:if test="${not empty requestScope.lastLoginTime}">
                <p class="auth-note">您上次登录时间：<c:out value="${requestScope.lastLoginTime}" /></p>
            </c:if>
            <c:if test="${not empty requestScope.message}">
                <p class="notice notice-error" role="alert"><c:out value="${requestScope.message}" /></p>
            </c:if>
            <form class="form-stack" action="${navLogin}" method="post">
                <label class="field">用户名
                    <input type="text" name="username" autocomplete="username" required>
                </label>
                <label class="field">密码
                    <input type="password" name="password" autocomplete="current-password" required>
                </label>
                <label class="check-field"><input type="checkbox" name="remember" value="1"> 7 天内自动登录</label>
                <button class="button button-primary button-full" type="submit">登录</button>
            </form>
            <p class="auth-switch">还没有账号？ <a class="text-link" href="${navRegister}">注册账号</a></p>
        </div>
    </div>
</main>
<%@ include file="/WEB-INF/footer.jspf" %>
</body>
</html>
