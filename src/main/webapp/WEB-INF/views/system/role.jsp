<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/common/tag.jsp" %>
<%@ include file="/WEB-INF/views/common/link.jsp" %>

<%--数据表格--%>
<table id="role_table"></table>

<%--顶部工具栏--%>
<div id="role_toolbar" style="display: none">
    <%--    操作按钮--%>
    <div style="margin-bottom:5px">
        <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="false" onclick="role.showAddDialog()">添加</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="false"
           onclick="role.showEditDialog()">修改</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="false">保存</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-cut" plain="false">剪切</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" plain="false" onclick="role.delete()">移除</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="false" onclick="role.reload()">刷新</a>
    </div>
    <%--    搜索表单--%>
    <form id="role_search_form">
        <input id="deptId" type="hidden">
        <span>角色名:</span>
        <input id="roleName" style="line-height:26px;border:1px solid #ccc">
        <a href="#" class="easyui-linkbutton" plain="false" data-options="iconCls:'icon-search'"
           onclick="role.search()">查询</a>
        <a href="#" class="easyui-linkbutton" plain="false" data-options="iconCls:'icon-clear'"
           onclick="role.clear(roleSearchForm)">重置</a>
    </form>
    <hr>
</div>

<%--添加弹框--%>
<div id="role_add_dialog" class="easyui-dialog"
     style="width:500px;height:250px;top: 50px;padding: 5px;display: none"
     data-options="closed:'true',iconCls:'icon-save',resizable:true,modal:true,
         buttons:[
         {text:'重置',iconCls:'icon-clear',handler:function(){role.clear(roleAddForm)}}
         ,{text:'保存',iconCls:'icon-save',handler:function(){role.add()}}
         ,{text:'取消',iconCls:'icon-cancel',
         handler:function(){role.closeAddDialog()}}]">
    <form id="role_add_form" data-options="iframe:false" method="post">
        <table class="com_table" align="center" cellpadding="2">
            <tr>
                <td></td>
                <td><label>角色名:</label></td>
                <td><input class="easyui-textbox com_input" type="text" name="roleName"
                           data-options="required:true,prompt:'请输入角色名'"/></td>
                <td></td>
            </tr>
            <tr>
                <td></td>
                <td><label>描述信息:</label></td>
                <td><input class="easyui-textbox com_input" type="text" name="remark"
                           data-options="prompt:'请输入描述信息',height:'50px'"/></td>
                <td></td>
            </tr>
        </table>
    </form>
</div>

<%--编辑弹框--%>
<div id="role_edit_dialog" class="easyui-dialog"
     style="width:500px;height:250px;top: 50px;padding: 5px;display: none"
     data-options="closed:'true',iconCls:'icon-save',resizable:true,modal:true,buttons:[
         {text:'重置',iconCls:'icon-clear',handler:function(){role.clear(roleEditForm)}},
         {text:'更新',iconCls:'icon-ok',handler:function(){role.update()}}
         ,{text:'取消',iconCls:'icon-cancel',handler:function(){role.closeEditDialog()}}]">
    <form id="role_edit_form" data-options="iframe:false" method="post">
        <input name="id" type="hidden">
        <table class="com_table" align="center" cellpadding="2">
            <tr>
                <td></td>
                <td><label>角色名:</label></td>
                <td><input class="easyui-textbox com_input" type="text" name="roleName"
                           data-options="required:true,prompt:'请输入角色名'"/></td>
                <td></td>
            </tr>
            <tr>
                <td></td>
                <td><label>描述信息:</label></td>
                <td><input class="easyui-textbox com_input" type="text" name="remark"
                           data-options="prompt:'请输入描述信息',height:'50px'"/></td>
                <td></td>
            </tr>
        </table>
    </form>
</div>

<script src="<%=request.getContextPath()%>/resource/js/role.js"></script>
<script>
    role.init('<%=request.getContextPath()%>');
</script>


