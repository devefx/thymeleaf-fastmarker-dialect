package org.devefx.example.mapper.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.devefx.example.mapper.ArticleMapper;
import org.devefx.example.model.Article;
import org.springframework.stereotype.Component;

@Component
public class ArticleMapperImpl implements ArticleMapper {

    @Override
    public Article queryById(int articleId) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2017, 10, 2, 21, 28);
        Article article = new Article();
        article.setId(articleId);
        article.setTitle("艾媒咨询：2017上半年中国手机安全市场研究报告");
        article.setTime(calendar.getTime());
        article.setAuthor("佚名");
        return article;
    }

    @Override
    public List<Article> findRecommends(int articleId) {
        List<Article> articles = new ArrayList<>();
        articles.add(queryById(1));
        return articles;
    }

    
    @Override
    public int saveOrUpdate(Article article) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int remove(Article article) {
        // TODO Auto-generated method stub
        return 0;
    }
    
}
