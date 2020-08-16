let userTable;
let userSearchForm;
let userAddForm;
let userEditForm;
let userAddDialog;
let userEditDialog;
let ctx=null;

let user = {
    init: function (contextPath) {
        ctx=contextPath;
        userTable = $("#user_table"); //表格
        userAddForm = $("#user_add_form"); //添加表单
        userEditForm = $("#user_edit_form"); //编辑表单
        userAddDialog = $("#user_add_dialog"); //添加弹框
        userEditDialog = $("#user_edit_dialog"); //编辑弹框
        userSearchForm = $("#user_search_form");//模糊查询
        user.initTable(); //初始化表格
    },
    URL: {
        list: function () {
            return ctx+'/user/list.do';
        },
        delete: function () {
            return ctx+'/user/delete.do';
        },
        add: function () {
            return ctx+'/user/add.do';
        },
        get: function (id) {
            return ctx+'/user/get.do?id=' + id;
        },
        update: function () {
            return ctx+'/user/update.do';
        }
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
            pageList: [5, 12, 15, 20],
            pageSize: 12,
            rownumbers: true, //显示行列号
            showFooter: true,
            singleSelect: false, //只可以选择1行
            columns: [[
                {field: 'id', title: 'ID', width: 100, checkbox: true},
                {field: 'phone', title: '电话号码', width: 250, align: 'center'},
                {field: 'role', title: '角色', width: 200, align: 'center'},
                {field: 'password', title: '密码', width: 200, align: 'center'},
                {field: 'username', title: '用户名', width: 200, align: 'center'},
                {
                    field: 'sex', title: '性别', width: 200, align: 'center',
                    formatter: function (value, row, index) {
                        return row.sex === 1 ? '男+'+ctx : '女'
                    }
                }
            ]]
        });
    },
    //查询
    search: function () {
        userTable.datagrid('load', {
            username: $('#user_search_form #username').val(),
            role: $('#user_search_form #role').val(),
            phone: $('#user_search_form #phone').val(),
            sex: $('#user_search_form #sex').val(),
            startTime: $('#user_search_form #startTime').val(),
            endTime: $('#user_search_form #endTime').val()
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
                console.log(result)
                if (result) {
                    $.messager.alert('提示', '添加成功');
                    user.closeAddDialog();
                    user.clear(userAddForm);
                    user.reload();
                } else {
                    $.messager.alert('提示信息', "添加失败", 'warning');
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
                $.post(user.URL.delete() + '?id=' + strIds, function (jsonObj) {
                    if (jsonObj.code === 0) {
                        userTable.datagrid("reload"); //删除成功后 刷新页面
                        $.messager.alert('提示', '删除成功！', 'info');
                    } else {
                        $.messager.alert('提示信息', '删除失败，请联系管理员！', 'warning');
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
                if (result) {
                    $.messager.alert('提示', '更新成功', 'info');
                    user.closeEditDialog();
                    user.clear(userEditForm);
                    user.reload();
                } else {
                    $.messager.alert('提示信息', "更新失败", 'error');
                }
            }
        });
    },
    //显示添加框
    showAddDialog: function () {
        userAddDialog.dialog('open').dialog('setTitle', '添加');
    },
    //关闭添加框
    closeAddDialog: function () {
        user.clear(userAddDialog)
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
        $.ajax({
            type: "GET",
            url: user.URL.get(sels[0].id),
            success: function (data) {
                if (data.code === 0) {
                    userEditForm.form("load", data.data);
                    userEditDialog.dialog('open');
                } else {
                    alert(data.msg);
                }
            }
        });
    },
    //关闭编辑框
    closeEditDialog: function () {
        user.clear(userEditForm)
        userEditDialog.dialog('close');
    },
    //重置表单
    clear: function (formElement) {
        formElement.form('clear');
    },
    //刷新
    reload: function () {
        userTable.datagrid("reload");
    },
}
