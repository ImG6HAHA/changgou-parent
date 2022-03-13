package com.changgou.user.service;

import com.changgou.user.pojo.User;

import java.util.List;

/**
 * AUTHOR: Z
 * CREATE TIME:2022-02-01-20:20
 */
public interface UserService {


    //查询用户
    User findByName(String username);

    //所有用户列表
    List<User> findAll();


    //用户下单后添加积分
    void addPoints(String username, Integer points);
}
