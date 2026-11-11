<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>用户登录</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="/WEB-INF/nav.jspf" %>
<h1>用户登录</h1>
<c:if test="${param.registered eq '1'}">
    <p>注册成功，请登录。</p>
</c:if>

<p><c:out value="${requestScope.message}" /></p>

<c:url value="/login" var="loginUrl" />

<form action="${loginUrl}" method="post">
    <p>用户名：<input type="text" name="username"></p>
    <p>密码：<input type="password" name="password"></p>
    <button type="submit">登录</button>
</form>

<c:url value="/register" var="registerUrl" />
<a href="${registerUrl}">注册账号</a>
</body>
</html>