package org.devefx.example.service;

import java.util.List;

import org.devefx.example.model.Article;

public interface ArticleService {

    Article queryById(int articleId);
    
    List<Article> findRecommends(int articleId);
    
    int saveOrUpdate(Article article);
    
    int remove(Article article);
    
}
