package com.yuewangwang.service;

import com.yuewangwang.pojo.User;
import com.yuewangwang.utlis.Result;

import java.util.Map;

public interface UserService {

    //根据usernam查询寻user数据
    User findByUsername(String username);

    //注册user
    void register(String username, String password);


    //更改用户
    void updateUser(User user);


    //更改用户头像
    void updateAvatar(String avatarUrl,Integer id);

    //更改用户密码
    void updatePwd(String newpwd);
}
