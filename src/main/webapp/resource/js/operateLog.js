let operateLogTable;
let ctx = null;
let operateLogSearchForm;
let operateLog = {
    init: function (contextPath) {
        ctx = contextPath;
        operateLogSearchForm=$("#operate_search_form");
        operateLogTable = $("#operateLog_table"); //表格
        operateLog.initTable(); //初始化表格
    },
    URL: {
        list: function () {
            return ctx + '/backend/monitor/operateLog/list.do';
        },
        delete: function () {
            return ctx + '/backend/monitor/operateLog/delete.do';
        },
    },
    //初始化表格数据
    initTable: function () {
        operateLogTable.datagrid({
            title: '操作日志表',
            url: operateLog.URL.list(),
            autoSave: true,//自动保存
            fit: true,
            rownumbers: true, //显示行列号
            collapsible: true,
            fitColumns: false,
            toolbar: '#operateLog_toolbar',//绑定工具栏
            pagination: true,  //是否开启分页
            pageList: [12, 15, 20, 30],
            pageSize: 12,
            showFooter: true,
            singleSelect: false, //只可以选择1行
            columns: [[
                {field: 'id', title: 'ID', checkbox: true},
                {field: 'username', title: '用户名', width: 150, align: 'center'},
                {field: 'ip', title: '主机IP', width: 200, align: 'center'},
                {field: 'location', title: '登入地址', width: 200, align: 'center'},
                {field: 'operation', title: '操作', width: 200, align: 'center'},
                {field: 'createTime', title: '操作时间', width: 200, align: 'center'},
                {field: 'time', title: '耗时/毫秒', width: 150, align: 'center'},
                {
                    field: 'type', title: '状态', width: 150, align: 'center',
                    formatter: function (value, row, index) {
                        if (row.type === 1) {
                            return "<font color='green' >操作成功</font>"
                        } else {
                            return "<font color='#f40' >操作失败</font>"
                        }
                    }
                },

                {field: 'controller', title: '控制器', width: 200, align: 'center'},
                {field: 'method', title: '方法', width: 150, align: 'center'},
                {field: 'returnValue', title: '返回值', width: 250, align: 'center'},
                {field: 'params', title: '参数', width: 200, align: 'center'},
                {
                    field: 'errorMsg', title: '错误消息', width: 200, align: 'center'
                    ,
                    formatter: function (value, row, index) {
                        if (row.type === 1) {
                            return "<font color='#a9a9a9' >无</font>"
                        } else {
                            return "<font color='red' >" + row.errorMsg + "</font>"
                        }
                    }
                },
            ]]
        });
    },
    //刷新
    reload: function () {
        operateLogTable.datagrid("reload");
    },
    //查询
    search: function () {
        operateLogTable.datagrid('load', {
            ip : operateLogSearchForm.find("#ip").val(),
            location : operateLogSearchForm.find("#location").val(),
            method : operateLogSearchForm.find("#method").val(),
            operation : operateLogSearchForm.find("#operation").val(),
            type : operateLogSearchForm.find("#type").val(),
        });
    },
    //重置表单
    clear: function (formElement) {
        formElement.form('clear');
    },
    //删除
    delete: function () {
        //获取选中行的数据
        let selectRows = operateLogTable.datagrid("getSelections");
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
                $.post(operateLog.URL.delete() + '?id=' + strIds, function (jsonObj) {
                    if (jsonObj.code === 0) {
                        operateLogTable.datagrid("reload"); //删除成功后 刷新页面
                        $.messager.alert('提示', '删除成功！', 'info');
                    } else {
                        $.messager.alert('提示信息', '删除失败:' + jsonObj.msg, 'warning');
                    }
                }, "JSON");
            }
        });
    },

}
