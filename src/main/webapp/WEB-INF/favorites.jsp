<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <%@ include file="/WEB-INF/head.jspf" %>
    <title>我的收藏 · 书香校园</title>
</head>
<body>
<c:set var="activePage" value="favorites" />
<%@ include file="/WEB-INF/nav.jspf" %>
<main class="container main-content inner-page">
    <a class="back-link" href="${navBook}"><span aria-hidden="true">←</span> 返回图书列表</a>
    <div class="section-heading page-heading">
        <h1>我的收藏</h1>
        <span class="section-count">共 ${fn:length(requestScope.favoriteBooks)} 本</span>
    </div>
    <c:if test="${param.result eq 'removed'}">
        <p class="notice notice-success" role="status">已取消收藏。</p>
    </c:if>
    <c:if test="${param.result eq 'missing'}">
        <p class="notice" role="status">这条收藏已不存在，请以当前列表为准。</p>
    </c:if>
    <c:choose>
        <c:when test="${empty requestScope.favoriteBooks}">
            <div class="empty-state">
                <h2>暂无收藏图书</h2>
                <a class="button button-primary" href="${navBook}">浏览馆藏</a>
            </div>
        </c:when>
        <c:otherwise>
            <div class="book-grid">
                <c:forEach items="${requestScope.favoriteBooks}" var="book" varStatus="cardStatus">
                    <%@ include file="/WEB-INF/book-card.jspf" %>
                </c:forEach>
            </div>
        </c:otherwise>
    </c:choose>
</main>
<%@ include file="/WEB-INF/footer.jspf" %>
</body>
</html>
