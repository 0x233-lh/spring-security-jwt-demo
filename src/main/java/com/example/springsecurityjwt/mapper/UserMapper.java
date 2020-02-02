package com.example.springsecurityjwt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springsecurityjwt.entity.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper extends BaseMapper<User> {

    User findUserByUsername(@Param("username") String username);
}
