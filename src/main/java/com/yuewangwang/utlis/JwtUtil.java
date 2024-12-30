package com.yuewangwang.utlis;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;
import java.util.Map;

public class JwtUtil {

    private static final String KEY = "itheima";
	
	//接收业务数据,生成token并返回
    public static String genToken(Map<String, Object> claims) {
        return JWT.create() //创建jwt令牌
                .withClaim("claims", claims) //添加载荷
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 12)) //指定有效时间
                .sign(Algorithm.HMAC256(KEY)); //指定加密签名
    }

	//接收token,验证token,并返回业务数据，将载荷相关的用户信息返回
    public static Map<String, Object> parseToken(String token) {
        return JWT.require(Algorithm.HMAC256(KEY))  //解密jwt，解码不一致会抛出异常
                .build()
                .verify(token) //解密指定的token
                .getClaim("claims") //获取载荷有效信息
                .asMap(); //返回map集合
    }

}
