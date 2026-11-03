<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<body>
<h2>Hello World!</h2>

<form action="hello" method="get">
    <%= "姓名 " %>   <input type="text" name="username" /> <br>
    <%= "班级 " %>  <input type="text" name="className" /> <br>
    <button type="submit">提交</button>
</form>
</body>
</html>
