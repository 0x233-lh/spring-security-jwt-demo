package com.example.springsecurityjwt.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Secured("ROLE_ADMIN")
    @RequestMapping("/queryList")
    public ResponseEntity queryList() {
        return ResponseEntity.ok("查询成功！");
    }

}

