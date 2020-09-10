package com.coderman.system.dto.form;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @Author zhangyukang
 * @Date 2020/8/28 18:29
 * @Version 1.0
 **/
public class DeptParam {
    private Long id;

    private Long parentId;

    @NotEmpty(message = "部门名称不能为空")
    @Length(max = 10, min = 2,message = "部门名称长度限制2~10字符")
    private String deptName;

    private Long orderNum;

    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Long getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Long orderNum) {
        this.orderNum = orderNum;
    }
}
