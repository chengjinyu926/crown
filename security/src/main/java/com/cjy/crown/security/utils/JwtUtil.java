package com.cjy.crown.security.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Map;

/**
 * @author ：JinYu
 * @date ：Created in 2022/4/9 13:19
 * @description：
 */
public class JwtUtil {
    private static String secret = "cjy_crown";

    public static String createToken(Map<String, Object> claims) {
        return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, secret).compact();
    }
    public static Claims parseToken(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }
    public static String getUserId(String token){
       return parseToken(token).get("user_id",String.class);
    }
}
