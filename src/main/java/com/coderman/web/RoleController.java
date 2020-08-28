package com.coderman.web;

import com.coderman.common.EasyUIData;
import com.coderman.common.JsonData;
import com.coderman.dto.form.RoleParam;
import com.coderman.exception.ParamException;
import com.coderman.model.Role;
import com.coderman.service.RoleService;
import com.coderman.util.BeanValidator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author zhangyukang
 * @Date 2020/8/28 10:37
 * @Version 1.0
 **/
@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/listAll.do", method = RequestMethod.POST)
    @ResponseBody
    public EasyUIData<Role> listAll(){
        List<Role> roleList=roleService.listAll();
        return new EasyUIData<>(roleList.size(), roleList);
    }

    @RequestMapping(value = "/list.do", method = RequestMethod.POST)
    @ResponseBody
    public EasyUIData<Role> list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                 @RequestParam(value = "rows", defaultValue = "20") Integer rows,
                                 Role role) {
        PageHelper.startPage(page, rows);
        List<Role> roleList = roleService.query(role);
        PageInfo<Role> pageInfo = new PageInfo<>(roleList);
        return new EasyUIData<>(pageInfo.getTotal(), pageInfo.getList());
    }

    @RequestMapping(value = "/add.do", method = RequestMethod.POST)
    @ResponseBody
    public JsonData add(RoleParam roleParam) throws ParamException {
        BeanValidator.check(roleParam);
        roleService.add(roleParam);
        return JsonData.success();
    }

    @RequestMapping(value = "/get.do", method = RequestMethod.GET)
    @ResponseBody
    public JsonData get(@RequestParam("id") Long id) {
        Role role = roleService.getById(id);
        return JsonData.success(role);
    }

    @RequestMapping(value = "/update.do", method = RequestMethod.POST)
    @ResponseBody
    public JsonData update(RoleParam roleParam) throws ParamException {
        BeanValidator.check(roleParam);
        roleService.update(roleParam);
        return JsonData.success();
    }

    @RequestMapping(value = "/delete.do", method = RequestMethod.POST)
    @ResponseBody
    public JsonData delete(@RequestParam("id") String strIds) throws ParamException {
        String[] ids = strIds.split(",");
        roleService.delete(ids);
        return JsonData.success();
    }

}
