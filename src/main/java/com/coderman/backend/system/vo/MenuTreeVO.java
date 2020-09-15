package com.coderman.backend.system.vo;

import com.coderman.backend.util.TreeObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author zhangyukang
 * @Date 2020/8/27 15:46
 * @Version 1.0
 **/
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MenuTreeVO implements TreeObject {

    private Long id;

    private Long parentId;

    @JsonProperty("text")
    private String menuName;

    private Long orderNum;

    private String url;

    private List<TreeObject> children=new ArrayList<>();

    @JsonProperty("iconCls")
    private String icon;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date modifyTime;

    private String type;

    private String perms;

    public String getPerms() {
        return perms;
    }

    public void setPerms(String perms) {
        this.perms = perms;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    @Override
    public Long getOrderNum() {
        return orderNum;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public Long getParentId() {
        return parentId;
    }

    @Override
    public void setChildren(List<TreeObject> children) {
        this.children=children;
    }

    @Override
    public List<TreeObject> getChildren() {
        return children;
    }

    @Override
    public String toString() {
        return "MenuTreeVO{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", menuName='" + menuName + '\'' +
                ", orderNum=" + orderNum +
                ", children=" + children +
                '}';
    }

    /** getter and setter*/
    public void setId(Long id) {
        this.id = id;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public void setOrderNum(Long orderNum) {
        this.orderNum = orderNum;
    }

    public MenuTreeVO() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

}
