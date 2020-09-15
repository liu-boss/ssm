let menuTable;
let menuAddForm;
let menuEditForm;
let menuAddDialog;
let menuEditDialog;
let menuAddDeptSelectTree;
let menuEditDeptSelectTree;
let ctx = null;

let menu = {
    init: function (contextPath) {
        ctx = contextPath;
        menuTable = $("#menu_table"); //表格
        menuAddForm = $("#menu_add_form"); //添加表单
        menuEditForm = $("#menu_edit_form"); //编辑表单
        menuAddDialog = $("#menu_add_dialog"); //添加弹框
        menuEditDialog = $("#menu_edit_dialog"); //编辑弹框
        menuAddDeptSelectTree=$("#menu_add_menu_select_tree");//添加上级菜单树
        menuEditDeptSelectTree=$("#menu_edit_menu_select_tree");//修改上级菜单树
        menu.initTable(); //初始化表格
    },
    URL: {
        tree: function () {
            return ctx + '/backend/system/menu/tree.do';
        },
        delete: function () {
            return ctx + '/backend/system/menu/delete.do';
        },
        add: function () {
            return ctx + '/backend/system/menu/add.do';
        },
        get: function (id) {
            return ctx + '/backend/system/menu/get.do?id=' + id;
        },
        update: function () {
            return ctx + '/backend/system/menu/update.do';
        },
    },
    //初始化表格数据
    initTable: function () {
        menuTable.treegrid({
            title: '菜单信息表',
            url: menu.URL.tree(),
            idField:'id',
            pagination: true,  //是否开启分页
            treeField:'text',
            fit: true,
            collapsible: true,
            fitColumns: true,
            toolbar: '#menu_toolbar',//绑定工具栏
            rownumbers: true, //显示行列号
            showFooter: true,
            singleSelect: true, //只可以选择1行
            columns: [[
                {field: 'id', title: 'ID'},
                {field: 'text', title: '菜单名', width: 100, align: 'center'},
                {field: 'type', title: '类型', width: 100, align: 'center',
                    formatter: function (value, row, index) {
                        if(row.type==='0'){
                            if(row.url!=null&&row.url!==""){
                                return  '<a style="color: blue" href="#" >菜单链接</a>';
                            }
                            return  '<span color="green" class="tree-icon tree-folder tree-folder-close"></span> <span>目录</span>';
                        }else if(row.type==='1'){
                            return '<span style="color: #ec2867" >按钮</span>';
                        }
                    }},
                {field: 'orderNum', title: '排序', width: 120, align: 'center'},
                {field: 'perms', title: '权限编码', width: 100},
                {field: 'url', title: '地址', width: 120},
                {field: 'createTime', title: '创建时间', width: 150, align: 'center'},
                {field: 'modifyTime', title: '修改时间', width: 150, align: 'center'},
            ]],
            onLoadSuccess: function(){  menu.closeAll()  }
        });
    },
    //添加
    add: function () {
        menuAddForm.form('submit', {
            url: menu.URL.add(),
            contentType: 'application/json',
            onSubmit: function () {
                return $(this).form('validate');
            },
            success: function (result) {
                let res = $.parseJSON(result);
                if (res.code === 0) {
                    $.messager.alert('提示', '添加成功', 'info');
                    menu.closeAddDialog();
                    menu.clear(menuAddForm);
                    menu.reload();
                } else {
                    $.messager.alert('提示信息', "添加失败:" + res.msg, 'warning');
                }
            }
        });
    },
    //删除
    delete: function () {
        //获取选中行的数据
        let selectRows = menuTable.datagrid("getSelections");
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
                $.post(menu.URL.delete() + '?id=' + strIds, function (jsonObj) {
                    if (jsonObj.code === 0) {
                        menu.reload(); //删除成功后 刷新页面
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
        let comboTree = menuEditDeptSelectTree.combotree('tree');	// get the tree object
        let n = comboTree.tree('getSelected');
        let menuId=$("#menuId").val();//当前编辑的节点id
        while (n != null) {
            if ( menuId == n.id) {
                $.messager.alert("提示","上级选择有误:(不能选择本身和子节点)",'error');
                return false;
            }
            n = comboTree.tree("getParent", n.target)
        }
        menuEditForm.form('submit', {
            url: menu.URL.update(),
            contentType: 'application/json',
            onSubmit: function () {
                return $(this).form('validate');
            },
            success: function (result) {
                let res = $.parseJSON(result);
                if (res.code === 0) {
                    $.messager.alert('提示', '更新成功', 'info');
                    menu.closeEditDialog();
                    menu.clear(menuEditForm);
                    menu.reload();
                } else {
                    $.messager.alert('提示信息', "更新失败:" + res.msg, 'error');
                }
            }
        });
    },
    //显示添加框
    showAddDialog: function () {
        menuAddDialog.dialog('open').dialog('setTitle', '添加');
        menuAddDeptSelectTree.combotree({
            url: menu.URL.tree(),
        });
        $('#add_icons').combobox({
            valueField:'text',
            textField:'text',
            url:'/resource/easyui/themes/icon-standard/list.json',
            editable:false,
            formatter:function(data){
                return '<img width="16" height="16" class="item-img" src="'+data.url+'"/>&nbsp;&nbsp;<span  class="item-text">&nbsp;'+data.text+'</span>';
            },
        });
    },
    //关闭添加框
    closeAddDialog: function () {
        menu.clear(menuAddDialog)
        menuAddDialog.dialog('close');
    },
    //显示编辑框
    showEditDialog: function () {
        let sels = menuTable.datagrid("getSelections");
        if (sels.length < 1) {
            $.messager.alert("对话框", "至少选择一行", 'warning');
            return;
        }
        if (sels.length > 1) {
            $.messager.alert("对话框", "只能选择一行", 'warning');
            return;
        }
        menuEditDeptSelectTree.combotree({
            url: menu.URL.tree(),
        });
        $('#edit_icons').combobox({
            valueField:'text',
            textField:'text',
            url:'/resource/easyui/themes/icon-standard/list.json',
            editable:false,
            formatter:function(data){
                return '<img width="16" height="16" class="item-img" src="'+data.url+'"/>&nbsp;&nbsp;<span  class="item-text">&nbsp;'+data.text+'</span>';
            },
        });
        $.ajax({
            type: "GET",
            url: menu.URL.get(sels[0].id),
            success: function (data) {
                if (data.code === 0) {
                    menuEditForm.form("load", data.data);
                    menuEditDialog.dialog('open').dialog('setTitle', '编辑');
                } else {
                    alert(data.msg);
                }
            }
        });
    },
    //关闭编辑框
    closeEditDialog: function () {
        menu.clear(menuEditForm)
        menuEditDialog.dialog('close');
    },
    //重置表单
    clear: function (formElement) {
        formElement.form('clear');
    },
    //刷新
    reload: function () {
        menuTable.treegrid("reload");
    },
    //折叠
    closeAll(){
        menuTable.treegrid("collapseAll");
    },
    //展开
    openAll(){
        menuTable.treegrid("expandAll");
    },
    //改变菜单类型
    changeAddMenuType(type){
        if(type.value==0){
            //menu
            $("#menu_add_icon_tr").show();
            $("#menu_add_url_tr").show();
            $("#menu_add_orderNum_tr").show();
        }else if(type.value==1){
            //button
            $("#menu_add_icon_tr").hide();
            $("#menu_add_url_tr").hide();
            $("#menu_add_orderNum_tr").hide();
            //清空值
            $("#menu_add_icon_tr").find("input").val("");
            $("#menu_add_url_tr").find("input").val("");
            $("#menu_add_orderNum_tr").find("input").val("");
        }
    },
    changeEditMenuType(type){
        if(type.value==0){
            //menu
            $("#menu_edit_icon_tr").show();
            $("#menu_edit_url_tr").show();
            $("#menu_edit_orderNum_tr").show();
        }else if(type.value==1){
            //button
            $("#menu_edit_icon_tr").hide();
            $("#menu_edit_url_tr").hide();
            $("#menu_edit_orderNum_tr").hide();
            //清空值
            $("#menu_edit_icon_tr").find("input").val("");
            $("#menu_edit_url_tr").find("input").val("");
            $("#menu_edit_orderNum_tr").find("input").val("");
        }
    }
}
