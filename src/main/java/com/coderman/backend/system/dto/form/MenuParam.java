package com.coderman.backend.system.dto.form;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @Author zhangyukang
 * @Date 2020/8/30 16:16
 * @Version 1.0
 **/
public class MenuParam {
    private Long id;

    private Long parentId;

    @NotEmpty(message = "菜单名称不能为空")
    @Length(max = 10, min = 2,message = "菜单名称长度限制2~10字符")
    private String menuName;

    private Long orderNum;

    private String icon;

    private String url;

    @NotBlank(message = "类型不能为空")
    private String type;

    private String perms;


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

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public Long getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Long orderNum) {
        this.orderNum = orderNum;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPerms() {
        return perms;
    }

    public void setPerms(String perms) {
        this.perms = perms;
    }
}
