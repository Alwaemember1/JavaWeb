<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 26809
  Date: 2026/11/9
  Time: 23:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>收藏列表</h1>
<c:if test="${empty requestScope.favoriteBooks}">
    <p>暂无收藏图书。</p>
</c:if>
<c:forEach items="${requestScope.favoriteBooks}" var="book">
    <p>
    书名：
        <c:out value="${book.title}" />

    </p>
    <c:url value="/favorites" var="favoriteUrl" />

    <form action="${favoriteUrl}" method="post">
        <input type="hidden" name="action" value="remove">
        <input type="hidden" name="bookId" value="${book.id}">
        <button type="submit">取消收藏</button>
    </form>
    </c:forEach>
    <c:url value ="/book" var="bookUrl"/>
    <a href="${bookUrl}">返回图书列表</a>
</body>
</html>
