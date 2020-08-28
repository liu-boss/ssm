package com.coderman.web;

import com.coderman.service.DeptService;
import com.coderman.util.TreeObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author zhangyukang
 * @Date 2020/8/27 16:35
 * @Version 1.0
 **/
@Controller
@RequestMapping("/dept")
public class DeptController {

    @Autowired
    private DeptService deptService;

    @RequestMapping(value = "/tree.do",method = RequestMethod.POST)
    @ResponseBody
    public List<TreeObject> tree(){
        return deptService.tree();
    }
}
