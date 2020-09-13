<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/common/tag.jsp" %>
<%@ include file="/WEB-INF/views/common/link.jsp" %>

<%--数据表格--%>
<table id="menu_table"></table>

<%--顶部工具栏--%>
<div id="menu_toolbar" style="display: none">
    <%--    操作按钮--%>
    <div style="margin-bottom:5px">
        <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="false" onclick="menu.showAddDialog()">添加</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="false" onclick="menu.showEditDialog()">修改</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" plain="false" onclick="menu.delete()">移除</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="false" onclick="menu.reload()">刷新</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-cut" plain="false" onclick="menu.openAll()">展开所有</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-lock" plain="false" onclick="menu.closeAll()">折叠所有</a>
    </div>
    <hr>
</div>

<%--添加弹框--%>
<div id="menu_add_dialog" class="easyui-dialog"
     style="width:500px;height:500px;top: 50px;padding: 5px;display: none"
     data-options="closed:'true',iconCls:'icon-save',resizable:true,modal:true,
         buttons:[
         {text:'重置',iconCls:'icon-clear',handler:function(){menu.clear(menuAddForm)}}
         ,{text:'保存',iconCls:'icon-save',handler:function(){menu.add()}}
         ,{text:'取消',iconCls:'icon-cancel',
         handler:function(){menu.closeAddDialog()}}]">
    <form id="menu_add_form" data-options="iframe:false" method="post">
        <table class="com_table" align="center" cellpadding="2">
            <tr>
                <td></td>
                <td><label>名称:</label></td>
                <td><input class="easyui-textbox com_input" type="text" name="menuName" data-options="required:true,prompt:'请输入菜单名'"/></td>
                <td></td>
            </tr>

            <tr>
                <td></td>
                <td><label>类型:</label></td>
                <td>
                    <select  id="menu_add_type" class="easyui-combobox com_input" name="type" data-options="onSelect:function(type){menu.changeAddMenuType(type)},panelHeight:'auto',require:true,editable:false,prompt:'请选择类型'">
                        <option value="0">菜单</option>
                        <option value="1">按钮</option>
                    </select>
                </td>
                <td></td>
            </tr>

            <tr id="menu_add_icon_tr">
                <td></td>
                <td><label>图标:</label></td>
                <td><input class="easyui-textbox com_input"  type="text" name="icon" id="add_icons"  data-options="prompt:'请输入icon图标'"/></td>
                <td></td>
            </tr>
            <tr>
                <td></td>
                <td><label>上级:</label></td>
                <td>
                    <select id="menu_add_menu_select_tree" class="easyui-combotree com_input" name="parentId" data-options="prompt:'请选择上级的菜单'"></select>
                </td>
                <td></td>
            </tr>

            <tr>
                <td></td>
                <td><label>权限:</label></td>
                <td><input class="easyui-textbox com_input" type="text" name="perms" data-options="prompt:'请输入权限标识'"/></td>
                <td></td>
            </tr>
            <tr id="menu_add_url_tr">
                <td></td>
                <td><label>url:</label></td>
                <td><input class="easyui-textbox com_input" type="text" name="url" data-options="prompt:'请输入url地址'"/></td>
                <td></td>
            </tr>

            <tr id="menu_add_orderNum_tr">
                <td></td>
                <td><label>排序:</label></td>
                <td><input type="text" class="easyui-numberbox com_input" value="0" data-options="min:0,precision:0" name="orderNum"></td>
                <td></td>
            </tr>
        </table>
    </form>
</div>

<%--编辑弹框--%>
<div id="menu_edit_dialog" class="easyui-dialog"
     style="width:500px;height:500px;top: 50px;padding: 5px;display: none"
     data-options="closed:'true',iconCls:'icon-save',resizable:true,modal:true,buttons:[
         {text:'重置',iconCls:'icon-clear',handler:function(){menu.clear(menuEditForm)}},
         {text:'更新',iconCls:'icon-ok',handler:function(){menu.update()}}
         ,{text:'取消',iconCls:'icon-cancel',handler:function(){menu.closeEditDialog()}}]">
    <form id="menu_edit_form" data-options="iframe:false" method="post">
        <input name="id" id="menuId" type="hidden">
        <table class="com_table" align="center" cellpadding="2">
            <tr>
                <td></td>
                <td><label>名称:</label></td>
                <td><input class="easyui-textbox com_input" type="text" name="menuName" data-options="required:true,prompt:'请输入菜单名'"/></td>
                <td></td>
            </tr>

            <tr>
                <td></td>
                <td><label>类型:</label></td>
                <td>
                    <select  id="menu_edit_type" class="easyui-combobox com_input" name="type" data-options="onSelect:function(type){menu.changeEditMenuType(type)},panelHeight:'auto',require:true,editable:false,prompt:'请选择类型'">
                        <option value="0">菜单</option>
                        <option value="1">按钮</option>
                    </select>
                </td>
                <td></td>
            </tr>

            <tr id="menu_edit_icon_tr">
                <td></td>
                <td><label>icon图标:</label></td>
                <td><input class="easyui-textbox com_input" type="text" name="icon" id="edit_icons"  data-options="prompt:'请输入icon图标'"/></td>
                <td></td>
            </tr>

            <tr>
                <td></td>
                <td><label>上级:</label></td>
                <td>
                    <select id="menu_edit_menu_select_tree" class="easyui-combotree com_input" name="parentId" data-options="prompt:'请选择上级的菜单'"></select>
                </td>
                <td></td>
            </tr>

            <tr>
                <td></td>
                <td><label>权限:</label></td>
                <td><input class="easyui-textbox com_input" type="text" name="perms" data-options="prompt:'请输入权限标识'"/></td>
                <td></td>
            </tr>
            <tr id="menu_edit_url_tr">
                <td></td>
                <td><label>url:</label></td>
                <td><input class="easyui-textbox com_input" type="text" name="url" data-options="prompt:'请输入url地址'"/></td>
                <td></td>
            </tr>

            <tr id="menu_edit_orderNum_tr">
                <td></td>
                <td><label>排序:</label></td>
                <td><input type="text" class="easyui-numberbox com_input" value="0" data-options="min:0,precision:0" name="orderNum"></td>
                <td></td>
            </tr>
        </table>
    </form>
</div>

<script src="<%=request.getContextPath()%>/resource/js/menu.js"></script>
<script>
    menu.init('<%=request.getContextPath()%>');

</script>

<style>
    .item-img,.item-text{
        cursor: pointer;
    }
</style>

