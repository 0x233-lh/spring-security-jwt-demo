package com.example.springsecurityjwt.service;

import com.example.springsecurityjwt.entity.User;
import com.example.springsecurityjwt.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;


    public List<User> findUserList() {
        return userMapper.selectList(null);
    }


    public User findUserByUsername(String username) {
        return userMapper.findUserByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userMapper.findUserByUsername(username);
    }
}
