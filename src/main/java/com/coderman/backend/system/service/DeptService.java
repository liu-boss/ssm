package com.coderman.backend.system.service;

import com.coderman.backend.system.dto.form.DeptParam;
import com.coderman.backend.system.model.Dept;
import com.coderman.backend.util.TreeObject;

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
