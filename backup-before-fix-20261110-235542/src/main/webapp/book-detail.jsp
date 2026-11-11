<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="UTF-8">
  <title>图书详情</title>
</head>
<body>
<h1><c:out value="${requestScope.book.title}" /></h1>

<p>
  作者：
  <c:out value="${requestScope.book.author}" />
</p>
<p>类别：
    <c:out value="${requestScope.book.category}"/>
</p>
<p>
  简介：
  <c:out value="${requestScope.book.description}" />
</p>

<c:choose>
  <c:when test="${requestScope.book.available}">
    <p>状态：在架</p>
  </c:when>
  <c:otherwise>
    <p>状态：已借出</p>
  </c:otherwise>
</c:choose>

<c:url value="/book" var="listUrl" />
<a href="${listUrl}">返回图书列表</a>
</body>
</html>