package com.coderman.backend.system.dto;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @Author zhangyukang
 * @Date 2020/8/26 17:15
 * @Version 1.0
 **/
public class UserLoginDTO {
    @NotBlank(message = "用户名不能为空")
    private String username; //用户名
    @NotBlank(message = "登入密码不能为空")
    private String password; //密码
    @NotBlank(message = "验证码不能为空")
    private String verCode; //登入验证码

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerCode() {
        return verCode;
    }

    public void setVerCode(String verCode) {
        this.verCode = verCode;
    }
}
