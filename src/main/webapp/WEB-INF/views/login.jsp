<%@ page import="com.coderman.util.ShiroContextHolder" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta charset="utf-8"/>
    <title>用户登录</title>
    <%@include file="/WEB-INF/views/common/link.jsp" %>
    <%@include file="/WEB-INF/views/common/tag.jsp" %>
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
            margin-bottom: 10px;
        }
    </style>
</head>
<body>

<%
    if( ShiroContextHolder.getUser()!=null){
        //如果已登入,跳转到后台主页
        response.sendRedirect(request.getContextPath()+"/system/mainPage.do");
    }
%>
<form action="#">
    <div>
        <h1>后台系统登录</h1>
    </div>
    <div>
        <input class="easyui-textbox easyui-validatebox" id="username" value="zhangyukang" name="username"
               data-options="iconCls:'icon-man',iconWidth:30,iconAlign:'left',prompt:'用户名'"
               style="width:100%;height:35px;"/>
    </div>
    <div>
        <input class="easyui-passwordbox easyui-validatebox" id="password" value="123456" name="password"
               data-options="iconWidth:30,iconAlign:'left',prompt:'密码'" style="width:100%;height:35px;"/>
    </div>

    <div style="overflow: hidden">
        <input class="easyui-textbox easyui-validatebox" id="verCode" name="verCode" value="123"
               data-options="iconCls:'icon-reload',iconWidth:30,iconAlign:'left',prompt:'验证码'"
               style="width:60%;height:35px;float: left"/>
        <img style="float: right" src="${pageContext.request.contextPath}/system/user/captcha.do" id="yzm">

        <script>
            $('#yzm').css('cursor', 'pointer').click(function () {
                $('#yzm').attr('src', '/system/user/captcha.do?time' + new Date().getTime());
            });
        </script>
    </div>
    <div>
        <input class="easyui-checkbox" type="checkbox" id="remember" label="记住密码" labelPosition="after"
               labelWidth="70"/>
    </div>
    <div>
        <input class="easyui-linkbutton" type="button" id="loginBtn" value="登陆" style="width:100%;height:35px;"/>
    </div>
    <div>
        <div style="display:inline;">
            <a href="javascript:void(0)">还未注册？</a>
        </div>
        <div style="display:inline;margin-left:170px;">
            <a href="javascript:void(0)">忘记密码？</a>
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
         * 用户登入
         */
        function login() {
            let username = $("#username").val();
            let password = $("#password").val();
            let verCode = $("#verCode").val();
            let remember = $("#remember").checkbox('options').checked;
            if (username == null || username === '') {
                $.messager.alert('Warning', '用户名不能为空');
                return;
            } else if (password == null || password === '') {
                $.messager.alert('Warning', '密码不能为空');
                return;
            } else if (verCode == null || verCode === '') {
                $.messager.alert('Warning', '验证码不能为空');
                return;
            }
            let data = {username: username, password: password, verCode: verCode};
            $.ajax({
                url: '/system/user/login.do',
                type: 'POST',
                data: JSON.stringify(data),
                contentType: 'application/json; charset=utf-8',
                dataType: 'json',
                async: false,
                success: function (result) {
                    if (result.code === 0) {
                        let win = $.messager.progress({
                            title: '登入成功,正在跳转到后台',
                            msg: '正在努力加载数据中......'
                        });
                        setTimeout(function () {
                            $.messager.progress('close');
                            window.location.href = '/system/mainPage.do';
                        }, 1000)
                    } else {
                        $('#yzm').attr('src', '/system/user/captcha.do?time' + new Date().getTime());
                        $.messager.alert('Error', result.msg, 'error')
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


