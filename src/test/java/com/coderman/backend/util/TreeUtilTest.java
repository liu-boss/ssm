package com.coderman.backend.util;

import com.coderman.backend.system.mapper.DeptMapper;
import com.coderman.backend.system.model.Dept;
import com.coderman.backend.system.vo.DeptTreeVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author zhangyukang
 * @Date 2020/8/27 15:51
 * @Version 1.0
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/application-dao.xml", "classpath:spring/application-service.xml"})
public class TreeUtilTest {

    @Autowired
    private DeptMapper deptMapper;
//
//    public static void main(String[] args) {
//        List<TreeObject> list=new ArrayList<>();
//        list.add(new MenuVO(1L,null,"系统管理",null));
//        list.add(new MenuVO(2L,1L,"用户管理",null));
//        list.add(new MenuVO(3L,1L,"角色管理",null));
//        list.add(new MenuVO(4L,3L,"角色添加",null));
//        list.add(new MenuVO(5L,null,"其他管理",null));
//        List<TreeObject> tree = TreeUtil.buildTree(list);
//        System.out.println(tree);
//    }

    @Test
    public void deptVOTreeTest() throws JsonProcessingException {
        List<Dept> list=deptMapper.selectAll();
        List<TreeObject> voList=new ArrayList<>();
        for (Dept dept : list) {
            DeptTreeVO deptTreeVO = new DeptTreeVO();
            BeanUtils.copyProperties(dept,deptTreeVO);
            voList.add(deptTreeVO);
        }
        List<TreeObject> tree = TreeUtil.buildTree(voList,true);
        ObjectMapper mapper=new ObjectMapper();
        String json = mapper.writeValueAsString(tree);
        System.out.println(json);
    }
}
