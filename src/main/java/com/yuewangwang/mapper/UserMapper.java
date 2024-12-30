package com.yuewangwang.mapper;

import com.yuewangwang.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Map;

@Mapper
public interface UserMapper {


    //根据用户查询用户
    @Select("select * from user where username=#{username}")
    User findByUsername(String username);


    //添加用户
    @Insert("insert into user(username,password,create_time,update_time)" +
            "values (#{username},#{md5String},now(),now())")
    void add(String username, String md5String);


    //更改用户
    @Update("update user set nickname=#{nickname},email=#{email} ,update_time=#{updateTime} where id=#{id}")
    void updateUser(User user);

    //更改用户头像
    @Update("update user set user_pic=#{avatarUrl},update_time=now() where id=#{id}")
    void updateAvatar(String avatarUrl,Integer id);


    @Update("update user set password=#{md5String} where id=#{id}")
    void updatePwd(String md5String, Integer id);
}
