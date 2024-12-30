package com.yuewangwang.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.autoconfigure.PageHelperAutoConfiguration;
import com.github.pagehelper.autoconfigure.PageHelperProperties;
import com.yuewangwang.mapper.ArtcleMapper;
import com.yuewangwang.pojo.Article;
import com.yuewangwang.pojo.PageBean;
import com.yuewangwang.service.ArtcleService;
import com.yuewangwang.utlis.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class ArtcleServiceImpl implements ArtcleService {

    @Autowired
    private ArtcleMapper artcleMapper;
    private PageHelperAutoConfiguration pageHelperAutoConfiguration;
    @Autowired
    private PageHelperProperties pageHelperProperties;

    //添加新闻内容
    @Override
    public void add(Article article) {
        article.setCreateTime(LocalDateTime.now());
        article.setUpdateTime(LocalDateTime.now());

        //创建人id
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        article.setCreateUser(id);

        artcleMapper.add(article);
    }

    //更新文章内容
    @Override
    public void updateArticle(Article article) {
        article.setUpdateTime(LocalDateTime.now());
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        article.setCreateUser(id);

        artcleMapper.update(article);
    }

    @Override
    public void delete(Integer id) {
        artcleMapper.delete(id);
    }


    //分页查询
    @Override
    public PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state) {
        //创建PageBean对象用于接收分页查询返回的数据
        PageBean<Article> pageBean = new PageBean<>();

        //开启分页查询
        Page<Article> page = PageHelper.startPage(pageNum, pageSize);

        //查询数据的条件是当前登陆人的id
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        //查询结果
        List<Article> list = artcleMapper.list(id, categoryId, state);

        //把查询到的集合转换为 分页的类型
        //Page<Article> p = (Page<Article>) list;

        pageBean.setTotal(page.getTotal());
        pageBean.setList(page.getResult());
        return pageBean;
    }
}
