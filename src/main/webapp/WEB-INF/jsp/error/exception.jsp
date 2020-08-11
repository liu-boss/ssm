<%@page import="java.io.PrintStream"%>
<%@page import="java.io.ByteArrayOutputStream"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isErrorPage="true"%>
<html>
<head>
    <title>出错误了</title>
</head>
<body>

<h1>哎呀!,出错了</h1>
<h4>错误内容:<span style="color: red">[<%=exception.getMessage()%>]</span></h4>
<div style="display:block;color: #f40;font-size: 12px;font-weight: 600">
    <%  //此处输出异常信息
        ByteArrayOutputStream ostr = new ByteArrayOutputStream();
        exception.printStackTrace(new PrintStream(ostr));
        out.print(ostr);
    %>
</div>
</body>
</html>
