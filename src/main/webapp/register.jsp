<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>用户注册</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="/WEB-INF/nav.jspf" %>
<h1>用户注册</h1>

<p><c:out value="${requestScope.message}" /></p>

<c:url value="/register" var="registerUrl" />
<form action="${registerUrl}" method="post"
      onsubmit="return checkRegister();">
    <p>用户名：
        <input type="text" id="username" name="username">
    </p>
    <p>密码：
        <input type="password" id="password" name="password">
    </p>
    <p>昵称：
        <input type="text" id="nickname" name="nickname">
    </p>
    <button type="submit">注册</button>
</form>

<script>
function checkRegister() {
    let username = document.getElementById("username").value.trim();
    let password = document.getElementById("password").value;
    let nickname = document.getElementById("nickname").value.trim();

    if (username.length < 4 || username.length > 20) {
        alert("用户名长度必须为4～20个字符");
        return false;
    }

    if (password.trim().length === 0 ||
        password.length < 8 ||
        password.length > 32) {
        alert("密码不能为空，长度必须为8～32个字符");
        return false;
    }

    if (nickname.length === 0 || nickname.length > 20) {
        alert("昵称不能为空，且不能超过20个字符");
        return false;
    }

    return true;
}
</script>
</body>
</html>