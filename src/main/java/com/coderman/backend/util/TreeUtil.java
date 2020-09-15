package com.coderman.backend.util;

import javax.xml.bind.Element;
import java.util.*;

/**
 * 组装树形结构的工具类
 *
 * @Author zhangyukang
 * @Date 2020/8/27 15:21
 * @Version 1.0
 **/
public class TreeUtil {


    /**
     * 使用map获取树形结构
     * @param nodeList
     * @param index 是否使用索引
     * @return
     */
    public static List<TreeObject> buildTree(List<TreeObject> nodeList,boolean index) {
        if(index){
            List<TreeObject> rootList= new ArrayList<>();
            Map<Object, TreeObject> map = new HashMap<>();
            for (TreeObject p : nodeList) {
                map.put(p.getId(), p);
            }
            for ( TreeObject p : nodeList ) {
                if ( p.getParentId() == null || (Long) p.getParentId() == 0) {
                    rootList.add(p);
                } else {
                    TreeObject parent = map.get(p.getParentId());
                    parent.getChildren().add(p);
                }
            }
            return rootList;
        }else {
            return buildTree(nodeList);
        }
    }

    /**
     * 递归
     * 组装tree
     * @param nodeList
     * @return
     */
    private static List<TreeObject> buildTree(List<TreeObject> nodeList) {
        List<TreeObject> rootList = new ArrayList<>();
        if (null == nodeList || nodeList.size() == 0) {
            return rootList;
        }
        //获取所有的root节点
        for (TreeObject treeObject : nodeList) {
            if (treeObject.getParentId() == null || (Long) treeObject.getParentId() == 0) {
                rootList.add(treeObject);
            }
        }
        if (rootList.size() > 0) {
            /* order排序 */
            rootList.sort(TreeObject.order());
            for (TreeObject root : rootList) {
                //设置子节点
                root.setChildren(getChildList(root, nodeList));
            }
        }
        return rootList;
    }

    /**
     * 获取当前节点的子节点
     *
     * @param current
     * @param nodeList
     * @return
     */
    private static List<TreeObject> getChildList(TreeObject current, List<TreeObject> nodeList) {
        List<TreeObject> childList = new ArrayList<>();
        for (TreeObject node : nodeList) {
            if (node.getParentId() != null && node.getParentId().equals(current.getId())) {
                childList.add(node);
            }
        }
        if (childList.size() > 0) {
            /* order排序 */
            childList.sort(TreeObject.order());
            for (TreeObject childNode : childList) {
                childNode.setChildren(getChildList(childNode, nodeList));
            }
            return childList;
        }else {
            return null;
        }
    }

}
