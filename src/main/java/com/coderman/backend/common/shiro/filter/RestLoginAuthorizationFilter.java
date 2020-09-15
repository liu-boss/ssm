package com.coderman.backend.common.shiro.filter;

import com.coderman.backend.common.JsonData;
import com.coderman.backend.util.HttpUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 解决前后端分离,页面跳转问题
 * @Author zhangyukang
 * @Date 2020/8/17 08:20
 * @Version 1.0
 **/
public class RestLoginAuthorizationFilter extends FormAuthenticationFilter {

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (isLoginRequest(request, response)) {
            if (isLoginSubmission(request, response)) {
                return executeLogin(request, response);
            } else {
                //allow them to see the login page ;)
                return true;
            }
        } else {
            HttpServletRequest req= (HttpServletRequest) request;
            HttpServletResponse resp= (HttpServletResponse) response;
            if(HttpUtil.isAjax(req)){
                JsonData need_login = JsonData.fail(JsonData.NOT_LOGIN, "need login");
                ObjectMapper objectMapper=new ObjectMapper();
                HttpUtil.writeObject(resp, objectMapper.writeValueAsString(need_login));
            }else {
                saveRequestAndRedirectToLogin(request, response);
            }
            return false;
        }
    }

}




