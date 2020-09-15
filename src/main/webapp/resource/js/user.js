let userTable;
let userSearchForm;
let userAddForm;
let userEditForm;
let userAddDialog;
let userEditDialog;
let userAddDeptSelectTree;
let userEditDeptSelectTree;
let userAddRoleSelect;
let userEditRoleSelect;
let treeElement;
let ctx = null;

let user = {
    init: function (contextPath) {
        ctx = contextPath;
        userTable = $("#user_table"); //表格
        userAddForm = $("#user_add_form"); //添加表单
        userEditForm = $("#user_edit_form"); //编辑表单
        userAddDialog = $("#user_add_dialog"); //添加弹框
        userEditDialog = $("#user_edit_dialog"); //编辑弹框
        userSearchForm = $("#user_search_form");//模糊查询
        userAddDeptSelectTree = $("#user_add_dept_select_tree");//下拉树(添加)
        userEditDeptSelectTree = $("#user_edit_dept_select_tree");//下拉树(添加)
        userAddRoleSelect = $("#user_add_role_select");//添加角色组件
        userEditRoleSelect = $("#user_edit_role_select");//编辑角色组件
        treeElement = $("#dept_tree");
        user.initDeptTree();//初始化部门树
        user.initTable(); //初始化表格
    },
    URL: {
        list: function () {
            return ctx + '/backend/system/user/list.do';
        },
        delete: function (id) {
            return ctx + '/backend/system/user/delete.do?id='+id;
        },
        add: function () {
            return ctx + '/backend/system/user/add.do';
        },
        get: function (id) {
            return ctx + '/backend/system/user/get.do?id=' + id;
        },
        update: function () {
            return ctx + '/backend/system/user/update.do';
        },
        resetPassword:function (id) {
            return ctx+'/backend/system/user/resetPassword.do?id='+id;
        },
        queryRole: function (id) {
            return ctx + '/backend/system/user/queryRole.do?id=' + id;
        },
        deptTree: function () {
            return ctx + '/backend/system/dept/tree.do';
        },
        roleAll: function () {
            return ctx + '/backend/system/role/listAll.do';
        },
    },
    //初始化表格数据
    initTable: function () {
        userTable.datagrid({
            title: '用户信息表',
            url: user.URL.list(),
            autoSave: true,//自动保存
            fit: true,
            collapsible: true,
            fitColumns: true,
            toolbar: '#user_toolbar',//绑定工具栏
            pagination: true,  //是否开启分页
            pageList: [12,15, 20, 30],
            pageSize: 12,
            rownumbers: true, //显示行列号
            showFooter: true,
            singleSelect: false, //只可以选择1行
            columns: [[
                {field: 'id', title: 'ID', checkbox: true},
                {field: 'username', title: '用户名', width: 120, align: 'center'},
                {field: 'mobile', title: '电话号码', width: 130, align: 'center'},
                {field: 'email', title: '邮箱', width: 150, align: 'center'},
                {
                    field: 'status', title: '状态', width: 120, align: 'center',
                    formatter: function (value, row, index) {
                        return row.status === '1' ? '<font color="green">可用</font>' : '<font color="red">禁用</font>'
                    }
                },
                {
                    field: 'sex', title: '性别', width: 150, align: 'center',
                    formatter: function (value, row, index) {
                        if (row.sex === '2') {
                            return '<font color="#e86dff">保密</font>';
                        }
                        return row.sex == 0 ? '<font color="blue">男</font>' : '<font color="#ff1493">女</font>'
                    }
                },
                {field: 'description', title: '描述信息', width: 220, align: 'center'},
                {field: 'createTime', title: '创建时间', width: 150, align: 'center'},
                // {field: 'modifyTime', title: '修改时间', width: 150, align: 'center'},
                {field: 'lastLoginTime', title: '最近登入时间', width: 150, align: 'center'}
            ]]
        });
    },
    //加载部门树
    initDeptTree: function () {
        treeElement.tree({
            url: user.URL.deptTree(),
            onLoadSuccess: function () {
                user.openAll();
            },
            //点击节点
            onClick: function (node) {
                $("#deptId").val(node.id);
                user.search();
            },
        });
    },
    //查询
    search: function () {
        userTable.datagrid('load', {
            username: userSearchForm.find("#username").val(),
            email: userSearchForm.find("#email").val(),
            mobile: userSearchForm.find("#mobile").val(),
            sex: userSearchForm.find("#sex").val(),
            status: userSearchForm.find("#status").val(),
            deptId: userSearchForm.find("#deptId").val(),
        });
    },
    //添加
    add: function () {
        userAddForm.form('submit', {
            url: user.URL.add(),
            contentType: 'application/json',
            onSubmit: function () {
                return $(this).form('validate');
            },
            success: function (result) {
                let res = $.parseJSON(result);
                if (res.code === 0) {
                    $.messager.alert('提示', '添加成功', 'info');
                    user.closeAddDialog();
                    user.clear(userAddForm, false);
                    user.reload();
                } else {
                    $.messager.alert('提示信息', "添加失败:" + res.msg, 'warning');
                }
            }
        });
    },
    //删除
    delete: function () {
        //获取选中行的数据
        let selectRows = userTable.datagrid("getSelections");
        //如果没有选中行的话，提示信息
        if (selectRows.length < 1) {
            $.messager.alert("提示消息", "请选择要删除的记录！", 'warning');
            return;
        }
        //如果选中行了，则要进行判断
        $.messager.confirm("确认消息", "确定要删除所选记录吗？", function (isDelete) {
            //如果为真的话
            if (isDelete) {
                //定义变量值
                let strIds = "";
                //拼接字符串，这里也可以使用数组，作用一样
                for (let i = 0; i < selectRows.length; i++) {
                    strIds += selectRows[i].id + ",";
                }
                //循环切割
                strIds = strIds.substr(0, strIds.length - 1);
                $.post(user.URL.delete(strIds), function (jsonObj) {
                    if (jsonObj.code === 0) {
                        userTable.datagrid("reload"); //删除成功后 刷新页面
                        $.messager.alert('提示', '删除成功！', 'info');
                    } else {
                        $.messager.alert('提示信息', '删除失败:' + jsonObj.msg, 'warning');
                    }
                }, "JSON");
            }
        });
    },
    //更新
    update: function () {
        userEditForm.form('submit', {
            url: user.URL.update(),
            contentType: 'application/json',
            onSubmit: function () {
                return $(this).form('validate');
            },
            success: function (result) {
                let res = $.parseJSON(result);
                if (res.code === 0) {
                    $.messager.alert('提示', '更新成功', 'info');
                    user.closeEditDialog();
                    user.clear(userEditForm, false);
                    user.reload();
                } else {
                    $.messager.alert('提示信息', "更新失败:" + res.msg, 'error');
                }
            }
        });
    },
    //显示添加框
    showAddDialog: function () {
        userAddDialog.dialog('open').dialog('setTitle', '添加');
        userAddDeptSelectTree.combotree({
            url: user.URL.deptTree(),
        });
        userAddRoleSelect.combogrid({
            url: user.URL.roleAll(),
            delay: 500,
            panelWidth: 450,
            mode: 'remote',
            editable: false,
            idField: 'id',
            textField: 'roleName',
            multiple: true,
            columns: [[
                {field: 'id', title: 'Id', width: 60, checkbox: true},
                {field: 'roleName', title: '角色名称', width: 100},
                {field: 'remark', title: '角色描述', width: 150},
                {field: 'createTime', title: '创建时间', width: 180}
            ]]
        });
    },
    //关闭添加框
    closeAddDialog: function () {
        user.clear(userAddDialog, false)
        userAddDialog.dialog('close');
    },
    //显示编辑框
    showEditDialog: function () {
        let sels = userTable.datagrid("getSelections");
        if (sels.length < 1) {
            $.messager.alert("对话框", "至少选择一行", 'warning');
            return;
        }
        if (sels.length > 1) {
            $.messager.alert("对话框", "只能选择一行", 'warning');
            return;
        }
        userEditDeptSelectTree.combotree({
            url: user.URL.deptTree(),
        });
        //加载用户已有的角色
        $.ajax({
            type: "GET",
            url: user.URL.queryRole(sels[0].id),
            success: function (res) {
                if (res.code === 0) {
                    let roles = "";
                    if (res.data.length > 0) {
                        roles = res.data.join(",");
                    }
                    userEditRoleSelect.combogrid({
                        url: user.URL.roleAll(),
                        delay: 500,
                        value: roles,
                        panelWidth: 450,
                        mode: 'remote',
                        editable: false,
                        idField: 'id',
                        textField: 'roleName',
                        multiple: true,
                        columns: [[
                            {field: 'id', title: 'Id', width: 60, checkbox: true},
                            {field: 'roleName', title: '角色名称', width: 100},
                            {field: 'remark', title: '角色描述', width: 150},
                            {field: 'createTime', title: '创建时间', width: 180}
                        ]]
                    });
                } else {
                    alert(data.msg);
                }
            }
        });

        $.ajax({
            type: "GET",
            url: user.URL.get(sels[0].id),
            success: function (data) {
                if (data.code === 0) {
                    userEditForm.form("load", data.data);
                    userEditDialog.dialog('open').dialog('setTitle', '编辑');
                } else {
                    alert(data.msg);
                }
            }
        });
    },
    //关闭编辑框
    closeEditDialog: function () {
        user.clear(userEditForm, false)
        userEditDialog.dialog('close');
    },
    //重置表单
    clear: function (formElement, loadTree) {
        formElement.form('clear');
        if (loadTree) {
            user.initDeptTree();
        }
    },
    //刷新
    reload: function () {
        userTable.datagrid("reload");
    },
    //折叠
    closeAll(){
        treeElement.tree("collapseAll");
    },
    //展开
    openAll(){
        treeElement.tree("expandAll");
    },
    //重置密码
    resetPassword(){
        let sels = userTable.datagrid("getSelections");
        if (sels.length < 1) {
            $.messager.alert("对话框", "至少选择一行", 'warning');
            return;
        }
        if (sels.length > 1) {
            $.messager.alert("对话框", "只能选择一行", 'warning');
            return;
        }
        $.messager.confirm("确认消息", "确定重置密码吗？(默认:123456)", function (isReset) {
            //如果为真的话
            if (isReset) {
                $.get(user.URL.resetPassword(sels[0].id), function (jsonObj) {
                    if (jsonObj.code === 0) {
                        $.messager.alert('提示', '重置密码成功！', 'info');
                    } else {
                        $.messager.alert('提示信息', '重置密码失败:' + jsonObj.msg, 'warning');
                    }
                }, "JSON");
            }
        });

    }
}
