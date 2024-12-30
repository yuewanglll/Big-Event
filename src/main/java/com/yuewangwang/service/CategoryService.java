package com.yuewangwang.service;

import com.yuewangwang.pojo.Category;

import java.util.List;

public interface CategoryService {

    //该接口用于获取当前已经登陆账户创建的说要文章分类
    List<Category> findList();


    //新增文章分类
    void addCategory(Category category);

    //更新文章分类
    void updateCategory(Category category);

    //获取文章分类详情
    Category dateil(Integer id);

    //删除文章分类
    void deleteCategory(Integer id);
}
