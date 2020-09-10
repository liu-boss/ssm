package com.coderman.system.service;

import com.coderman.system.dto.form.DeptParam;
import com.coderman.system.model.Dept;
import com.coderman.util.TreeObject;

import java.util.List;

/**
 * @Author zhangyukang
 * @Date 2020/8/27 16:42
 * @Version 1.0
 **/
public interface DeptService {
    List<TreeObject> tree(); //部门树
    Dept getById(Long id);
    void add(DeptParam deptParam);
    void delete(Long id); //删除
    void update(DeptParam deptParam);
}
