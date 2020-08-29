<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>后台管理</title>
    <%@ include file="/WEB-INF/views/common/tag.jsp"%>
    <%@ include file="/WEB-INF/views/common/link.jsp"%>
</head>
<body class="easyui-layout">
<%--上#E0ECFF--%>
<div region="north" style="height: 50px; background-color: #E0ECFF">
    <img src="/resource/image/logo.png" height="100%" style="float:left;margin-left: 10px;">
    <div style="margin: 10px 20px;float: right;overflow: hidden">
        欢迎<span style="color: red" >[<shiro:principal property="username"/>]</span>登录，
        <a style="color:blue;" href="${pageContext.request.contextPath}/user/logout.do">退出</a>
    </div>
</div>
<%--左--%>
<div region="west" style="width: 200px" title="导航菜单" split="true">
    <div id="leftMenu" class="easyui-sidemenu" data-options="data:menu,onSelect:menuSelect,multiple:true" style="width: 193px;"></div>
</div>
<%--主体--%>
<div data-options="region:'center'" style="background:#fff3f3;">
    <!--选项卡使用-->
    <div id="div_tabs" class="easyui-tabs" data-options="tools:'#tab-tools',fit:true,border:true">
        <!--首页-->
        <div id="windows" title="用户管理" style="background-color: #ffffff;background-size: cover;">
           <iframe src="${pageContext.request.contextPath}/userPage.do" style='width:100%;height:98%' frameborder='0'></iframe>
        </div>
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

    //点击左侧菜单
    function menuSelect(item){
        if(item==null || item.url==null){
            return;
        }
        //判断选项卡是否存在
        let has=$("#div_tabs").tabs("exists",item.text);
        if(has){
            //选中存在的选项卡
            $("#div_tabs").tabs("select",item.text);
        }else{
            //创建新的选项卡面板
            $("#div_tabs").tabs("add",{
                title:item.text,
                closable:true,
                content:"<iframe src="+item.url+" style='width:100%;height:98%' frameborder='0'/>"
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

    //菜单数据,可以动态从后台加载: url: 请求路径
    let menu=[{
        text: '系统管理',
        iconCls: 'icon-large-picture',
        state: 'open',
        children: [{
            url:'/userPage.do',
            text: '用户管理',
            iconCls : 'icon-large-clipart'
        }, {
            url:'/rolePage.do',
            text: '角色管理',
            iconCls : 'icon-more'
        }, {
            text: '部门管理',
            iconCls : 'icon-lock',
            url:'/deptPage.do',
        }]
    }, {
        text: '基础管理',
        iconCls: 'icon-more',
        state: 'open',
        children: [{
            text: '文档管理',
            iconCls : 'icon-large-shapes',
            url:'/admin/test.do',
        }, {
            text: '下载管理',
            iconCls : 'icon-large-picture'
        }, {
            text: 'Option6',
            iconCls : 'icon-large-picture'
        }]
    }];

</script>


