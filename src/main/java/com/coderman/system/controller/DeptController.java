package com.coderman.system.controller;

import com.coderman.common.JsonData;
import com.coderman.common.ProjectConstant;
import com.coderman.common.aop.Operate;
import com.coderman.system.dto.form.DeptParam;
import com.coderman.exception.ParamException;
import com.coderman.system.model.Dept;
import com.coderman.system.service.DeptService;
import com.coderman.util.BeanValidator;
import com.coderman.util.TreeObject;
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
 * @Date 2020/8/27 16:35
 * @Version 1.0
 **/
@Controller
@RequestMapping("/system/dept")
public class DeptController {

    @Autowired
    private DeptService deptService;

    @RequestMapping(value = "/tree.do", method = RequestMethod.POST)
    @ResponseBody
    public List<TreeObject> tree() {
        return deptService.tree();
    }

    @Operate(operateModule = ProjectConstant.SYSTEM_MODULE,operateDesc = "编辑部门")
    @RequestMapping(value = "/get.do", method = RequestMethod.GET)
    @ResponseBody
    public JsonData get(@RequestParam("id") Long id) {
        Dept dept = deptService.getById(id);
        return JsonData.success(dept);
    }

    @Operate(operateModule = ProjectConstant.SYSTEM_MODULE,operateDesc = "新增部门")
    @RequiresPermissions({"system:dept:add"})
    @RequestMapping(value = "/add.do", method = RequestMethod.POST)
    @ResponseBody
    public JsonData add(DeptParam deptParam) throws ParamException {
        BeanValidator.check(deptParam);
        deptService.add(deptParam);
        return JsonData.success();
    }

    @Operate(operateModule = ProjectConstant.SYSTEM_MODULE,operateDesc = "删除部门")
    @RequiresPermissions({"system:dept:delete"})
    @RequestMapping(value = "/delete.do", method = RequestMethod.POST)
    @ResponseBody
    public JsonData delete(@RequestParam("id") Long id) {
        deptService.delete(id);
        return JsonData.success();
    }

    @Operate(operateModule = ProjectConstant.SYSTEM_MODULE,operateDesc = "更新部门")
    @RequiresPermissions({"system:dept:update"})
    @RequestMapping(value = "/update.do", method = RequestMethod.POST)
    @ResponseBody
    public JsonData update(DeptParam deptParam) throws ParamException {
        BeanValidator.check(deptParam);
        deptService.update(deptParam);
        return JsonData.success();
    }

}
