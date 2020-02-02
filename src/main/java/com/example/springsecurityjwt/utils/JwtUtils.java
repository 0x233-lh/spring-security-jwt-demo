package com.example.springsecurityjwt.utils;

import com.example.springsecurityjwt.entity.Payload;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.joda.time.DateTime;

import java.security.Key;
import java.util.Base64;
import java.util.UUID;

public class JwtUtils {

    private static final String JWT_PAYLOAD_USER_KEY = "user";

    /**
     * 加密算法
     */
    private static Key SIGNING_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    /**
     * 私钥加密token
     *
     * @param userInfo   载荷中的数据
     * @param expire     过期时间，单位分钟
     * @return JWT
     */
    public static String generateTokenExpireInMinutes(Object userInfo, int expire) {

        return Jwts.builder()
                .claim(JWT_PAYLOAD_USER_KEY, JSONUtils.toString(userInfo))
                .setId(createJTI())
                .setExpiration(DateTime.now().plusMinutes(expire).toDate())
                .signWith(SIGNING_KEY)
                .compact();
    }


    private static String createJTI() {
        return new String(Base64.getEncoder().encode(UUID.randomUUID().toString().getBytes()));
    }

    /**
     * 获取token中的载荷信息
     *
     * @param token     用户请求中的令牌
     * @return 用户信息
     */
    public static <T> Payload<T> getInfoFromToken(String token) {
        Jws<Claims> claimsJws = parserToken(token);
        Claims body = claimsJws.getBody();
        Payload<T> claims = new Payload<>();
        claims.setId(body.getId());
        claims.setExpiration(body.getExpiration());
        return claims;
    }

    /**
     * 获取token中的用户信息
     *
     * @param token     用户请求中的令牌
     * @return 用户信息
     */
    public static <T> Payload<T> getInfoFromToken(String token, Class<T> userType) {
        Jws<Claims> claimsJws = parserToken(token);
        Claims body = claimsJws.getBody();
        Payload<T> claims = new Payload<>();
        claims.setId(body.getId());
        claims.setUserInfo(JSONUtils.toBean(body.get(JWT_PAYLOAD_USER_KEY).toString(), userType));
        claims.setExpiration(body.getExpiration());
        return claims;
    }

    /**
     *
     * @param token     用户请求中的token
     * @return Jws<Claims>
     */
    private static Jws<Claims> parserToken(String token) {
        return Jwts.parser().setSigningKey(SIGNING_KEY).parseClaimsJws(token);
    }

}
