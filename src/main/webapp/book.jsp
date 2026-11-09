<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="UTF-8">
  <title>图书列表</title>
</head>
<body>
<h1>书香校园图书列表</h1>
<c:choose>
  <c:when test="${not empty sessionScope.loginUser}">
    <p>
      欢迎，
      <c:out value="${sessionScope.loginUser.nickname}" />
    </p>
    <c:url value="/logout" var="logoutUrl" />

    <form action="${logoutUrl}" method="post">
      <button type="submit">退出登录</button>
    </form>
  </c:when>

  <c:otherwise>
    <c:url value="/login" var="loginUrl" />
    <a href="${loginUrl}">登录</a>
  </c:otherwise>
</c:choose>
<c:if test="${empty requestScope.books}">
  <p>暂无图书。</p>
</c:if>

<c:forEach items="${requestScope.books}"
           var="book"
           varStatus="status">

  <div>
    <h2>
        ${status.count}.
      <c:out value="${book.title}" />
    </h2>

    <p>作者：<c:out value="${book.author}" /></p>

    <c:choose>
      <c:when test="${book.available}">
        <p>状态：在架</p>
      </c:when>
      <c:otherwise>
        <p>状态：已借出</p>
      </c:otherwise>
    </c:choose>

    <c:url value="/book/detail" var="detailUrl">
      <c:param name="id" value="${book.id}" />
    </c:url>

    <a href="${detailUrl}">查看详情</a>
    <c:url value="/favorites" var="favoriteUrl">
    </c:url>
    form action="${favoriteUrl}" method="post">
      <input type="hidden" name="bookId" value="${book.id}" />
      <button type="submit">加入收藏</button>
    <hr>
  </div>
</c:forEach>
</body>
</html>