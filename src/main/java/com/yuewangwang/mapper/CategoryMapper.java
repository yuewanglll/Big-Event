package com.yuewangwang.mapper;

import com.yuewangwang.pojo.Category;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

@Mapper
public interface CategoryMapper {

    //该接口用于获取当前已经登陆账户创建的说要文章分类



    //新增文章分类
    @Insert("insert into category(category_name,category_alias,create_user,create_time,update_time)" +
            "values (#{categoryName},#{categoryAlias},#{createUser},#{createTime},#{updateTime})")
    void addCategory(Category category);


    @Select("select * from category where create_user=#{id}")
    List<Category> findList(Integer id);

    //更新文章分类
    @Update("update category set category_name=#{categoryName},category_alias=#{categoryAlias} ,update_time=#{updateTime} where id=#{id}")
    void updateCategory(Category category);

    //获取文章分类详情
    @Select("select * from category where id=#{id}")
    Category dateil(Integer id);


    //删除文章分类
    @Delete("delete from category where id=#{id}")
    void deleteCategory(Integer id);
}
