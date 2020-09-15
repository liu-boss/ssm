let roleTable;
let roleSearchForm;
let roleAddForm;
let roleEditForm;
let roleAddDialog;
let roleEditDialog;
let roleAddPermissionSelectTree;
let roleEditPermissionSelectTree;
let ctx = null;

let role = {
    init: function (contextPath) {
        ctx = contextPath;
        roleTable = $("#role_table"); //表格
        roleAddForm = $("#role_add_form"); //添加表单
        roleEditForm = $("#role_edit_form"); //编辑表单
        roleAddDialog = $("#role_add_dialog"); //添加弹框
        roleEditDialog = $("#role_edit_dialog"); //编辑弹框
        roleSearchForm = $("#role_search_form");//模糊查询
        roleAddPermissionSelectTree = $("#role_add_permission_select_tree");//添加角色的(权限)
        roleEditPermissionSelectTree = $("#role_edit_permission_select_tree");//编辑角色的(权限)
        role.initTable(); //初始化表格
    },
    URL: {
        list: function () {
            return ctx + '/backend/system/role/list.do';
        },
        delete: function () {
            return ctx + '/backend/system/role/delete.do';
        },
        add: function () {
            return ctx + '/backend/system/role/add.do';
        },
        get: function (id) {
            return ctx + '/backend/system/role/get.do?id=' + id;
        },
        update: function () {
            return ctx + '/backend/system/role/update.do';
        },
        menuTree: function () {
            return ctx + '/backend/system/menu/tree.do';
        },
        queryMenu: function (id) {
            return ctx+'/backend/system/role/queryMenu.do?id='+id;
        }
    },
    //初始化表格数据
    initTable: function () {
        roleTable.datagrid({
            title: '角色信息表',
            url: role.URL.list(),
            autoSave: true,//自动保存
            fit: true,
            collapsible: true,
            fitColumns: true,
            toolbar: '#role_toolbar',//绑定工具栏
            pagination: true,  //是否开启分页
            pageList: [10, 15, 20, 30],
            pageSize: 10,
            rownumbers: true, //显示行列号
            showFooter: true,
            singleSelect: false, //只可以选择1行
            columns: [[
                {field: 'id', title: 'ID', checkbox: true},
                {field: 'roleName', title: '角色名', width: 120, align: 'center'},
                {field: 'remark', title: '描述信息', width: 220, align: 'center'},
                {field: 'createTime', title: '创建时间', width: 150, align: 'center'},
                {field: 'modifyTime', title: '修改时间', width: 150, align: 'center'},
            ]]
        });
    },
    //查询
    search: function () {
        roleTable.datagrid('load', {
            roleName: roleSearchForm.find("#roleName").val(),
        });
    },
    //添加
    add: function () {
        roleAddForm.form('submit', {
            url: role.URL.add(),
            contentType: 'application/json',
            onSubmit: function () {
                return $(this).form('validate');
            },
            success: function (result) {
                let res = $.parseJSON(result);
                if (res.code === 0) {
                    $.messager.alert('提示', '添加成功', 'info');
                    role.closeAddDialog();
                    role.clear(roleAddForm);
                    role.reload();
                } else {
                    $.messager.alert('提示信息', "添加失败:" + res.msg, 'warning');
                }
            }
        });
    },
    //删除
    delete: function () {
        //获取选中行的数据
        let selectRows = roleTable.datagrid("getSelections");
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
                $.post(role.URL.delete() + '?id=' + strIds, function (jsonObj) {
                    if (jsonObj.code === 0) {
                        roleTable.datagrid("reload"); //删除成功后 刷新页面
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
        roleEditForm.form('submit', {
            url: role.URL.update(),
            contentType: 'application/json',
            onSubmit: function () {
                return $(this).form('validate');
            },
            success: function (result) {
                let res = $.parseJSON(result);
                if (res.code === 0) {
                    $.messager.alert('提示', '更新成功', 'info');
                    role.closeEditDialog();
                    role.clear(roleEditForm);
                    role.reload();
                } else {
                    $.messager.alert('提示信息', "更新失败:" + res.msg, 'error');
                }
            }
        });
    },
    //显示添加框
    showAddDialog: function () {
        roleAddDialog.dialog('open').dialog('setTitle', '添加');

        roleAddPermissionSelectTree.combotree({
            multiple: true,
            url: role.URL.menuTree(),
        });
    },
    //关闭添加框
    closeAddDialog: function () {
        role.clear(roleAddDialog)
        roleAddDialog.dialog('close');
    },
    //显示编辑框
    showEditDialog: function () {
        let sels = roleTable.datagrid("getSelections");
        if (sels.length < 1) {
            $.messager.alert("对话框", "至少选择一行", 'warning');
            return;
        }
        if (sels.length > 1) {
            $.messager.alert("对话框", "只能选择一行", 'warning');
            return;
        }
        //加载角色已有权限
        $.ajax({
            type: "GET",
            url: role.URL.queryMenu(sels[0].id),
            success: function (res) {
                if (res.code === 0) {
                    roleEditPermissionSelectTree.combotree({
                        multiple: true,
                        lines:true,
                        cascadeCheck:false,//不级联检查
                        url: role.URL.menuTree(),
                        //回显权限
                        onLoadSuccess:function () {
                            roleEditPermissionSelectTree.combotree('setValues', res.data);
                        }
                    });
                } else {
                    alert(data.msg);
                }
            }
        });

        $.ajax({
            type: "GET",
            url: role.URL.get(sels[0].id),
            success: function (data) {
                if (data.code === 0) {
                    roleEditForm.form("load", data.data);
                    roleEditDialog.dialog('open').dialog('setTitle', '编辑');
                } else {
                    alert(data.msg);
                }
            }
        });
    },
    //关闭编辑框
    closeEditDialog: function () {
        role.clear(roleEditForm)
        roleEditDialog.dialog('close');
    },
    //重置表单
    clear: function (formElement) {
        formElement.form('clear');
    },
    //刷新
    reload: function () {
        roleTable.datagrid("reload");
    },
}
