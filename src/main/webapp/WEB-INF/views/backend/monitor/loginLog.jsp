<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/backend/common/tag.jsp" %>
<%@ include file="/WEB-INF/views/backend/common/link.jsp" %>

<%--数据表格--%>
<table id="loginLog_table"></table>
<div id="sm" style="width:300px"></div>
<%--顶部工具栏--%>
<div id="loginLog_toolbar" style="display: none">
    <%--    操作按钮--%>
    <div style="margin-bottom:5px">
        <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" plain="false" onclick="loginLog.delete()">移除</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="false" onclick="loginLog.reload()">刷新</a>
    </div>
    <hr>
</div>

<script src="<%=request.getContextPath()%>/resource/js/loginLog.js"></script>
<script>
    loginLog.init('<%=request.getContextPath()%>');
</script>



