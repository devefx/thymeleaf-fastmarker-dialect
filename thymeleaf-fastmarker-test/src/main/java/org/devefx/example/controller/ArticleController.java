package org.devefx.example.controller;

import java.util.concurrent.atomic.AtomicLong;

import org.devefx.example.model.Article;
import org.devefx.example.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/article")
public class ArticleController {
    
    @Autowired
    private ArticleService articleService;
    
    final AtomicLong count = new AtomicLong(1);
    
    @RequestMapping("/{id:\\d+}")
    public ModelAndView index(@PathVariable("id") int id) {
        ModelAndView view = new ModelAndView("article");
        view.addObject("id", id);
        
        System.out.println("访问article页面次数：" + count.getAndIncrement());
        return view;
    }
    
    @ResponseBody
    @RequestMapping("/update/{id:\\d+}")
    public String update(@PathVariable("id") int id) {
        Article article = articleService.queryById(id);
        
        articleService.saveOrUpdate(article);
        return "success";
    }
    
}
