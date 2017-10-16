package org.devefx.example.service.impl;

import java.util.List;

import org.devefx.example.mapper.ArticleMapper;
import org.devefx.example.model.Article;
import org.devefx.example.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;
    
    @Override
    public Article queryById(int articleId) {
        return articleMapper.queryById(articleId);
    }
    
    @Override
    public List<Article> findRecommends(int articleId) {
        return articleMapper.findRecommends(articleId);
    }
    
    @Override
    public int saveOrUpdate(Article article) {
        return articleMapper.saveOrUpdate(article);
    }

    @Override
    public int remove(Article article) {
        return articleMapper.remove(article);
    }

}
