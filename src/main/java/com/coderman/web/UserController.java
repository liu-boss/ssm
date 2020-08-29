package com.coderman.web;

import com.coderman.common.EasyUIData;
import com.coderman.common.JsonData;
import com.coderman.dto.UserLoginDTO;
import com.coderman.dto.form.UserAddParam;
import com.coderman.dto.form.UserUpdateParam;
import com.coderman.exception.ParamException;
import com.coderman.model.User;
import com.coderman.service.UserService;
import com.coderman.util.BeanValidator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wf.captcha.GifCaptcha;
import com.wf.captcha.utils.CaptchaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;

/**
 * @Author zhangyukang
 * @Date 2020/8/26 17:13
 * @Version 1.0
 **/
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


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

    @RequestMapping(value = "/resetPassword.do",method = RequestMethod.GET)
    @ResponseBody
    public JsonData resetPassword(@RequestParam("id") Long userId){
        userService.resetPassword(userId);
        return JsonData.success();
    }

    @RequestMapping(value = "/queryRole.do", method = RequestMethod.GET)
    @ResponseBody
    public JsonData queryRole(@RequestParam("id") String userId){
        Set<Long> roleIdSet=userService.queryRolesById(userId);
        return JsonData.success(roleIdSet);
    }

    @RequestMapping(value = "/add.do", method = RequestMethod.POST)
    @ResponseBody
    public JsonData add(UserAddParam addParam) throws ParamException {
        BeanValidator.check(addParam);
        userService.add(addParam);
        return JsonData.success();
    }

    @RequestMapping(value = "/get.do", method = RequestMethod.GET)
    @ResponseBody
    public JsonData get(@RequestParam("id") Long id) {
        User user = userService.getById(id);
        return JsonData.success(user);
    }

    @RequestMapping(value = "/update.do", method = RequestMethod.POST)
    @ResponseBody
    public JsonData update(UserUpdateParam userUpdateParam) throws ParamException {
        BeanValidator.check(userUpdateParam);
        userService.update(userUpdateParam);
        return JsonData.success();
    }

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
            userService.login(loginDTO.getUsername(), loginDTO.getPassword());
            return JsonData.success();
        }
    }

    @RequestMapping("/captcha.do")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        GifCaptcha gifCaptcha = new GifCaptcha(130, 34, 4);
        CaptchaUtil.out(gifCaptcha, request, response);
    }
}
