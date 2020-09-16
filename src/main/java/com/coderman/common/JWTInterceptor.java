package com.coderman.common;

import com.coderman.model.User;
import com.coderman.util.JWTUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureException;
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
public class JWTInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("url:" + request.getRequestURL() + "被拦截");
        /** Token 验证 */
        final String header = JWTUtil.getHeader();
        String token = request.getHeader(header);
        if (StringUtils.isEmpty(token)) {
            token = request.getParameter(header);
        }
        if (StringUtils.isEmpty(token)) {
            throw new SignatureException(header + "不能为空");
        }
        Claims claims;
        try {
            claims = JWTUtil.getTokenClaim(token);
            if (claims == null || JWTUtil.isTokenExpired(claims.getExpiration())) {
                throw new SignatureException(header + "失效，请重新登录。");
            }
        } catch (Exception e) {
            throw new SignatureException(header + "失效，请重新登录。");
        }

        /** 设置 identityId 用户身份ID,用户信息等. */
        User user=new User();
        user.setId(Integer.valueOf(claims.getSubject()));
        user.setUsername((String) claims.get("username"));
        WebContextHolder.setUser(user);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        WebContextHolder.remove();
    }
}
