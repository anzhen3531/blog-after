package com.anzhen.utils.jwt;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.experimental.UtilityClass;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Classname : JwtUtils
 * @Date : 2021.4.27 20:15
 * @Created : anzhen
 * @Description :
 * @目的: 使用jwt + token 进行前后端交互
 */
@UtilityClass
public class JwtUtils {


    /**
     * 生成token
     *
     * @param secret
     * @return java.lang.String
     */
    public String createToken(Map<String, Object> claimMap,String secret) {
        return generateToken(claimMap, secret);
    }

    /**
     * 生成token
     *
     * @param claimMap
     * @return java.lang.String
     */
    public String generateToken(Map<String, Object> claimMap, String secret) {
        return Jwts.builder()
//                .setId(UUID.randomUUID().toString())
                // 设置签发时间
//                .setIssuedAt(new Date(System.currentTimeMillis()))
                // 设置过期时间
//                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                //设置主体内容
                .addClaims(claimMap)
                //设置签名算法
                .signWith(generateKey(secret))
                .compact();
    }

    /**
     * 校验token
     *
     * @param token
     * @return java.lang.Boolean
     */
    public Boolean validateToken(String token, String secret) {
        try {
            Jwts.parser().setSigningKey(generateKey(secret)).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 解析token
     *
     * @param token
     * @return Map<Object>
     */
    public Map<String, Object> parseToken(String token, String secret) {
        // 得到DefaultJwtParser
        return Jwts.parser()
                // 设置签名密钥
                .setSigningKey(generateKey(secret))
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 刷新token(通过旧token换取新的token)
     *
     * @param
     * @return String
     */
    public String refreshToken(String token, String secret){
        Map<String,Object> claims = parseToken(token, secret);
        Map<String,Object> newClaims = new HashMap<>();
        newClaims.put("username",claims.get("username"));
        newClaims.put("created", new Date());

        return Jwts.builder()
//                .setId(UUID.randomUUID().toString())
                // 设置签发时间
//                .setIssuedAt(new Date(System.currentTimeMillis()))
                // 设置过期时间
//                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                //设置主体内容
                .addClaims(newClaims)
                //设置签名算法
                .signWith(generateKey(secret))
                .compact();
    }

    /**
     * 生成安全密钥
     *
     * @return
     */
    public Key generateKey(String secret) {
        return new SecretKeySpec(secret.getBytes(), SignatureAlgorithm.HS256.getJcaName());
    }

}
