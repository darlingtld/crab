<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page session="true" contentType="text/html; charset=GB2312" %>
<html>
<head>
    <meta charset="UTF-8">
    <link href="../bower_components/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
    <title>登陆</title>

</head>
<body>

<div class="container">
    <h1 class="text-center">管理员登录</h1>

    <div id="login-box">

        <h3>请输入用户名和密码</h3>
        <c:if test="${not empty error}">
            <div class="error">${error}</div>
        </c:if>
        <c:if test="${not empty msg}">
            <div class="msg">${msg}</div>
        </c:if>
        <form role="form" name='loginForm' action="<c:url value='/j_spring_security_check' />" method='POST'>
            <div class="form-group">
                <label for="name">用户名</label>
                <input type="text" class="form-control" id="name" name="j_username"
                       placeholder="请输入用户名">
            </div>
            <div class="form-group">
                <label for="password">密码</label>
                <input type="password" class="form-control" id="password" name="j_password"
                       placeholder="请输入密码">
            </div>
            <button type="submit" class="btn btn-default">登录</button>
        </form>

    </div>

</div>


</body>
</html>