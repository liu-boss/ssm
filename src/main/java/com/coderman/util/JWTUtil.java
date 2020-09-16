package com.coderman.util;

/**
 * @Author zhangyukang
 * @Date 2020/8/5 18:02
 * @Version 1.0
 **/

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * JWT的token，区分大小写
 */
public class JWTUtil {

    private static String secret="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ6aGFuZ3l1a2FuZyIsInJvbGUiOiJhZG1pbiIsImV4cCI6MTYwMDIyNTExMiwiaWF0IjoxNjAwMjIxNTEyfQ.ehJepgLDhiIGh_yY3IvJ2oYcw4OMR2YbxPWgb7QP83HB6qgPcmv96CmK9zBXUHUAzq5xnFB9OGGLrq7Nq1snRQ";
    private static long expire=60*60;
    private static String header="token";

    /**
     * 生成token
     *
     * @param subject 当前用户信息 (userId)
     * @param map     自定义信息
     * @return
     */
    public static String createToken(String subject, Map<String, Object> map) {
        Date nowDate = new Date();
        Date expireDate = new Date(nowDate.getTime() + expire * 1000);//过期时间
        JwtBuilder builder = Jwts.builder();
        if (!map.isEmpty()) {
            builder.setClaims(map);
        }
        return builder.setHeaderParam("typ", "JWT")
                .setSubject(subject)
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * 获取token中注册信息
     *
     * @param token
     * @return
     */
    public static Claims getTokenClaim(String token) {
        try {
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 验证token是否过期失效
     *
     * @param expirationTime
     * @return
     */
    public static boolean isTokenExpired(Date expirationTime) {
        return expirationTime.before(new Date());
    }

    /**
     * 获取token失效时间
     *
     * @param token
     * @return
     */
    public static Date getExpirationDateFromToken(String token) {
        return Objects.requireNonNull(getTokenClaim(token)).getExpiration();
    }

    /**
     * 获取用户名从token中
     */
    public static String getUsernameFromToken(String token) {
        return (String) Objects.requireNonNull(getTokenClaim(token)).get("username");
    }

    /**
     * 获取jwt发布时间
     */
    public static Date getIssuedAtDateFromToken(String token) {
        return Objects.requireNonNull(getTokenClaim(token)).getIssuedAt();
    }

    // --------------------- getter & setter ---------------------

    public  String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public long getExpire() {
        return expire;
    }

    public void setExpire(long expire) {
        this.expire = expire;
    }

    public static String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }
}
