package com.coderman.dto.form;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @Author zhangyukang
 * @Date 2020/8/28 10:38
 * @Version 1.0
 **/
public class RoleParam {

    private Long id;

    @NotEmpty(message = "角色名称不能为空")
    @Length(max = 10, min = 2,message = "角色名称长度限制2~10字符")
    private String roleName;

    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
