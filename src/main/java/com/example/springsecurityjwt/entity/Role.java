package com.example.springsecurityjwt.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
@TableName("role")
public class Role implements GrantedAuthority {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String roleName;

    private String roleDesc;

    @JsonIgnore
    @Override
    public String getAuthority() {
        return this.roleName;
    }
}
