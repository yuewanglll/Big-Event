package com.yuewangwang.service.impl;

import com.yuewangwang.mapper.CategoryMapper;
import com.yuewangwang.pojo.Category;
import com.yuewangwang.service.CategoryService;
import com.yuewangwang.utlis.ThreadLocalUtil;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    //新增文章分类
    @Override
    public void addCategory(Category category) {
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());

        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        //添加操作人的id
        category.setCreateUser(id);

        categoryMapper.addCategory(category);
    }


    //该接口用于获取当前已经登陆账户创建的说要文章分类
    @Override
    public List<Category> findList() {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        List<Category> list = categoryMapper.findList(id);
        return list;
    }


    //更新文章分类
    @Override
    public void updateCategory(Category category) {

        category.setUpdateTime(LocalDateTime.now());

        categoryMapper.updateCategory(category);
    }


    //获取文章分类详情
    @Override
    public Category dateil(Integer id) {
       Category category= categoryMapper.dateil(id);
       return category;
    }

    @Override
    public void deleteCategory(Integer id) {
        categoryMapper.deleteCategory(id);
    }
}
