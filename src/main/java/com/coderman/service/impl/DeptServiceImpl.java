package com.coderman.service.impl;

import com.coderman.mapper.DeptMapper;
import com.coderman.model.Dept;
import com.coderman.service.DeptService;
import com.coderman.util.TreeObject;
import com.coderman.util.TreeUtil;
import com.coderman.vo.DeptTreeVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author zhangyukang
 * @Date 2020/8/27 16:43
 * @Version 1.0
 **/
@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptMapper deptMapper;

    @Override
    public List<TreeObject> tree() {
        List<Dept> list=deptMapper.selectAll();
        List<TreeObject> voList=new ArrayList<>();
        for (Dept dept : list) {
            DeptTreeVO deptTreeVO = new DeptTreeVO();
            BeanUtils.copyProperties(dept,deptTreeVO);
            voList.add(deptTreeVO);
        }
        return TreeUtil.buildTree(voList);
    }
}
