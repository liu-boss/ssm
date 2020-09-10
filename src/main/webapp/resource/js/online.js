let onlineTable;
let ctx = null;
let current;
let online = {
    init: function (contextPath,username) {
        ctx = contextPath;
        current=username;
        onlineTable = $("#online_table"); //表格
        online.initTable(); //初始化表格
    },
    URL: {
        list: function () {
            return ctx + '/system/online/list.do';
        },
        forceLogout: function (strs) {
            return ctx+'/system/online/forceLogout.do?nameList='+strs
        }
    },
    //初始化表格数据
    initTable: function () {
        onlineTable.datagrid({
            title: '在线信息表',
            url: online.URL.list(),
            autoSave: true,//自动保存
            fit: true,
            collapsible: true,
            fitColumns: true,
            toolbar: '#online_toolbar',//绑定工具栏
            pagination: true,  //是否开启分页
            pageList: [10, 15, 20, 30],
            pageSize: 15,
            rownumbers: true, //显示行列号
            showFooter: true,
            singleSelect: false, //只可以选择1行
            columns: [[
                {field: 'id', title: 'ID', checkbox: true},
                {field: 'username', title: '用户名', width: 120, align: 'center',
                    formatter: function (value, row, index) {
                        if(row.username===current){
                            return  '<font color="#663399">[当前用户]</font>';
                        }else{
                            return row.username;
                        }
                    }},
                {field: 'host', title: '主机地址', width: 220, align: 'center'},
                {field: 'location', title: '登入地址', width: 220, align: 'center'},
                {field: 'startTime', title: '开始访问', width: 150, align: 'center'},
                {field: 'expired', title: '是否过期', width: 150, align: 'center'},
                {field: 'status', title: '状态', width: 150, align: 'center',
                    formatter: function (value, row, index) {
                        return "<font style='color: #00ee00'>[在线]</font>"
                    }},
                {field: 'timeout', title: '会话过期时间(分钟)', width: 150, align: 'center'},
                {field: 'lastAccessTime', title: '最新访问', width: 150, align: 'center'},
            ]]
        });
    },
    //踢出系统
    forceLogout: function () {
        //获取选中行的数据
        let selectRows = onlineTable.datagrid("getSelections");
        //如果没有选中行的话，提示信息
        if (selectRows.length < 1) {
            $.messager.alert("提示消息", "请选择要踢出的用户！", 'warning');
            return;
        }
        //如果选中行了，则要进行判断
        $.messager.confirm("确认消息", "确定要踢出所选用户吗？", function (isDelete) {
            //如果为真的话
            if (isDelete) {
                //定义变量值
                let strIds = "";
                //拼接字符串，这里也可以使用数组，作用一样
                for (let i = 0; i < selectRows.length; i++) {
                    strIds += selectRows[i].username + ",";
                }
                //循环切割
                strIds = strIds.substr(0, strIds.length - 1);
                $.post(online.URL.forceLogout(strIds), function (jsonObj) {
                    if (jsonObj.code === 0) {
                        onlineTable.datagrid("reload"); //删除成功后 刷新页面
                        $.messager.alert('提示', '成功踢出该用户！', 'info');
                    } else {
                        $.messager.alert('提示信息', '踢出用户失败:' + jsonObj.msg, 'warning');
                    }
                }, "JSON");
            }
        });
    },
    //刷新
    reload: function () {
        onlineTable.datagrid("reload");
    },

}
