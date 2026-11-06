<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>用户注册</title>
</head>
<body>
<h1>用户注册</h1>
<p><c:out value="${requestScope.message}" /></p>
<c:url value="/register" var="registerUrl" />
<form action="${registerUrl}" method="post">
    <p>用户名：<input type="text" name="username"></p>
    <p>密码：<input type="password" name="password"></p>
    <p>昵称：<input type="text" name="nickname"></p>
    <p><button type="submit">注册</button></p>
</form>
</body>
</html>
