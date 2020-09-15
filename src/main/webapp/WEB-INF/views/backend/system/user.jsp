<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/backend/common/tag.jsp" %>
<%@ include file="/WEB-INF/views/backend/common/link.jsp" %>

<body class="easyui-layout">
<div data-options="region:'west',title:'部门树',split:true" style="width:150px;">
    <ul id="dept_tree" data-options="lines:true"></ul>
</div>
<div data-options="region:'center',title:'管理中心'" style="padding:5px;background:#eee;">


    <%--数据表格--%>
    <table id="user_table"></table>

    <%--顶部工具栏--%>
    <div id="user_toolbar" style="display: none">
        <%--    操作按钮--%>
        <div style="margin-bottom:5px">
            <a href="#" class="easyui-linkbutton" iconCls="icon-cut" plain="false" onclick="user.openAll()">展开所有</a>
            <a href="#" class="easyui-linkbutton" iconCls="icon-lock" plain="false" onclick="user.closeAll()">折叠所有</a>
            <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="false" onclick="user.showAddDialog()">添加</a>
            <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="false"
               onclick="user.showEditDialog()">修改</a>
            <a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="false">保存</a>
            <a href="#" class="easyui-linkbutton" iconCls="icon-cut" plain="false">剪切</a>
            <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" plain="false" onclick="user.delete()">移除</a>
            <a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="false" onclick="user.reload()">刷新</a>
            <a href="#" class="easyui-linkbutton" iconCls="icon-mini-refresh" plain="false" onclick="user.resetPassword()">重置密码</a>
        </div>
        <%--    搜索表单--%>
        <form id="user_search_form">
            <input id="deptId" type="hidden">
            <span>用户名:</span>
            <input id="username" style="line-height:26px;border:1px solid #ccc">
            <span>电话:</span>
            <input id="mobile" style="line-height:26px;border:1px solid #ccc">
            <span>邮箱:</span>
            <input id="email" style="line-height:26px;border:1px solid #ccc">
            <span>性别</span>
            <select id="sex" class="easyui-combobox" data-options="editable:false" style="width:100px;">
                <option value="">全部</option>
                <option value="0">男生</option>
                <option value="1">女生</option>
                <option value="2">保密</option>
            </select>
            <span>状态:</span>
            <select id="status" class="easyui-combobox" data-options="editable:false" style="width:100px;">
                <option value="">全部</option>
                <option value="1">可用</option>
                <option value="0">禁用</option>
            </select>
            <a href="#" class="easyui-linkbutton" plain="false" data-options="iconCls:'icon-search'"
               onclick="user.search()">查询</a>
            <a href="#" class="easyui-linkbutton" plain="false" data-options="iconCls:'icon-clear'"
               onclick="user.clear(userSearchForm,true)">重置</a>
        </form>
        <hr>
    </div>

    <%--添加弹框--%>
    <div id="user_add_dialog" class="easyui-dialog"
         style="width:700px;height:400px;top: 50px;padding: 5px;display: none"
         data-options="closed:'true',iconCls:'icon-save',resizable:true,modal:true,
         buttons:[
         {text:'重置',iconCls:'icon-clear',handler:function(){user.clear(userAddForm)}}
         ,{text:'保存',iconCls:'icon-save',handler:function(){user.add()}}
         ,{text:'取消',iconCls:'icon-cancel',
         handler:function(){user.closeAddDialog()}}]">
        <form id="user_add_form" data-options="iframe:false" method="post">
            <table class="com_table" align="center" cellpadding="2">
                <tr>
                    <td></td>
                    <td><label>用户名:</label></td>
                    <td><input class="easyui-textbox com_input" type="text" name="username"
                               data-options="required:true,prompt:'请输入用户名'"/></td>
                    <td></td>

                    <td></td>
                    <td><label>邮箱:</label></td>
                    <td><input class="easyui-textbox com_input" type="text" name="email"
                               data-options="required:true,prompt:'请输入邮箱'"/></td>
                    <td></td>
                </tr>
                <tr>
                    <td></td>
                    <td><label>电话号码:</label></td>
                    <td><input class="easyui-textbox com_input" type="text" name="mobile"
                               data-options="required:true,prompt:'请输入电话号码'"/>
                    </td>
                    <td></td>

                    <td></td>
                    <td><label>密码:</label></td>
                    <td><input class="easyui-textbox com_input" type="text" name="password"
                               data-options="required:true,prompt:'请输入登入密码'"/></td>
                    <td></td>
                </tr>
                <tr>
                    <td></td>
                    <td><label>状态:</label></td>
                    <td>
                        <select class="easyui-combobox com_input" name="status"
                                data-options="panelHeight:'auto',
                                require:true,editable:false,prompt:'请选择用户状态'">
                            <option value="1">可用</option>
                            <option value="0">禁用</option>
                        </select>
                    </td>
                    <td></td>

                    <td></td>
                    <td><label>性别:</label></td>
                    <td>
                        <select class="easyui-combobox com_input" name="sex"
                                data-options="panelHeight:'auto',require:true,editable:false
                                    ,prompt:'请选择用户性别'">
                            <option value="0">男</option>
                            <option value="1">女</option>
                            <option value="2">保密</option>
                        </select>
                    </td>
                    <td></td>
                </tr>
                <tr>
                    <td></td>
                    <td><label>所属部门:</label></td>
                    <td>
                        <select id="user_add_dept_select_tree" class="easyui-combotree com_input" name="deptId" data-options="prompt:'请选择所属的部门'"></select>
                    </td>
                    <td></td>

                    <td></td>
                    <td><label>描述信息:</label></td>
                    <td><input class="easyui-textbox com_input" type="text" name="description"
                               data-options="prompt:'描述限制长度限制2~30字符',height:'50px',required:true"/></td>
                    <td></td>
                </tr>
                <tr>
                    <td></td>
                    <td><label>选择角色:</label></td>
                    <td>
                        <input id="user_add_role_select"  class="com_input" name="roleIdList">
                    </td>
                    <td></td>

                </tr>
            </table>
        </form>
    </div>

    <%--编辑弹框--%>
    <div id="user_edit_dialog" class="easyui-dialog"
         style="width:700px;height:400px;top: 50px;padding: 5px;display: none"
         data-options="closed:'true',iconCls:'icon-save',resizable:true,modal:true,buttons:[
         {text:'重置',iconCls:'icon-clear',handler:function(){user.clear(userEditForm)}},
         {text:'更新',iconCls:'icon-ok',handler:function(){user.update()}}
         ,{text:'取消',iconCls:'icon-cancel',handler:function(){user.closeEditDialog()}}]">
        <form id="user_edit_form" data-options="iframe:false" method="post">
            <input name="id" type="hidden">
            <table class="com_table" align="center" cellpadding="2">
                <tr>
                    <td></td>
                    <td><label>用户名:</label></td>
                    <td><input class="easyui-textbox com_input" type="text" name="username"
                               data-options="required:true,prompt:'请输入用户名'"/></td>
                    <td></td>

                    <td></td>
                    <td><label>邮箱:</label></td>
                    <td><input class="easyui-textbox com_input" type="text" name="email"
                               data-options="required:true,prompt:'请输入邮箱'"/></td>
                    <td></td>
                </tr>
                <tr>
                    <td></td>
                    <td><label>电话号码:</label></td>
                    <td><input class="easyui-textbox com_input" type="text" name="mobile"
                               data-options="required:true,prompt:'请输入电话号码'"/>
                    </td>
                    <td></td>
                    <td></td>
                    <td><label>状态:</label></td>
                    <td>
                        <select class="easyui-combobox com_input" name="status"
                                data-options="panelHeight:'auto',
                                require:true,editable:false,prompt:'请选择用户状态'">
                            <option value="1">可用</option>
                            <option value="0">禁用</option>
                        </select>
                    </td>
                    <td></td>

                </tr>
                <tr>

                    <td></td>
                    <td><label>性别:</label></td>
                    <td>
                        <select class="easyui-combobox com_input" name="sex"
                                data-options="panelHeight:'auto',require:true,editable:false
                                    ,prompt:'请选择用户性别'">
                            <option value="0">男</option>
                            <option value="1">女</option>
                            <option value="2">保密</option>
                        </select>
                    </td>
                    <td></td>

                    <td></td>
                    <td><label>描述信息:</label></td>
                    <td><input class="easyui-textbox com_input" type="text" name="description"
                               data-options="prompt:'描述限制长度限制2~30字符',height:'50px',required:true"/></td>
                    <td></td>
                </tr>
                <tr>
                    <td></td>
                    <td><label>所属部门:</label></td>
                    <td>
                        <select id="user_edit_dept_select_tree" class="easyui-combotree com_input" name="deptId" data-options="prompt:'请选择所属的部门'"></select>
                    </td>
                    <td></td>
                </tr>
                <tr>
                    <td></td>
                    <td><label>选择角色:</label></td>
                    <td>
                        <input id="user_edit_role_select"  class="com_input" name="roleIdList">
                    </td>
                    <td></td>

                </tr>
            </table>
        </form>
    </div>
</div>
</body>
<script src="<%=request.getContextPath()%>/resource/js/user.js"></script>
<script>
    user.init('<%=request.getContextPath()%>');
</script>

