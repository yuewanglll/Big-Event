package com.yuewangwang.controller;

import com.yuewangwang.pojo.Category;
import com.yuewangwang.service.CategoryService;
import com.yuewangwang.utlis.Result;
import com.yuewangwang.utlis.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    //该接口用于获取当前已经登陆账户创建的说要文章分类
    @GetMapping("categoryDemo")
    public Result<List<Category>> category() {

    List<Category> categories= categoryService.findList();
        return Result.success(categories);
    }

    //新增文章分类
    @PostMapping
    public Result addCategory(@RequestBody @Validated(Category.Add.class) Category category) { //因为请求是json格式所以用请求体接收
        categoryService.addCategory(category);
        return Result.success();
    }

    //更新文章分类
    @PutMapping
    public Result updateCategory(@RequestBody @Validated(Category.Update.class) Category category) {

        categoryService.updateCategory(category);
        return Result.success();

    }

    //获取文章分类详情
    @GetMapping("dateil")
    public Result<Category> dateil(Integer id) {
      Category category= categoryService.dateil(id);
        return Result.success(category);
    }


    //删除文章分类
    @DeleteMapping
    public Result deleteCategory(Integer id) {
        categoryService.deleteCategory(id);
        return Result.success();
    }

}
