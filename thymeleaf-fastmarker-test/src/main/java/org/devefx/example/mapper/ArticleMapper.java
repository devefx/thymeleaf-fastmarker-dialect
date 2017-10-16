package org.devefx.example.mapper;

import java.util.List;

import org.devefx.example.model.Article;
import org.devefx.thymeleaf.refresher.RefreshFragment;
import org.devefx.thymeleaf.refresher.RefreshFragment.Argument;

public interface ArticleMapper {
    
    Article queryById(int articleId);
    
    List<Article> findRecommends(int articleId);
    
    @RefreshFragment(name = "ArticleContentPreHandler", arguments = {
        @Argument(name = "id", value = "arg0.id")
    })
    int saveOrUpdate(Article article);
    
    int remove(Article article);
    
}
