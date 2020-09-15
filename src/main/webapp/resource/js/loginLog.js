let loginLogTable;
let ctx = null;
let loginLog = {
    init: function (contextPath) {
        ctx = contextPath;
        loginLogTable = $("#loginLog_table"); //表格
        loginLog.initTable(); //初始化表格
    },
    URL: {
        list: function () {
            return ctx + '/backend/monitor/loginLog/list.do';
        },
        delete: function () {
            return ctx + '/backend/monitor/loginLog/delete.do';
        },
    },
    //初始化表格数据
    initTable: function () {
        loginLogTable.datagrid({
            title: '登入信息表',
            url: loginLog.URL.list(),
            autoSave: true,//自动保存
            fit: true,
            collapsible: true,
            fitColumns: true,
            toolbar: '#loginLog_toolbar',//绑定工具栏
            pagination: true,  //是否开启分页
            pageList: [14, 15, 20, 30],
            pageSize: 14,
            rownumbers: true, //显示行列号
            showFooter: true,
            singleSelect: false, //只可以选择1行
            columns: [[
                {field: 'id', title: 'ID', checkbox: true},
                {field: 'username', title: '用户名', width: 120, align: 'center'},
                {field: 'ip', title: '主机IP', width: 220, align: 'center'},
                {field: 'location', title: '登入地址', width: 220, align: 'center'},
                {field: 'loginTime', title: '登入时间', width: 150, align: 'center'},
                {field: 'os', title: '操作系统', width: 150, align: 'center'},
                {field: 'browser', title: '浏览器', width: 150, align: 'center'},
            ]]
        });
    },
    //刷新
    reload: function () {
        loginLogTable.datagrid("reload");
    },
    //删除
    delete: function () {
        //获取选中行的数据
        let selectRows = loginLogTable.datagrid("getSelections");
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
                $.post(loginLog.URL.delete() + '?id=' + strIds, function (jsonObj) {
                    if (jsonObj.code === 0) {
                        loginLogTable.datagrid("reload"); //删除成功后 刷新页面
                        $.messager.alert('提示', '删除成功！', 'info');
                    } else {
                        $.messager.alert('提示信息', '删除失败:' + jsonObj.msg, 'warning');
                    }
                }, "JSON");
            }
        });
    },

}
