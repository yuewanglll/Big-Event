package com.yuewangwang;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class bigeventtest {


    @Test
    public void testGen() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 1);
        claims.put("name", "test");

        //生成JWT的代码
        String token = JWT.create()//创建JWT
                .withClaim("user", claims) //添加载荷
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60))//添加过期时间
                .sign(Algorithm.HMAC256("czjtyww"));//指定算法，配置密钥
        System.out.println(token);
    }


    @Test
    public void testDecode() {
        //定义字符串，模拟用户传入过来的token
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9" +
                ".eyJ1c2VyIjp7Im5hbWUiOiJ0ZXN0IiwiaWQiOjF9LCJleHAiOjE3MzQ3MDY3NTB9" +
                ".wjlKorVIpXRQTXmsGTxD1Wb5N0x1sYS0iBn9tPJ0QBg";

        //解码jwt
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("czjtyww")).build();

        DecodedJWT decodedJWT = jwtVerifier.verify(token); //验证token，生成一个解析后的jwt对象
        Map<String, Claim> claims = decodedJWT.getClaims();//获取载荷
        String payload = decodedJWT.getPayload();//
        System.out.println(payload);//获取载荷加密信息


    }


}
