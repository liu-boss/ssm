package com.coderman.common;

import com.coderman.model.User;
import com.coderman.util.JwtUtil;
import com.coderman.util.WebUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * token 拦截器
 *
 * @Author zhangyukang
 * @Date 2020/8/5 18:16
 * @Version 1.0
 **/
public class JwtInterceptor implements HandlerInterceptor {


    private Logger logger= LoggerFactory.getLogger(JwtInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("url:" + request.getRequestURL() + "被拦截");

        /** token 验证 */
        String token = request.getHeader(JwtUtil.getHeader());
        if (StringUtils.isEmpty(token)) {
            token = request.getParameter(JwtUtil.getHeader());
        }
        if (StringUtils.isEmpty(token)) {
            throw new SignatureException(JwtUtil.getHeader() + "不能为空");
        }
        Claims claims;
        try {
            claims = JwtUtil.getTokenClaim(token);
            if (claims == null || JwtUtil.isTokenExpired(claims.getExpiration())) {
                throw new SignatureException(JwtUtil.getHeader() + "失效，请重新登录。");
            }
        } catch (Exception e) {
            throw new SignatureException(JwtUtil.getHeader() + "失效，请重新登录。");
        }

        /** 设置 identityId 用户身份ID,用户信息等. */
        User user = new User();
        user.setId(Integer.valueOf(claims.getSubject()));
        user.setUsername((String) claims.get("username"));
        user.setRole((String) claims.get("role"));
        WebUtil.setUser(user);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        WebUtil.remove();
    }
}
