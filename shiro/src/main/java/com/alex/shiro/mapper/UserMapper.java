package com.alex.shiro.mapper;

import com.alex.shiro.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {

    User findByUserName(String username);
}
