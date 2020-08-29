let deptTable;
let deptAddForm;
let deptEditForm;
let deptAddDialog;
let deptEditDialog;
let deptAddDeptSelectTree;
let deptEditDeptSelectTree;
let ctx = null;

let dept = {
    init: function (contextPath) {
        ctx = contextPath;
        deptTable = $("#dept_table"); //表格
        deptAddForm = $("#dept_add_form"); //添加表单
        deptEditForm = $("#dept_edit_form"); //编辑表单
        deptAddDialog = $("#dept_add_dialog"); //添加弹框
        deptEditDialog = $("#dept_edit_dialog"); //编辑弹框
        deptAddDeptSelectTree=$("#dept_add_dept_select_tree");//添加上级部门树
        deptEditDeptSelectTree=$("#dept_edit_dept_select_tree");//修改上级部门树
        dept.initTable(); //初始化表格
    },
    URL: {
        tree: function () {
            return ctx + '/dept/tree.do';
        },
        delete: function () {
            return ctx + '/dept/delete.do';
        },
        add: function () {
            return ctx + '/dept/add.do';
        },
        get: function (id) {
            return ctx + '/dept/get.do?id=' + id;
        },
        update: function () {
            return ctx + '/dept/update.do';
        },
    },
    //初始化表格数据
    initTable: function () {
        deptTable.treegrid({
            title: '部门信息表',
            url: dept.URL.tree(),
            idField:'id',
            pagination: true,  //是否开启分页
            treeField:'text',
            fit: true,
            collapsible: true,
            fitColumns: true,
            toolbar: '#dept_toolbar',//绑定工具栏
            rownumbers: true, //显示行列号
            showFooter: true,
            singleSelect: true, //只可以选择1行
            columns: [[
                {field: 'id', title: 'ID'},
                {field: 'text', title: '部门名', width: 100, align: 'center'},
                {field: 'orderNum', title: '排序', width: 120, align: 'center'},
                {field: 'remark', title: '备注信息', width: 120, align: 'center'},
                {field: 'createTime', title: '创建时间', width: 150, align: 'center'},
                {field: 'modifyTime', title: '修改时间', width: 150, align: 'center'},
            ]],
            onLoadSuccess: function(){  deptTable.treegrid("collapseAll");  }
        });
    },
    //添加
    add: function () {
        deptAddForm.form('submit', {
            url: dept.URL.add(),
            contentType: 'application/json',
            onSubmit: function () {
                return $(this).form('validate');
            },
            success: function (result) {
                let res = $.parseJSON(result);
                if (res.code === 0) {
                    $.messager.alert('提示', '添加成功', 'info');
                    dept.closeAddDialog();
                    dept.clear(deptAddForm);
                    dept.reload();
                } else {
                    $.messager.alert('提示信息', "添加失败:" + res.msg, 'warning');
                }
            }
        });
    },
    //删除
    delete: function () {
        //获取选中行的数据
        let selectRows = deptTable.datagrid("getSelections");
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
                $.post(dept.URL.delete() + '?id=' + strIds, function (jsonObj) {
                    if (jsonObj.code === 0) {
                        dept.reload(); //删除成功后 刷新页面
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
        let comboTree = deptEditDeptSelectTree.combotree('tree');	// get the tree object
        let n = comboTree.tree('getSelected');
        let deptId=$("#deptId").val();//当前编辑的节点id
        while (n != null) {
            if ( deptId == n.id) {
                $.messager.alert("提示","上级选择有误:(不能选择本身和子节点)",'error');
                return false;
            }
            n = comboTree.tree("getParent", n.target)
        }

        deptEditForm.form('submit', {
            url: dept.URL.update(),
            contentType: 'application/json',
            onSubmit: function () {
                return $(this).form('validate');
            },
            success: function (result) {
                let res = $.parseJSON(result);
                if (res.code === 0) {
                    $.messager.alert('提示', '更新成功', 'info');
                    dept.closeEditDialog();
                    dept.clear(deptEditForm);
                    dept.reload();
                } else {
                    $.messager.alert('提示信息', "更新失败:" + res.msg, 'error');
                }
            }
        });
    },
    //显示添加框
    showAddDialog: function () {
        deptAddDialog.dialog('open').dialog('setTitle', '添加');
        deptAddDeptSelectTree.combotree({
            url: dept.URL.tree(),
        });
    },
    //关闭添加框
    closeAddDialog: function () {
        dept.clear(deptAddDialog)
        deptAddDialog.dialog('close');
    },
    //显示编辑框
    showEditDialog: function () {
        let sels = deptTable.datagrid("getSelections");
        if (sels.length < 1) {
            $.messager.alert("对话框", "至少选择一行", 'warning');
            return;
        }
        if (sels.length > 1) {
            $.messager.alert("对话框", "只能选择一行", 'warning');
            return;
        }
        deptEditDeptSelectTree.combotree({
            url: dept.URL.tree(),
        });
        $.ajax({
            type: "GET",
            url: dept.URL.get(sels[0].id),
            success: function (data) {
                if (data.code === 0) {
                    deptEditForm.form("load", data.data);
                    deptEditDialog.dialog('open').dialog('setTitle', '编辑');
                } else {
                    alert(data.msg);
                }
            }
        });
    },
    //关闭编辑框
    closeEditDialog: function () {
        dept.clear(deptEditForm)
        deptEditDialog.dialog('close');
    },
    //重置表单
    clear: function (formElement) {
        formElement.form('clear');
    },
    //刷新
    reload: function () {
        deptTable.treegrid("reload");
    },
    //折叠
    closeAll(){
        deptTable.treegrid("collapseAll");
    },
    //展开
    openAll(){
        deptTable.treegrid("expandAll");
    }
}
