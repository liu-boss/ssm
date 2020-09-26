<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <meta charset='utf8' />
    <title></title>

    <style>
        *{ margin: 0; padding: 0;}
        body{font: 14px/1.4 Microsoft YaHei,SimSun,Arial;}
        .errorwarp{ padding-top: 100px;text-align: center;}
        p{ line-height: 94px;font-size: 18px;color: #14191e;}
        a{ font-size: 18px; color: #f01414; display: block; margin-left: auto; margin-right: auto; text-decoration: none;}
        .feedback{ font-size: 14px; margin-top: 38px;}
    </style>
</head>
<body>

<div class='errorwarp'>
    <img src='${pageContext.request.contextPath}/resource/image/404.gif' />
    <p>Sorry，找不到你想要的页面</p>
</div>
</body>
</html>
