package com.example.springsecurityjwt.config;

import com.example.springsecurityjwt.filter.JwtLoginFilter;
import com.example.springsecurityjwt.filter.JwtVerifyFilter;
import com.example.springsecurityjwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // 认证用户的来源【内存或者数据库】
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
//        auth.inMemoryAuthentication().withUser("user").password(passwordEncoder.encode("123")).roles("USER");
    }


    // security相关的配置
    @Override
    public void configure(HttpSecurity http) throws Exception {
        // 基于上面的配置
        http
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new JwtLoginFilter(super.authenticationManager()))
                .addFilter(new JwtVerifyFilter(super.authenticationManager()));
    }

}
