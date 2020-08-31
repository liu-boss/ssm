package com.coderman.service.impl;

import com.coderman.dto.form.DeptParam;
import com.coderman.exception.ParamException;
import com.coderman.mapper.DeptMapper;
import com.coderman.model.Dept;
import com.coderman.service.DeptService;
import com.coderman.util.TreeObject;
import com.coderman.util.TreeUtil;
import com.coderman.vo.DeptTreeVO;
import javafx.scene.Parent;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
        List<Dept> list = deptMapper.selectAll();
        List<TreeObject> voList = new ArrayList<>();
        for (Dept dept : list) {
            DeptTreeVO deptTreeVO = new DeptTreeVO();
            BeanUtils.copyProperties(dept, deptTreeVO);
            voList.add(deptTreeVO);
        }
        return TreeUtil.buildTree(voList);
    }

    @Override
    public Dept getById(Long id) {
        return deptMapper.selectByPrimaryKey(id);
    }

    @Override
    public void add(DeptParam deptParam) throws ParamException {
        if (deptParam.getParentId() == null) {
            deptParam.setParentId(0L);
        }
        if (deptParam.getOrderNum() == null) {
            deptParam.setOrderNum(0L);
        }
        Dept dept = new Dept();
        BeanUtils.copyProperties(deptParam, dept);
        dept.setCreateTime(new Date());
        dept.setModifyTime(new Date());
        if (deptMapper.checkByDeptName(deptParam.getDeptName(), null) > 0) { //校验部门名可用性
            throw new ParamException("部门名称已被占用");
        }
        if(dept.getParentId()!=0L){
            Dept parentDept = deptMapper.selectByPrimaryKey(dept.getParentId());
            if (parentDept == null) {
                throw new ParamException("父部门不存在");
            }
        }
        deptMapper.insert(dept);
    }

    @Override
    public void delete(Long id) throws ParamException {
        //查询是否有子节点
        if (deptMapper.checkParentDept(id) > 0) {
            throw new ParamException("有子部门无法删除");
        } else {
            deptMapper.deleteByPrimaryKey(id);
        }
    }

    @Override
    public void update(DeptParam deptParam) throws ParamException {
        if (deptParam.getParentId() == null) {
            deptParam.setParentId(0L);
        }
        if (deptParam.getOrderNum() == null) {
            deptParam.setOrderNum(0L);
        }
        if (deptMapper.checkByDeptName(deptParam.getDeptName(), deptParam.getId()) > 0) { //校验部门名可用性
            throw new ParamException("部门名称已被占用");
        }
        //查找父部门
        if (deptParam.getParentId() != 0L) {
            Dept parentDept = deptMapper.selectByPrimaryKey(deptParam.getParentId());
            if (parentDept == null) {
                throw new ParamException("父部门不存在");
            }
        }
        //添加的父节点不能是本身和其子节点
        if (deptParam.getParentId().intValue() == deptParam.getId().intValue()) {
            throw new ParamException("添加的父部门不能是本身");
        }
        //获取该节点下的所有子节点
        HashSet<Long> childIdList = new HashSet<>();
        getChildIdList(deptParam.getId(), childIdList);
        if (childIdList.contains(deptParam.getParentId())) {
            throw new ParamException("添加的父部门不能是子当前节点的子部门");
        }
        Dept dept = new Dept();
        BeanUtils.copyProperties(deptParam, dept);
        dept.setModifyTime(new Date());
        deptMapper.updateByPrimaryKeySelective(dept);
    }

    /**
     * 获取该节点下的所有子节点
     *
     * @param id
     * @param result
     */
    private void getChildIdList(Long id, HashSet<Long> result) {
        Set<Long> nextIdList = deptMapper.selectAllNextIdList(id);
        result.addAll(nextIdList);
        if (nextIdList != null && nextIdList.size() > 0) {
            for (Long next : nextIdList) {
                getChildIdList(next, result);
            }
        }
    }

}
