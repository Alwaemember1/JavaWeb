<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>用户注册</h1>
    <c:out value="${requestScope.message}"/>
<c:url value="/register" var="registerUrl"/>
<form action="${registerUrl}" method="post">
    <p>用户名：<input type="text" name="username"></p>
    <p>密码：<input type="password" name="password"></p>
    <p>昵称：<input type="text" name="nickname"></p>
    <p><button type="submit">注册</button></p>
</form>
</body>
</html>
