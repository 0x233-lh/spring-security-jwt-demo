package com.example.springsecurityjwt.filter;

import com.example.springsecurityjwt.entity.Payload;
import com.example.springsecurityjwt.entity.User;
import com.example.springsecurityjwt.utils.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class JwtVerifyFilter extends BasicAuthenticationFilter {

    public JwtVerifyFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 从请求头中获取token
        String jwtToken = request.getHeader("Authorization");

        if (jwtToken == null || !jwtToken.startsWith("Bearer")) {
            //如果携带错误的token，则给用户提示请登录！
            chain.doFilter(request, response);
            response.setContentType("application/json;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            PrintWriter out = response.getWriter();
            Map resultMap = new HashMap();
            resultMap.put("code", HttpServletResponse.SC_FORBIDDEN);
            resultMap.put("msg", "请登录！");
            out.write(new ObjectMapper().writeValueAsString(resultMap));
            out.flush();
            out.close();
            return;
        }

        // 获取正确的token
       jwtToken = jwtToken.replace("Bearer", "");
        // 校验token是否正确
        Payload<User> payload = JwtUtils.getInfoFromToken(jwtToken, User.class);
        User user = payload.getUserInfo();

        if (Objects.nonNull(user)) {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            chain.doFilter(request, response);
        }
    }


}
