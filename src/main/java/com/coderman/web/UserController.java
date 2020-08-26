package com.coderman.web;

import com.coderman.common.JsonData;
import com.coderman.dto.UserLoginDTO;
import com.coderman.service.UserService;
import com.coderman.util.BeanValidator;
import com.wf.captcha.GifCaptcha;
import com.wf.captcha.utils.CaptchaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    /**
     * 用户登入
     * @param loginDTO
     * @return
     */
    @RequestMapping(value = "/login.do",method = RequestMethod.POST)
    @ResponseBody
    public JsonData login(@RequestBody UserLoginDTO loginDTO,HttpServletRequest request){
        BeanValidator.check(loginDTO);
        String captcha = (String)request.getSession().getAttribute("captcha");
        if(captcha==null){
            return JsonData.fail("验证码过期");
        }else if(!captcha.equalsIgnoreCase(loginDTO.getVerCode())){
            return JsonData.fail("验证码错误");
        } else {
            userService.login(loginDTO.getUsername(),loginDTO.getPassword());
            return JsonData.success();
        }
    }

    /**
     * 图形验证码
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/captcha.do")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        GifCaptcha gifCaptcha = new GifCaptcha(130,34,4);
        CaptchaUtil.out(gifCaptcha, request, response);
    }
}
