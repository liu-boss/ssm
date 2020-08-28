package com.coderman.util;

import java.util.List;

/**
 * 树形式显示的接口
 * @Author zhangyukang
 * @Date 2020/8/27 15:20
 * @Version 1.0
 **/
public interface TreeObject {
    Object getId();
    Object getParentId();
    List<TreeObject> getChildren();
    void setChildren(List<TreeObject> children);
}
