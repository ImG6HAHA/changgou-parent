package com.changgou.user.service.Impl;

import com.changgou.user.dao.UserMapper;
import com.changgou.user.pojo.User;
import com.changgou.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * AUTHOR: Z
 * CREATE TIME:2022-02-01-20:21
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public User findByName(String username) {
       return userMapper.selectByPrimaryKey(username);
    }

    @Override
    public List<User> findAll() {
        return userMapper.selectAll();
    }

    @Override
    public void addPoints(String username, Integer points) {
        userMapper.addPoints(username, points);
    }
}
