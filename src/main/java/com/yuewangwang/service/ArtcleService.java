package com.yuewangwang.service;

import com.yuewangwang.pojo.Article;
import com.yuewangwang.pojo.PageBean;

public interface ArtcleService {

    //添加新闻内容
    void add(Article article);

    //更新文章内容
    void updateArticle(Article article);

    //删除新闻
    void delete(Integer id);


    //分页查询
    PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state);
}
