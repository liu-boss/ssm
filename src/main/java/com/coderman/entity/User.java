package com.coderman.entity;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author zhangyukang
 * @Date 2020/6/22 18:12
 * @Version 1.0
 **/
@Table(name = "tb_user")
public class User {

    @Id
    private Integer id;

    private String username;

    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
