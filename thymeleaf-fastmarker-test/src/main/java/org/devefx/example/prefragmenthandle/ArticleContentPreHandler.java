package org.devefx.example.prefragmenthandle;

import java.util.Calendar;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.devefx.example.model.Article;
import org.devefx.thymeleaf.PreFragmentHandler;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.AbstractContext;
import org.thymeleaf.context.IContext;

@Component("ArticleContentPreHandler")
public class ArticleContentPreHandler implements PreFragmentHandler {

	final AtomicLong count = new AtomicLong(1);
	
	@Override
	public String getFileNameExpression() {
		return "${execInfo.templateName}/${id}/" + getClass().getSimpleName();
	}
	
	@Override
	public void handle(Map<String, Object> parameters, IContext context) {
		// TODO Auto-generated method stub

		// 假设数据是从数据库查询出来的
		int articleId = 1;
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(2017, 10, 2, 21, 28);
		Article article = new Article();
		article.setId(articleId);
		article.setTitle("艾媒咨询：2017上半年中国手机安全市场研究报告");
		article.setTime(calendar.getTime());
		article.setAuthor("佚名");
		
		AbstractContext ctx = (AbstractContext) context;
		ctx.setVariable("article", article);
		
		System.out.println("执行数据库操作次数：" + (count.getAndIncrement()));
	}

}
