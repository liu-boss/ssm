package com.coderman.vo;

import com.coderman.util.TreeObject;

import java.util.List;

/**
 * @Author zhangyukang
 * @Date 2020/8/27 15:46
 * @Version 1.0
 **/
public class MenuVO implements TreeObject {

    private Long id;

    private Long parentId;

    private String name;

    private List<TreeObject> children;

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


    public MenuVO(Long id, Long parentId, String name, List<TreeObject> children) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.children = children;
    }

    @Override
    public String toString() {
        return "MenuVO{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", name='" + name + '\'' +
                ", children=" + children +
                '}';
    }
}
