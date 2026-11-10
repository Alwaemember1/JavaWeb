<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>个人中心</title>
</head>
<body>
<h1>个人中心</h1>
<p>用户名：<c:out value="${sessionScope.loginUser.username}" /></p>
<p>昵称：<c:out value="${sessionScope.loginUser.nickname}" /></p>
<c:choose>
    <c:when test="${not empty sessionScope.previousLoginTime}">
        <p>本浏览器上次成功登录时间：
            <c:out value="${sessionScope.previousLoginTime}" />
        </p>
    </c:when>
    <c:otherwise>
        <p>本浏览器暂无上次登录记录。</p>
    </c:otherwise>
</c:choose>
<c:url value="/book" var="bookUrl" />
<c:url value="/favorites" var="favoritesUrl" />
<c:url value="/logout" var="logoutUrl" />
<p><a href="${bookUrl}">返回图书列表</a></p>
<p><a href="${favoritesUrl}">我的收藏</a></p>
<form action="${logoutUrl}" method="post">
    <button type="submit">退出登录</button>
</form>
</body>
</html>
