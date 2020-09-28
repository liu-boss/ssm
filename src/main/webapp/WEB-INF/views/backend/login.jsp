<%@ page import="com.coderman.backend.util.ShiroContextHolder" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta charset="utf-8"/>
    <title>用户登录</title>
    <%@include file="/WEB-INF/views/backend/common/link.jsp" %>
    <%@include file="/WEB-INF/views/backend/common/tag.jsp" %>
    <style>
        html, body {
            width: 100%;
            height: 100%;
            margin: 0;
            padding: 0;
            background: #2d3a4b;
        }

        h1 {
            color: rgb(128, 128, 128);
            text-align: center;
        }

        a:link, a:hover, a:visited, a:active {
            color: rgb(128, 128, 128);
            text-decoration: none;
        }
        label{
            font-size: 11px;
        }
        form {
            width: 400px;
            min-width: 400px;
            position: absolute;
            left: 50%;
            transform: translateX(-50%);
            top: 19%;
            margin: 0;
            padding: 30px;
            background-color: white;
            border: 2px solid rgba(128, 128, 128, 0.2);
            border-radius: 10px;
        }

        form div {
            margin-bottom: 8px;
        }
    </style>
</head>
<body>

<%
    String baseUrlPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
    if (ShiroContextHolder.getUser() != null) {
        //如果已登入,跳转到后台主页
        response.sendRedirect(request.getContextPath() + "/backend/mainPage.do");
    }
%>
<form action="#">
    <div>
        <h1><fmt:message key="i18n.title"></fmt:message></h1>
    </div>
    <div>
        <input  class="easyui-textbox easyui-validatebox" id="username" value="zhangyukang" name="username"
               data-options="iconCls:'icon-man',iconWidth:30,iconAlign:'left',prompt:'<fmt:message key="i18n.username"></fmt:message>'"
               style="width:100%;height:35px;"/>
    </div>
    <div>
        <input class="easyui-passwordbox easyui-validatebox" id="password" value="123456" name="password"
               data-options="iconWidth:30,iconAlign:'left',prompt:'<fmt:message key="i18n.password"></fmt:message>'"
               style="width:100%;height:35px;"/>
    </div>

    <div style="overflow: hidden">
        <input class="easyui-textbox easyui-validatebox" id="verCode" value="" name="verCode"
               data-options="iconCls:'icon-reload',iconWidth:30,iconAlign:'left',prompt:'<fmt:message key="i18n.code"></fmt:message>'"
               style="width:60%;height:35px;float: left"/>
        <img style="float: right" src="${pageContext.request.contextPath}/backend/system/user/captcha.do" id="yzm">

        <script>
            $('#yzm').css('cursor', 'pointer').click(function () {
                $('#yzm').attr('src', '${pageContext.request.contextPath}/backend/system/user/captcha.do?time' + new Date().getTime());
            });
        </script>
    </div>
    <div>
        <input class="easyui-checkbox" type="checkbox" id="remember" label="<fmt:message key="i18n.rememberMe"></fmt:message>" labelPosition="after" labelWidth="100"/>
    </div>
    <div>
        <input class="easyui-linkbutton" type="button" id="loginBtn" value="<fmt:message key="i18n.loginBtn"></fmt:message>" style="width:100%;height:35px;"/>
    </div>
    <div>
        <div style="display:inline;">
            <a href="javascript:void(0)"><fmt:message key="i18n.createAccount"></fmt:message></a>
        </div>
        <div style="display:inline;margin-left:170px;">
            <a href="javascript:void(0)"><fmt:message key="i18n.forgetPass"></fmt:message></a>
        </div>
    </div>

    <div style="width: 100%;text-align: center">
        <div style="display:inline-block;">
            <a style="color: #333333;font-size: 13px" href="<%=baseUrlPath%>/backend/loginPage.do?locale=en_US">English</a>
        </div>
        <span style="font-size: 13px;color: #999">|</span>
        <div style="display:inline-block;">
            <a style="color: #333333;font-size: 13px" href="<%=baseUrlPath%>/backend/loginPage.do?locale=zh_CN">简体中文</a>
        </div>
    </div>

</form>
</body>
</html>

<script type="text/javascript">


    $(function () {
        /**
         * 解决session 失效页面在tab里的问题
         */
        if (window !== top) {
            top.location.href = location.href;
        }

        /**
         *
         * 检查是session的状态
         */
        let status=<%=request.getParameter("forceLogout")%>;
        if(status!=null&&status===1){
            if(confirm("您已被踢出系统,是否重新登入?")){
                window.location.href = '${pageContext.request.contextPath}/backend/loginPage.do';
            }
            return;
        }

        /**
         * 用户登入
         */
        function login() {
            let username = $("#username").val();
            let password = $("#password").val();
            let verCode = $("#verCode").val();
            // let remember = $("#remember").checkbox('options').checked;
            if (username == null || username === '') {
                $.messager.alert('<fmt:message key="i18n.tip"></fmt:message>', '<fmt:message key="i18n.emptyUsername"></fmt:message>','warning');
                return;
            } else if (password == null || password === '') {
                $.messager.alert('<fmt:message key="i18n.tip"></fmt:message>', '<fmt:message key="i18n.emptyPassword"></fmt:message>','warning');
                return;
            } else if (verCode == null || verCode === '') {
                $.messager.alert('<fmt:message key="i18n.tip"></fmt:message>', '<fmt:message key="i18n.emptyCode"></fmt:message>','warning');
                return;
            }
            let data = {username: username, password: password, verCode: verCode};
            $.ajax({
                url: '${pageContext.request.contextPath}/backend/system/user/login.do',
                type: 'POST',
                data: JSON.stringify(data),
                contentType: 'application/json; charset=utf-8',
                dataType: 'json',
                async: false,
                success: function (result) {
                    if (result.code === 0) {
                        let win = $.messager.progress({
                            title: '<fmt:message key="i18n.loginLoadingTitle"></fmt:message>',
                            msg: '<fmt:message key="i18n.loginLoadingMsg"></fmt:message>'
                        });
                        setTimeout(function () {
                            $.messager.progress('close');
                            window.location.href = '${pageContext.request.contextPath}/backend/mainPage.do';
                        }, 1000)
                    } else {
                        $('#yzm').attr('src', '${pageContext.request.contextPath}/backend/system/user/captcha.do?time' + new Date().getTime());
                        $.messager.alert('<fmt:message key="i18n.tip"></fmt:message>', result.msg, 'error')
                    }
                }
            });
        }

        /**
         * 点击登入
         */
        let isClick = true;
        $("#loginBtn").bind("click", function () {
            if (isClick) {
                isClick = false;
                login();
            }
            setTimeout(function () {
                isClick = true;
            }, 1000);//一秒内不能重复点击
        });
        return false;
    })
</script>


