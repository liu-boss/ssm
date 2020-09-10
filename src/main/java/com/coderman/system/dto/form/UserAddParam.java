package com.coderman.system.dto.form;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @Author zhangyukang
 * @Date 2020/8/26 21:12
 * @Version 1.0
 **/
public class UserAddParam {

    @NotEmpty(message = "姓名不能为空")
    @Length(max = 30, min = 2,message = "姓名长度限制2~30字符")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;

    @Email(message = "请输入合法的邮箱")
    private String email;

    @Pattern(regexp = "1[3|4|5|7|8][0-9]\\d{8}",message = "输入合法的电话号码")
    @NotBlank(message = "电话不能为空")
    private String mobile;

    @NotBlank(message = "性别不能为空")
    private String sex;

    @NotBlank(message = "用户状态不能空")
    private String status;

    @Size(min = 2,max = 100,message = "描述限制长度限制2~30字符")
    private String description;

    private Long[] roleIdList;

    private Long deptId;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long[] getRoleIdList() {
        return roleIdList;
    }

    public void setRoleIdList(Long[] roleIdList) {
        this.roleIdList = roleIdList;
    }
}
