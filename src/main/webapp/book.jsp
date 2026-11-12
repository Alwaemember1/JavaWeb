<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <%@ include file="/WEB-INF/head.jspf" %>
    <title>书香校园 · 图书漂流站</title>
</head>
<body>
<c:set var="activePage" value="books" />
<%@ include file="/WEB-INF/nav.jspf" %>
<main class="container main-content">
    <section class="hero" aria-labelledby="page-title">
        <div class="hero-content">
            <h1 id="page-title">图书漂流站</h1>
            <p>浏览校园共享图书，收藏想读的下一本。</p>
            <a class="button button-light" href="#catalog">浏览馆藏 <span aria-hidden="true">↓</span></a>
        </div>
    </section>
    <section id="catalog" class="catalog" aria-labelledby="catalog-title">
        <div class="section-heading">
            <h2 id="catalog-title">馆藏图书</h2>
            <span class="section-count">共 ${fn:length(requestScope.books)} 本</span>
        </div>
        <c:if test="${param.favorite eq 'added'}">
            <p class="notice notice-success" role="status">收藏成功，可以进入“我的收藏”查看。</p>
        </c:if>
        <c:if test="${param.favorite eq 'exists'}">
            <p class="notice" role="status">你已经收藏过这本书了。</p>
        </c:if>
        <c:choose>
            <c:when test="${empty requestScope.books}">
                <p class="empty-state">暂无图书。</p>
            </c:when>
            <c:otherwise>
                <div class="book-grid">
                    <c:forEach items="${requestScope.books}" var="book" varStatus="cardStatus">
                        <%@ include file="/WEB-INF/book-card.jspf" %>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>
    </section>
</main>
<%@ include file="/WEB-INF/footer.jspf" %>
</body>
</html>
