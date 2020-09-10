package com.coderman.monitor.controller;

import com.coderman.common.EasyUIData;
import com.coderman.common.JsonData;
import com.coderman.common.ProjectConstant;
import com.coderman.common.aop.Operate;
import com.coderman.exception.ParamException;
import com.coderman.monitor.model.LoginLog;
import com.coderman.monitor.service.LoginLogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author zhangyukang
 * @Date 2020/9/8 20:09
 * @Version 1.0
 **/
@Controller
@RequestMapping("/monitor/loginLog")
public class LoginLogController {

    @Autowired
    private LoginLogService loginLogService;

    @RequestMapping(value = "/list.do", method = RequestMethod.POST)
    @ResponseBody
    public EasyUIData<LoginLog> list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                     @RequestParam(value = "rows", defaultValue = "20") Integer rows,
                                     LoginLog loginLog) {
        PageHelper.startPage(page, rows);
        List<LoginLog> loginLogList = loginLogService.query(loginLog);
        PageInfo<LoginLog> pageInfo = new PageInfo<>(loginLogList);
        return new EasyUIData<>(pageInfo.getTotal(), pageInfo.getList());
    }

    @Operate(operateModule = ProjectConstant.MONITOR_MODULE,operateDesc = "删除登入日志")
    @RequiresPermissions({"monitor:loginLog:delete"})
    @RequestMapping(value = "/delete.do", method = RequestMethod.POST)
    @ResponseBody
    public JsonData delete(@RequestParam("id") String strIds) throws ParamException {
        String[] ids = strIds.split(",");
        loginLogService.delete(ids);
        return JsonData.success();
    }
}
