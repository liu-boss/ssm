package com.coderman.system.controller;

import com.coderman.common.JsonData;
import com.coderman.common.ProjectConstant;
import com.coderman.common.aop.Operate;
import com.coderman.system.dto.form.MenuParam;
import com.coderman.exception.ParamException;
import com.coderman.system.model.Menu;
import com.coderman.system.service.MenuService;
import com.coderman.util.BeanValidator;
import com.coderman.util.ShiroContextHolder;
import com.coderman.util.TreeObject;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author zhangyukang
 * @Date 2020/8/30 10:21
 * @Version 1.0
 **/
@Controller
@RequestMapping("/system/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    //用户菜单
    @RequestMapping(value = "/getUserMenus", method = RequestMethod.POST)
    @ResponseBody
    public JsonData getUserMenus(@RequestParam(value = "username") String username) {
        if (!username.equalsIgnoreCase(ShiroContextHolder.getUser().getUsername())) {
            throw new ParamException("非法获取其他用户菜单");
        } else {
            List<TreeObject> userMenus = menuService.getUserMenus(username);
            return JsonData.success(userMenus);
        }
    }

    @RequestMapping(value = "/tree.do", method = RequestMethod.POST)
    @ResponseBody
    public List<TreeObject> tree() {
        return menuService.tree();
    }

    //添加
    @Operate(operateModule = ProjectConstant.SYSTEM_MODULE,operateDesc = "新增菜单")
    @RequiresPermissions({"system:menu:add"})
    @RequestMapping(value = "/add.do", method = RequestMethod.POST)
    @ResponseBody
    public JsonData add(MenuParam menuParam) throws ParamException {
        BeanValidator.check(menuParam);
        menuService.add(menuParam);
        return JsonData.success();
    }

    @Operate(operateModule = ProjectConstant.SYSTEM_MODULE,operateDesc = "更新菜单")
    @RequestMapping(value = "/get.do", method = RequestMethod.GET)
    @ResponseBody
    public JsonData get(@RequestParam("id") Long id) {
        Menu menu = menuService.getById(id);
        return JsonData.success(menu);
    }

    @Operate(operateModule = ProjectConstant.SYSTEM_MODULE,operateDesc = "更新菜单")
    @RequiresPermissions({"system:menu:update"})
    @RequestMapping(value = "/update.do", method = RequestMethod.POST)
    @ResponseBody
    public JsonData update(MenuParam menuParam) throws ParamException {
        BeanValidator.check(menuParam);
        menuService.update(menuParam);
        return JsonData.success();
    }

    @Operate(operateModule = ProjectConstant.SYSTEM_MODULE,operateDesc = "删除菜单")
    @RequiresPermissions({"system:menu:delete"})
    @RequestMapping(value = "/delete.do", method = RequestMethod.POST)
    @ResponseBody
    public JsonData delete(@RequestParam("id") Long id) {
        menuService.delete(id);
        return JsonData.success();
    }

}
