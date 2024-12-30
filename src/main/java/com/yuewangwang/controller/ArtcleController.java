package com.yuewangwang.controller;


import com.yuewangwang.pojo.Article;
import com.yuewangwang.pojo.PageBean;
import com.yuewangwang.service.ArtcleService;
import com.yuewangwang.utlis.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("article")
public class ArtcleController {

    @Autowired
    private ArtcleService artcleService;

    //添加新闻内容
    @PostMapping
    public Result add(@RequestBody @Validated Article article) {
        artcleService.add(article);
        return Result.success();
    }

    //更新新闻的内容
    @PutMapping
    public Result update(@RequestBody Article article) {
        artcleService.updateArticle(article);
        return Result.success();
    }


    //删除新闻内容
    @DeleteMapping
    public Result delete(Integer id) {
        artcleService.delete(id);
        return Result.success();
    }

    //分页查询
    @GetMapping
    public Result<PageBean<Article>> list(
            Integer pageNum, //当前页码
            Integer pageSize, //每页条数
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) String state
    ) {
        PageBean<Article> list = artcleService.list(pageNum, pageSize, categoryId, state);
        return Result.success(list);
    }

}
