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
<h2>我的头像</h2>
<c:url value="/avatar/image" var="avatarImageUrl" />
<c:choose>
    <c:when test="${requestScope.hasAvatar}">
        <img src="${avatarImageUrl}" alt="我的头像"
             width="120" height="120"
             style="object-fit:cover;border-radius:50%;">
    </c:when>
    <c:otherwise>
        <p>暂未上传头像。</p>
    </c:otherwise>
</c:choose>

<c:choose>
    <c:when test="${param.avatar eq 'success'}"><p>头像上传成功。</p></c:when>
    <c:when test="${param.avatar eq 'empty'}"><p>请选择图片。</p></c:when>
    <c:when test="${param.avatar eq 'size'}"><p>图片不能超过 2MB。</p></c:when>
    <c:when test="${param.avatar eq 'type'}"><p>只支持 JPG、JPEG 或 PNG 图片。</p></c:when>
    <c:when test="${param.avatar eq 'invalid'}"><p>图片内容无效或尺寸超过限制。</p></c:when>
</c:choose>

<c:url value="/avatar/upload" var="avatarUploadUrl" />
<form action="${avatarUploadUrl}" method="post" enctype="multipart/form-data">
    <label for="avatar">选择头像（JPG/PNG，最大 2MB）</label>
    <input type="file" id="avatar" name="avatar"
           accept=".jpg,.jpeg,.png,image/jpeg,image/png" required>
    <button type="submit">上传头像</button>
</form>

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
