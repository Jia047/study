package com.alex.shiro.service.impl;

import com.alex.shiro.mapper.UserMapper;
import com.alex.shiro.model.User;
import com.alex.shiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User findByUserName(String userName) {
        return userMapper.findByUserName(userName);
    }
}
