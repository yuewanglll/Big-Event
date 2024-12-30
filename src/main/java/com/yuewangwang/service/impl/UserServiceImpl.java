package com.yuewangwang.service.impl;

import com.yuewangwang.mapper.UserMapper;
import com.yuewangwang.pojo.User;
import com.yuewangwang.service.UserService;
import com.yuewangwang.utlis.Md5Util;
import com.yuewangwang.utlis.Result;
import com.yuewangwang.utlis.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserMapper userMapper;


    //根据username查找用户
    @Override
    public User findByUsername(String username) {
        User byUsername = userMapper.findByUsername(username);
        return byUsername;
    }

    //注册用户
    @Override
    public void register(String username, String password) {
        //注册用户前 先将用户密码加密
        String md5String = Md5Util.getMD5String(password);
        //添加
        userMapper.add(username,md5String);
    }


    //修改用户数据
    @Override
    public void updateUser(User user) {
        userMapper.updateUser(user);
    }

    //更改用户头像
    @Override
    public void updateAvatar(String avatarUrl,Integer id) {
        userMapper.updateAvatar(avatarUrl,id);
    }

    //更改用户密码
    @Override
    public void updatePwd(String newpwd) {
        //将密码加密存储
        String md5String = Md5Util.getMD5String(newpwd);

        Map<String,Object> usermap= ThreadLocalUtil.get();
        Integer id = (Integer) usermap.get("id");
        userMapper.updatePwd(md5String,id);
    }
}
