package com.coderman.mapper;

import com.coderman.entity.User;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

/**
 * @Author zhangyukang
 * @Date 2020/6/22 18:11
 * @Version 1.0
 **/
public interface UserMapper extends BaseMapper<User> {

    List<User> list();

}
