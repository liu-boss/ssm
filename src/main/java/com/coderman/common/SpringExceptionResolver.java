package com.coderman.common;

import com.coderman.exception.BizException;
import com.coderman.exception.ParamException;
import com.coderman.util.HttpUtil;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class SpringExceptionResolver implements HandlerExceptionResolver {

    private Logger log = LoggerFactory.getLogger(SpringExceptionResolver.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        String url = request.getRequestURL().toString();
        ModelAndView mv;
        String defaultMsg = "系统异常,请联系管理员";

        if (HttpUtil.isAjax(request)) {
            if (ex instanceof BizException) {
                BizException exception = (BizException) ex;
                log.error("ajax请求:业务异常, url:" + url + "  msg:" + exception.getMessage());//业务异常
                JsonData result = JsonData.fail(ex.getMessage());
                mv = new ModelAndView("jsonView", result.toMap());
            } else if (ex instanceof ParamException) {
                ParamException exception = (ParamException) ex;
                log.error("ajax请求:参数异常, url:" + url + "  msg:" + exception.getMessage());
                JsonData result = JsonData.fail(JsonData.PARAM_ERROR, ex.getMessage());//参数异常
                mv = new ModelAndView("jsonView", result.toMap());
            } else if (ex instanceof UnauthorizedException) {
                UnauthorizedException exception = (UnauthorizedException) ex;
                log.error("ajax请求:授权异常, url:" + url + "  msg:" + exception.getMessage());
                JsonData result = JsonData.fail(JsonData.UNAUTHORIZED, ex.getMessage());//未授权异常
                mv = new ModelAndView("jsonView", result.toMap());
            } else {
                log.error("ajax请求:系统异常, url:" + url, ex);
                JsonData result = JsonData.fail(JsonData.INTERNAL_SERVER_ERROR, defaultMsg);//系统异常
                mv = new ModelAndView("jsonView", result.toMap());
            }
        } else {
            if (ex instanceof UnauthorizedException) {
                log.error("页面请求出现异常:" + url, ex.getMessage());
                Map<String, Object> map = new HashMap<>();
                map.put("errorMsg", ex.toString());
                mv = new ModelAndView("error/unauthorized", map);
            } else {
                log.error("页面请求出现异常:" + url, ex.getMessage());
                Map<String, Object> map = new HashMap<>();
                map.put("errorMsg", ex.toString());
                mv = new ModelAndView("error/exception", map);
            }
        }
        return mv;
    }

}
