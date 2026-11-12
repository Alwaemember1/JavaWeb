<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <%@ include file="/WEB-INF/head.jspf" %>
    <title>个人中心 · 书香校园</title>
</head>
<body>
<c:set var="activePage" value="profile" />
<%@ include file="/WEB-INF/nav.jspf" %>
<c:url value="/avatar/image" var="avatarImageUrl" />
<c:url value="/avatar/upload" var="avatarUploadUrl" />
<main class="container main-content inner-page">
    <div class="section-heading page-heading"><h1>个人中心</h1></div>
    <div class="profile-grid">
        <section class="profile-panel" aria-labelledby="account-heading">
            <h2 id="account-heading">账号信息</h2>
            <dl class="account-info">
                <div>
                    <dt>用户名</dt>
                    <dd><c:out value="${sessionScope.loginUser.username}" /></dd>
                </div>
                <div>
                    <dt>昵称</dt>
                    <dd><c:out value="${sessionScope.loginUser.nickname}" /></dd>
                </div>
                <div>
                    <dt>本浏览器上次成功登录时间</dt>
                    <dd>
                        <c:choose>
                            <c:when test="${not empty sessionScope.previousLoginTime}">
                                <c:out value="${sessionScope.previousLoginTime}" />
                            </c:when>
                            <c:otherwise>暂无上次登录记录</c:otherwise>
                        </c:choose>
                    </dd>
                </div>
            </dl>
            <a class="text-link" href="${navFavorites}">查看我的收藏 <span aria-hidden="true">↗</span></a>
        </section>
        <section class="profile-panel" aria-labelledby="avatar-heading">
            <h2 id="avatar-heading">我的头像</h2>
            <c:choose>
                <c:when test="${requestScope.hasAvatar}">
                    <img class="profile-avatar" src="${avatarImageUrl}" alt="我的头像" width="96" height="96">
                </c:when>
                <c:otherwise>
                    <div class="profile-avatar avatar-placeholder" aria-hidden="true"></div>
                    <p class="auth-note">暂未上传头像。</p>
                </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${param.avatar eq 'success'}"><p class="notice notice-success" role="status">头像上传成功。</p></c:when>
                <c:when test="${param.avatar eq 'empty'}"><p class="notice notice-error" role="alert">请选择图片。</p></c:when>
                <c:when test="${param.avatar eq 'size'}"><p class="notice notice-error" role="alert">图片不能超过 2MB。</p></c:when>
                <c:when test="${param.avatar eq 'type'}"><p class="notice notice-error" role="alert">只支持 JPG、JPEG 或 PNG 图片。</p></c:when>
                <c:when test="${param.avatar eq 'invalid'}"><p class="notice notice-error" role="alert">图片内容无效或尺寸超过限制。</p></c:when>
            </c:choose>
            <form class="form-stack" action="${avatarUploadUrl}" method="post" enctype="multipart/form-data">
                <label class="field" for="avatar">选择头像 <span class="field-hint">JPG 或 PNG，最大 2 MB</span>
                    <input type="file" id="avatar" name="avatar" accept=".jpg,.jpeg,.png,image/jpeg,image/png" required>
                </label>
                <button class="button button-primary" type="submit">上传头像</button>
            </form>
        </section>
    </div>
</main>
<%@ include file="/WEB-INF/footer.jspf" %>
</body>
</html>
