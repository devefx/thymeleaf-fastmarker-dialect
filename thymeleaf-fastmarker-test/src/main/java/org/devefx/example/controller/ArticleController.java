package org.devefx.example.controller;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/article")
public class ArticleController {
	
	final AtomicLong count = new AtomicLong(1);
	
	@RequestMapping("/{id:\\d+}")
	public ModelAndView index(@PathVariable("id") int id) {
		ModelAndView view = new ModelAndView("article");
		view.addObject("id", id);
		
		System.out.println("访问article页面次数：" + count.getAndIncrement());
		return view;
	}
}
