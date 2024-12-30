package com.yuewangwang.interceptors;

import com.yuewangwang.utlis.JwtUtil;
import com.yuewangwang.utlis.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class LoginInterceptors implements HandlerInterceptor {

    //添加redis组件
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取请求头中的 token
        String token = request.getHeader("Authorization");

        //查询所有数据前，先检查token令牌，token令牌不正确会抛出异常
        try {
            //获取redis中的token,redis的token既是值又是键 之要能获取到就说明token没有问题
            ValueOperations<String, String> Operations = stringRedisTemplate.opsForValue();
            String s = Operations.get(token);
            if (s == null) {
                throw  new RuntimeException();
            }
            Map<String, Object> map = JwtUtil.parseToken(token);
            //把业务数据存储到ThreadLocal
            ThreadLocalUtil.set(map);
            return true; //放行
        } catch (Exception e) {
            //http响应码为401
            response.setStatus(401);
            return false; //不放行
        }
    }



    //执行时机：服务端给客户端行响应数据的时候
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //销毁线程局部变量
        ThreadLocalUtil.remove();
    }
}
