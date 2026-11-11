<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>个人中心</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="/WEB-INF/nav.jspf" %>
<h1>个人中心</h1>

<h2>我的资料</h2>
<p>用户名：
    <c:out value="${sessionScope.loginUser.username}" />
</p>
<p>昵称：
    <c:out value="${sessionScope.loginUser.nickname}" />
</p>

<c:choose>
    <c:when test="${not empty sessionScope.previousLoginTime}">
        <p>本浏览器该账号上次登录时间：
            <c:out value="${sessionScope.previousLoginTime}" />
        </p>
    </c:when>
    <c:otherwise>
        <p>本浏览器暂无该账号的上次登录记录。</p>
    </c:otherwise>
</c:choose>

<c:url value="/favorites" var="favoritesUrl" />
<p><a href="${favoritesUrl}">查看我的收藏</a></p>
</body>
</html>