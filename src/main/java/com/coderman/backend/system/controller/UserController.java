package com.coderman.backend.system.controller;

import com.coderman.backend.common.EasyUIData;
import com.coderman.backend.common.JsonData;
import com.coderman.backend.common.ProjectConstant;
import com.coderman.backend.common.aop.Operate;
import com.coderman.backend.system.dto.UserLoginDTO;
import com.coderman.backend.system.dto.form.UserAddParam;
import com.coderman.backend.system.dto.form.UserUpdateParam;
import com.coderman.backend.exception.ParamException;
import com.coderman.backend.monitor.model.LoginLog;
import com.coderman.backend.system.model.User;
import com.coderman.backend.monitor.service.LoginLogService;
import com.coderman.backend.system.service.UserService;
import com.coderman.backend.util.BeanValidator;
import com.coderman.backend.util.HttpUtil;
import com.coderman.backend.util.ShiroContextHolder;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wf.captcha.GifCaptcha;
import com.wf.captcha.utils.CaptchaUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @Author zhangyukang
 * @Date 2020/8/26 17:13
 * @Version 1.0
 **/
@Controller
@RequestMapping("/backend/system/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @Autowired
    private LoginLogService loginLogService;

    @RequestMapping(value = "/queryRole.do", method = RequestMethod.GET)
    @ResponseBody
    public JsonData queryRole(@RequestParam("id") String userId) {
        Set<Long> roleIdSet = userService.queryRolesById(userId);
        return JsonData.success(roleIdSet);
    }

    @RequestMapping(value = "/logout.do", method = RequestMethod.GET)
    public String logout() {
        logger.info("退出登入:user=>{}", ShiroContextHolder.getUser());
        SecurityUtils.getSubject().logout();
        return "redirect:/backend/loginPage.do";
    }

    @RequestMapping(value = "/list.do", method = RequestMethod.POST)
    @ResponseBody
    public EasyUIData<User> list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                 @RequestParam(value = "rows", defaultValue = "20") Integer rows,
                                 User user) {
        PageHelper.startPage(page, rows);
        List<User> userList = userService.query(user);
        PageInfo<User> pageInfo = new PageInfo<>(userList);
        return new EasyUIData<>(pageInfo.getTotal(), pageInfo.getList());
    }

    @Operate(operateModule = ProjectConstant.SYSTEM_MODULE,operateDesc = "密码重置")
    @RequiresPermissions({"system:user:resetPassword"})
    @RequestMapping(value = "/resetPassword.do", method = RequestMethod.GET)
    @ResponseBody
    public JsonData resetPassword(@RequestParam("id") Long userId) {
        userService.resetPassword(userId);
        return JsonData.success();
    }

    @Operate(operateModule = ProjectConstant.SYSTEM_MODULE,operateDesc = "新增用户")
    @RequiresPermissions({"system:user:add"})
    @RequestMapping(value = "/add.do", method = RequestMethod.POST)
    @ResponseBody
    public JsonData add(UserAddParam addParam) throws ParamException {
        BeanValidator.check(addParam);
        userService.add(addParam);
        return JsonData.success();
    }

    @Operate(operateModule = ProjectConstant.SYSTEM_MODULE,operateDesc = "编辑用户")
    @RequestMapping(value = "/get.do", method = RequestMethod.GET)
    @ResponseBody
    public JsonData get(@RequestParam("id") Long id) {
        User user = userService.getById(id);
        return JsonData.success(user);
    }

    @Operate(operateModule = ProjectConstant.SYSTEM_MODULE,operateDesc = "更新用户")
    @RequiresPermissions({"system:user:update"})
    @RequestMapping(value = "/update.do", method = RequestMethod.POST)
    @ResponseBody
    public JsonData update(UserUpdateParam userUpdateParam) throws ParamException {
        BeanValidator.check(userUpdateParam);
        userService.update(userUpdateParam);
        return JsonData.success();
    }

    @Operate(operateModule = ProjectConstant.SYSTEM_MODULE,operateDesc = "删除用户")
    @RequiresPermissions({"system:user:delete"})
    @RequestMapping(value = "/delete.do", method = RequestMethod.POST)
    @ResponseBody
    public JsonData delete(@RequestParam("id") String strIds) throws ParamException {
        String[] ids = strIds.split(",");
        userService.delete(ids);
        return JsonData.success();
    }

    @RequestMapping(value = "/login.do", method = RequestMethod.POST)
    @ResponseBody
    public JsonData login(@RequestBody UserLoginDTO loginDTO, HttpServletRequest request) throws ParamException {
        BeanValidator.check(loginDTO);
        String captcha = (String) request.getSession().getAttribute("captcha");
        if (captcha == null) {
            return JsonData.fail("验证码过期");
        } else if (!captcha.equalsIgnoreCase(loginDTO.getVerCode())) {
            return JsonData.fail("验证码错误");
        } else {
            String username = loginDTO.getUsername();
            String password = loginDTO.getPassword();
            userService.login(username, password);
            //保存登入日志
            LoginLog loginLog = new LoginLog();
            loginLog.setSystemBrowserInfo();
            loginLog.setUsername(username);
            loginLog.setLoginTime(new Date());
            loginLog.setIp(HttpUtil.getIpAddr(request));
            loginLog.setLocation(HttpUtil.getCityInfo(request));
            loginLogService.add(loginLog);
            return JsonData.success();
        }
    }

    @RequestMapping("/captcha.do")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        GifCaptcha gifCaptcha = new GifCaptcha(130, 34, 4);
        CaptchaUtil.out(gifCaptcha, request, response);
    }
}
