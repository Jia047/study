package com.alex.shiro.service;

import com.alex.shiro.model.User;

public interface UserService {

    User findByUserName(String userName);
}
