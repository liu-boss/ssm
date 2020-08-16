<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>首页</title>
</head>
<body>
<h1 style="color: green;text-align: center">哎哟不错哦!,一个基础的SSM整合</h1>
<hr>
<h3>文件上传</h3>
<form action="/user/upload.do" method="post" enctype="multipart/form-data">
    选择文件:<input type="file" name="file" width="120px">
    <input type="submit" value="上传">
</form>
<hr>
<h3>发送请求</h3>
<hr>
http://localhost:8080/user/list.do?page=1&size=3
<hr>
<h3>页面异常处理</h3>
<hr>
http://localhost:8080/user/list.do?page=4&size=3
<hr>
</body>

</html>
