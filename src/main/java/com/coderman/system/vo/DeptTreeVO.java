package com.coderman.system.vo;

import com.coderman.util.TreeObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

/**
 * 部门树
 * @Author zhangyukang
 * @Date 2020/8/27 16:16
 * @Version 1.0
 **/
public class DeptTreeVO implements TreeObject {

    private Long id;

    private Long parentId;

    @JsonProperty("text")
    private String deptName;

    private Long orderNum;

    private String remark;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date modifyTime;


    private List<TreeObject> children;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public Object getParentId() {
        return parentId;
    }

    @Override
    public List<TreeObject> getChildren() {
        return children;
    }

    @Override
    public void setChildren(List<TreeObject> children) {
        this.children=children;
    }

    public DeptTreeVO(Long id, Long parentId, String deptName, Long orderNum, Date createTime, Date modifyTime, List<TreeObject> children) {
        this.id = id;
        this.parentId = parentId;
        this.deptName = deptName;
        this.orderNum = orderNum;
        this.createTime = createTime;
        this.modifyTime = modifyTime;
        this.children = children;
    }

    public DeptTreeVO() {
    }

    /*** getter and setter*/

    public void setId(Long id) {
        this.id = id;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


}
