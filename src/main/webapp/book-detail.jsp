<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <%@ include file="/WEB-INF/head.jspf" %>
    <title><c:out value="${requestScope.book.title}" /> · 书香校园</title>
</head>
<body>
<c:set var="activePage" value="books" />
<%@ include file="/WEB-INF/nav.jspf" %>
<c:set var="book" value="${requestScope.book}" />
<main class="container main-content inner-page">
    <a class="back-link" href="${navBook}"><span aria-hidden="true">←</span> 返回图书列表</a>
    <article class="detail-panel">
        <div class="detail-art">
            <img src="${pageContext.request.contextPath}/images/covers/${book.id}.jpg"
                 alt="《<c:out value='${book.title}' />》封面" width="480" height="720">
        </div>
        <div class="detail-content">
            <div class="book-meta">
                <span class="book-category"><c:out value="${book.category}" /></span>
                <c:if test="${book.available}"><span class="status status-available">在架</span></c:if>
                <c:if test="${not book.available}"><span class="status status-borrowed">已借出</span></c:if>
            </div>
            <h1><c:out value="${book.title}" /></h1>
            <p class="detail-author">作者 <c:out value="${book.author}" /></p>
            <div class="detail-description">
                <h2>图书简介</h2>
                <p><c:out value="${book.description}" /></p>
            </div>
        </div>
    </article>
</main>
<%@ include file="/WEB-INF/footer.jspf" %>
</body>
</html>
