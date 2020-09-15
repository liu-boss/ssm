package com.coderman.backend.util;

import java.util.Comparator;
import java.util.List;

/**
 * 树形式显示的接口
 * @Author zhangyukang
 * @Date 2020/8/27 15:20
 * @Version 1.0
 **/
public interface TreeObject {
    /*
     * 排序,根据order排序
     */
    static Comparator<TreeObject> order(){
        return (o1, o2) -> {
            if(!o1.getOrderNum().equals(o2.getOrderNum())){
                return (int) (o1.getOrderNum() - o2.getOrderNum());
            }
            return 0;
        };
    }

    Long getOrderNum();
    Object getId();
    Object getParentId();
    List<TreeObject> getChildren();
    void setChildren(List<TreeObject> children);
}
