package com.coderman.backend.monitor.controller;

import com.coderman.backend.common.EasyUIData;
import com.coderman.backend.common.JsonData;
import com.coderman.backend.exception.ParamException;
import com.coderman.backend.monitor.model.OperateWithBLOBs;
import com.coderman.backend.monitor.service.OperateService;
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
 * @Date 2020/9/10 11:53
 * @Version 1.0
 **/
@Controller
@RequestMapping("/backend/monitor/operateLog")
public class OperateLogController {

    @Autowired
    private OperateService operateService;

    @RequestMapping(value = "/list.do", method = RequestMethod.POST)
    @ResponseBody
    public EasyUIData<OperateWithBLOBs> list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                             @RequestParam(value = "rows", defaultValue = "20") Integer rows,
                                             OperateWithBLOBs operate) {
        PageHelper.startPage(page, rows);
        List<OperateWithBLOBs> operateList = operateService.query(operate);
        PageInfo<OperateWithBLOBs> pageInfo = new PageInfo<>(operateList);
        return new EasyUIData<>(pageInfo.getTotal(), pageInfo.getList());
    }

    @RequiresPermissions({"monitor:operateLog:delete"})
    @RequestMapping(value = "/delete.do", method = RequestMethod.POST)
    @ResponseBody
    public JsonData delete(@RequestParam("id") String strIds) throws ParamException {
        String[] ids = strIds.split(",");
        operateService.delete(ids);
        return JsonData.success();
    }

}
