package com.example.springsecurityjwt;


import com.example.springsecurityjwt.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void findUserList() {
        System.out.println(userService.findUserList());
    }


    @Test
    public void findUserByUsernameTest() {

        System.out.println(userService.findUserByUsername("0x22"));
    }
}