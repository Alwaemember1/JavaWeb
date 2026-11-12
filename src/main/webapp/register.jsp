<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <%@ include file="/WEB-INF/head.jspf" %>
    <title>用户注册 · 书香校园</title>
</head>
<body>
<%@ include file="/WEB-INF/nav.jspf" %>
<main class="container main-content auth-page">
    <div class="auth-panel">
        <div class="auth-art" aria-hidden="true"></div>
        <div class="auth-content">
            <h1>创建账号</h1>
            <c:if test="${not empty requestScope.message}">
                <p class="notice notice-error" role="alert"><c:out value="${requestScope.message}" /></p>
            </c:if>
            <p class="notice notice-error" id="register-error" role="alert" hidden></p>
            <form class="form-stack" action="${navRegister}" method="post" onsubmit="return checkRegister();">
                <label class="field" for="username">用户名
                    <input type="text" id="username" name="username" autocomplete="username" placeholder="1～20 个字符">
                </label>
                <label class="field" for="nickname">昵称
                    <input type="text" id="nickname" name="nickname" autocomplete="nickname" placeholder="最多 20 个字符">
                </label>
                <label class="field" for="password">密码
                    <input type="password" id="password" name="password" autocomplete="new-password" placeholder="6～32 个字符">
                </label>
                <label class="field" for="confirmPassword">确认密码
                    <input type="password" id="confirmPassword" name="confirmPassword" autocomplete="new-password">
                </label>
                <button class="button button-primary button-full" type="submit">注册</button>
            </form>
            <p class="auth-switch">已有账号？ <a class="text-link" href="${navLogin}">登录</a></p>
        </div>
    </div>
</main>
<script>
function checkRegister() {
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;
    const confirmPassword = document.getElementById("confirmPassword").value;
    const nickname = document.getElementById("nickname").value;

    // 与 Servlet 的 String.isBlank() 和 String.trim() 使用相同的字符范围。
    const blank = /^[\t-\r\x1c-\x20\u1680\u2000-\u2006\u2008-\u200a\u2028\u2029\u205f\u3000]*$/;
    const outerSpaces = /^[\x00-\x20]+|[\x00-\x20]+$/g;
    const trimmedUsername = username.replace(outerSpaces, "");
    const trimmedNickname = nickname.replace(outerSpaces, "");

    if (blank.test(username) || trimmedUsername.length === 0) {
        return showRegisterError("用户名不能为空", "username");
    }
    if (trimmedUsername.length > 20) {
        return showRegisterError("用户名长度必须为1～20个字符", "username");
    }

    if (blank.test(password)) {
        return showRegisterError("密码不能为空", "password");
    }
    if (password.length < 6 || password.length > 32) {
        return showRegisterError("密码长度必须为6～32个字符", "password");
    }
    if (confirmPassword.length === 0) {
        return showRegisterError("请输入确认密码", "confirmPassword");
    }
    if (password !== confirmPassword) {
        return showRegisterError("两次输入的密码不一致", "confirmPassword");
    }

    if (blank.test(nickname) || trimmedNickname.length === 0) {
        return showRegisterError("昵称不能为空", "nickname");
    }
    if (trimmedNickname.length > 20) {
        return showRegisterError("昵称不能超过20个字符", "nickname");
    }

    return true;
}
function showRegisterError(message, field) {
    const error = document.getElementById("register-error");
    error.hidden = false;
    error.textContent = message;
    document.getElementById(field).focus();
    return false;
}
</script>
<%@ include file="/WEB-INF/footer.jspf" %>
</body>
</html>
