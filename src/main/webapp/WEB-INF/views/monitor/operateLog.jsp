<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/common/tag.jsp" %>
<%@ include file="/WEB-INF/views/common/link.jsp" %>



<%--数据表格--%>
<table id="operateLog_table"></table>
<div id="sm" style="width:300px"></div>
<%--顶部工具栏--%>
<div id="operateLog_toolbar" style="display: none">
    <%--    操作按钮--%>
    <div style="margin-bottom:5px">
        <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="operateLog.delete()">移除</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="operateLog.reload()">刷新</a>
    </div>
    <hr>
        <%--    搜索表单--%>
        <form id="operate_search_form">
            <span>操作:</span>
            <input  id="operation" style="line-height:26px;border:1px solid #ccc">
            <span>方法:</span>
            <input  id="method" style="line-height:26px;border:1px solid #ccc">
            <span>地址位置:</span>
            <input  id="location" style="line-height:26px;border:1px solid #ccc">
            <span>IP地址:</span>
            <input  id="ip" style="line-height:26px;border:1px solid #ccc">
            <span>状态:</span>
            <select id="type" class="easyui-combobox" data-options="editable:false" style="width:100px;">
                <option value="">全部</option>
                <option value="1">成功</option>
                <option value="2">失败</option>
            </select>
            <a href="#" class="easyui-linkbutton" plain="false" data-options="iconCls:'icon-search'"
               onclick="operateLog.search()">查询</a>
            <a href="#" class="easyui-linkbutton" plain="false" data-options="iconCls:'icon-clear'"
               onclick="operateLog.clear(operateLogSearchForm)">重置</a>
        </form>

</div>

<script src="<%=request.getContextPath()%>/resource/js/operateLog.js"></script>
<script>
    operateLog.init('<%=request.getContextPath()%>');
</script>



