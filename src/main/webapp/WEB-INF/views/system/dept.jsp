<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/common/tag.jsp" %>
<%@ include file="/WEB-INF/views/common/link.jsp" %>

<%--数据表格--%>
<table id="dept_table"></table>

<%--顶部工具栏--%>
<div id="dept_toolbar" style="display: none">
    <%--    操作按钮--%>
    <div style="margin-bottom:5px">
        <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="false" onclick="dept.showAddDialog()">添加</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="false" onclick="dept.showEditDialog()">修改</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" plain="false" onclick="dept.delete()">移除</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="false" onclick="dept.reload()">刷新</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-cut" plain="false" onclick="dept.openAll()">展开所有</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-lock" plain="false" onclick="dept.closeAll()">折叠所有</a>
    </div>
    <hr>
</div>

<%--添加弹框--%>
<div id="dept_add_dialog" class="easyui-dialog"
     style="width:500px;height:350px;top: 50px;padding: 5px;display: none"
     data-options="closed:'true',iconCls:'icon-save',resizable:true,modal:true,
         buttons:[
         {text:'重置',iconCls:'icon-clear',handler:function(){dept.clear(deptAddForm)}}
         ,{text:'保存',iconCls:'icon-save',handler:function(){dept.add()}}
         ,{text:'取消',iconCls:'icon-cancel',
         handler:function(){dept.closeAddDialog()}}]">
    <form id="dept_add_form" data-options="iframe:false" method="post">
        <table class="com_table" align="center" cellpadding="2">
            <tr>
                <td></td>
                <td><label>部门名:</label></td>
                <td><input class="easyui-textbox com_input" type="text" name="deptName" data-options="required:true,prompt:'请输入部门名'"/></td>
                <td></td>
            </tr>
            <tr>
                <td></td>
                <td><label>上级部门:</label></td>
                <td>
                    <select id="dept_add_dept_select_tree" class="easyui-combotree com_input" name="parentId" data-options="prompt:'请选择上级的部门'"></select>
                </td>
                <td></td>
            </tr>
            <tr>
                <td></td>
                <td><label>描述信息:</label></td>
                <td><input class="easyui-textbox com_input" type="text" name="remark" data-options="prompt:'请输入描述信息',height:'50px'"/></td>
                <td></td>
            </tr>
            <tr>
                <td></td>
                <td><label>排序:</label></td>
                <td><input type="text" class="easyui-numberbox com_input" value="0" data-options="min:0,precision:0" name="orderNum"></td>
                <td></td>
            </tr>
        </table>
    </form>
</div>

<%--编辑弹框--%>
<div id="dept_edit_dialog" class="easyui-dialog"
     style="width:500px;height:350px;top: 50px;padding: 5px;display: none"
     data-options="closed:'true',iconCls:'icon-save',resizable:true,modal:true,buttons:[
         {text:'重置',iconCls:'icon-clear',handler:function(){dept.clear(deptEditForm)}},
         {text:'更新',iconCls:'icon-ok',handler:function(){dept.update()}}
         ,{text:'取消',iconCls:'icon-cancel',handler:function(){dept.closeEditDialog()}}]">
    <form id="dept_edit_form" data-options="iframe:false" method="post">
        <input name="id" id="deptId" type="hidden">
        <table class="com_table" align="center" cellpadding="2">
            <tr>
                <td></td>
                <td><label>部门名:</label></td>
                <td><input class="easyui-textbox com_input" type="text" name="deptName"
                           data-options="required:true,prompt:'请输入部门名'"/></td>
                <td></td>
            </tr>
            <tr>
                <td></td>
                <td><label>上级部门:</label></td>
                <td>
                    <select id="dept_edit_dept_select_tree" class="easyui-combotree com_input" name="parentId"
                            data-options="prompt:'请选择上级的部门'"></select>
                </td>
                <td></td>
            </tr>
            <tr>
                <td></td>
                <td><label>描述信息:</label></td>
                <td><input class="easyui-textbox com_input" type="text" name="remark"
                           data-options="prompt:'请输入描述信息',height:'50px'"/></td>
                <td></td>
            </tr>
            <tr>
                <td></td>
                <td><label>排序:</label></td>
                <td><input type="text" class="easyui-numberbox com_input" value="0" data-options="min:0,precision:0" name="orderNum"></td>
                <td></td>
            </tr>
        </table>
    </form>
</div>

<script src="<%=request.getContextPath()%>/resource/js/dept.js"></script>
<script>
    dept.init('<%=request.getContextPath()%>');
</script>

