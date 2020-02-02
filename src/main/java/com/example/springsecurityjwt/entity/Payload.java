package com.example.springsecurityjwt.entity;


import lombok.Data;

import java.util.Date;

/**
 * JWT的载荷信息
 */
@Data
public class Payload<T> {

    private String id;
    private T userInfo;
    private Date expiration;

}
