<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>后台管理</title>
    <%@ include file="/WEB-INF/views/backend/common/tag.jsp" %>
    <%@ include file="/WEB-INF/views/backend/common/link.jsp" %>
</head>
<style>
    html,body{
        position: relative;
        overflow-y: hidden;
        height: 100%;
        width: 100%;
        margin: 0px;
        padding: 0px;
    }
    .tree-icon{
        margin-right: 4px;
    }
</style>

<body class="easyui-layout">
<%--上: E0ECFF--%>
<div region="north" style="height: 50px; background-color: #E0ECFF">
    <img src="${pageContext.request.contextPath}/resource/easyui/image/logo.png" height="100%" style="float:left;margin-left: 10px;">
    <div style="margin: 15px 20px;float: right;overflow: hidden">
        <fmt:message key="i18n.welcome"></fmt:message> &nbsp;[<span style="color: red" id="username"><shiro:principal property="username"/></span>]，
        <a style="color:blue;" href="${pageContext.request.contextPath}/backend/system/user/logout.do"><fmt:message key="i18n.logout"></fmt:message></a>
    </div>
</div>
<%--左--%>
<div region="west" style="width: 200px" title="导航菜单" split="true">
    <div id="leftMenu" style="width: 193px;" data-options="onSelect:menuSelect,multiple:true"></div>
</div>
<%--主体--%>
<div data-options="region:'center'" style="background:#ffffff;">
    <!--选项卡使用-->
    <div id="div_tabs" class="easyui-tabs" data-options="tools:'#tab-tools',fit:true,border:true">
        <!--首页-->
<%--        <div id="windows" title="用户管理" style="background-color: #ffffff;background-size: cover;">--%>
<%--            <iframe src="${pageContext.request.contextPath}/userPage.do" style='width:100%;height:98%' frameborder='0'></iframe>--%>
<%--        </div>--%>
    </div>
    <div id="tab-tools">
        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'"
           onclick="addPanel()"></a>
        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'"
           onclick="removePanel()"></a>
    </div>
</div>
<%--下--%>
<div region="south" style="height: 25px;padding: 5px" align="center"></div>
</body>
</html>
<script type="text/javascript">
    $(function () {
        //加载后台菜单
        $.ajax({
            url: '${pageContext.request.contextPath}/backend/system/menu/getUserMenus.do?username='+$("#username").text(),
            type: 'POST',
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            async: false,
            success: function (result) {
                if (result.code === 0) {
                    $('#leftMenu').sidemenu({
                        data: result.data,
                    });
                } else {
                    $.messager.alert('Error', result.msg, 'error')
                }
            }
        });
    })
    //点击左侧菜单
    function menuSelect(item) {
        if (item == null || item.url == null) {
            return;
        }
        //判断选项卡是否存在
        let has = $("#div_tabs").tabs("exists", item.text);
        if (has) {
            //选中存在的选项卡
            $("#div_tabs").tabs("select", item.text);
        } else {
            //创建新的选项卡面板
            $("#div_tabs").tabs("add", {
                title: item.text,
                closable: true,
                content: "<iframe src="${pageContext.request.contextPath} + item.url + " style='width:100%;height:98%' frameborder='0'/>"
            })
        }
    }

    //table标签页add+remove
    let index = 0;

    function addPanel() {
        index++;
        $('#div_tabs').tabs('add', {
            title: 'Tab' + index,
            content: '<div style="padding:10px">Content' + index + '</div>',
            closable: true
        });
    }

    function removePanel() {
        let tab = $('#div_tabs').tabs('getSelected');
        if (tab) {
            let index = $('#div_tabs').tabs('getTabIndex', tab);
            $('#div_tabs').tabs('close', index);
        }
    }

</script>


