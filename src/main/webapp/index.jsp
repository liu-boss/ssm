<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/6/22
  Time: 18:01
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>首页</title>
</head>
<body>
<%
    response.sendRedirect(request.getContextPath()+"/loginPage.do");
%>
</body>
</html>
