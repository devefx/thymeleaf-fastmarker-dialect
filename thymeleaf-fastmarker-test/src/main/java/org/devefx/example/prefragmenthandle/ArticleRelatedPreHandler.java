package org.devefx.example.prefragmenthandle;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.devefx.example.model.Article;
import org.devefx.example.service.ArticleService;
import org.devefx.thymeleaf.PreFragmentHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.AbstractContext;
import org.thymeleaf.context.IContext;

@Component("ArticleRelatedPreHandler")
public class ArticleRelatedPreHandler implements PreFragmentHandler {

    final AtomicLong count = new AtomicLong(1);
    
    @Autowired
    private ArticleService articleService;
    
    @Override
    public String getFileNameExpression() {
        return "${execInfo.templateName}/${id}/" + getClass().getSimpleName();
    }

    @Override
    public void handle(Map<String, Object> parameters, IContext context) {
        int articleId = (int) parameters.get("id");
        
        List<Article> articles = articleService.findRecommends(articleId);
        
        AbstractContext ctx = (AbstractContext) context;
        ctx.setVariable("articles", articles);
        
        System.out.println("ArticleRelatedPreHandler执行数据库操作次数：" + (count.getAndIncrement()));
    }

}
