<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>我的收藏</title>
</head>
<body>
<h1>收藏列表</h1>
<c:if test="${param.result eq 'removed'}">
    <p>已取消收藏。</p>
</c:if>
<c:if test="${param.result eq 'missing'}">
    <p>这条收藏已不存在，请以当前列表为准。</p>
</c:if>
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
