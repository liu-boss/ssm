<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/backend/common/tag.jsp" %>
<%@ include file="/WEB-INF/views/backend/common/link.jsp" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%--数据表格--%>
<table id="online_table"></table>
<div id="sm" style="width:300px"></div>
<%--顶部工具栏--%>
<div id="online_toolbar" style="display: none">
    <%--    操作按钮--%>
    <div style="margin-bottom:5px">
        <a href="#" onclick="online.forceLogout()" class="easyui-linkbutton" iconCls="icon-cut" plain="false">踢出系统</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="false" onclick="online.reload()">刷新</a>
    </div>
    <hr>
</div>

<script src="<%=request.getContextPath()%>/resource/js/online.js"></script>
<script>
    online.init('<%=request.getContextPath()%>','<shiro:principal property="username"/>');
</script>



