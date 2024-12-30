package com.yuewangwang.controller;

import com.yuewangwang.pojo.User;
import com.yuewangwang.service.UserService;
import com.yuewangwang.utlis.JwtUtil;
import com.yuewangwang.utlis.Md5Util;
import com.yuewangwang.utlis.Result;
import com.yuewangwang.utlis.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Validated //添加参数校验注解
@RestController //集成了@ResponseBody 返回数据是json格式
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate redis;

    /**
     * 用户注册
     *
     * @param username
     * @param password
     * @return
     */
    @PostMapping("register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password) {
        //查询用户是否存在
        User user = userService.findByUsername(username);
        if (user == null) {
            //用户为空不存在，可以注册
            userService.register(username, password);
            return Result.success();
        } else {
            //用户存在
            return Result.error("该用户已经存在");
        }
    }

    /**
     * 用户登录
     *
     * @param username
     * @param password
     * @return
     */
    @PostMapping("login")
    public Result<String> login(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password) {
        //判断用户是否存在
        User user = userService.findByUsername(username);
        if (user == null) {
            return Result.error("用户不存在");
        }
        //用户存在判断密码是否正确
        if (Md5Util.getMD5String(password).equals(user.getPassword())) {
            //登陆成功，返回给客户端tokenm,将用户的id、username添加到token的载荷
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", user.getId());
            claims.put("username", user.getUsername());
            String token = JwtUtil.genToken(claims);

            //将token放到reids
            ValueOperations<String, String> Operations = redis.opsForValue();
            //token既是键又是值，方便从前端获取到token后直接获取值，没有值就是token失效
            Operations.set(token,token);

            return Result.success(token);
        }
        return Result.error("用户密码错误");
    }


    @GetMapping("userInfo")
    public Result<User> getUserInfo(@RequestHeader(name = "Authorization") String token) {
        //解析token
        //Map<String, Object> map = JwtUtil.parseToken(token);

        //获取到token中的用户名
        // String username = (String) map.get("username");

        //从线程局部变量中拿到上述数据
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        //根据用户名查找用户数据
        User user = userService.findByUsername(username);
        //用户数据返回
        return Result.success(user);
    }


    @PutMapping("update")
    public Result updateUserInfo(@RequestBody @Validated User user) {
        //更改修改时间
        user.setUpdateTime(LocalDateTime.now());

        userService.updateUser(user);
        return Result.success();
    }


    @PatchMapping("updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl) {

        //拿到用户的id
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");

        userService.updateAvatar(avatarUrl, id);
        return Result.success();
    }

    @PatchMapping("updatePwd")
    public Result updatePwd(@RequestBody Map<String, String> params, @RequestHeader(name = "Authorization") String token) {
        //获取用户出入的新旧密码
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");

        //判断用户输入的是否为空
        if (!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(rePwd)) {
            //有一个为空
            return Result.error("缺少必要参数");
        }
        //用户输入正确，判断旧密码是否输入正确

        //先获取用户信息
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User byUsername = userService.findByUsername(username);

        if (!byUsername.getPassword().equals(Md5Util.getMD5String(oldPwd))) {
            return Result.error("用户密码输入不正确");
        }

        if (!newPwd.equals(rePwd)) {
            return Result.error("输入两次密码不一致");
        }

        userService.updatePwd(newPwd);
        //更改完密码先将旧token删除
        ValueOperations<String, String> stringStringValueOperations = redis.opsForValue();
        stringStringValueOperations.getOperations().delete(token);
        return Result.success();

    }

}
