package org.devefx.example.view;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

@Configuration
@ConditionalOnClass(SpringTemplateEngine.class)
@AutoConfigureAfter(ThymeleafAutoConfiguration.class)
public class ThymeleafConfiguration {
	
	public ThymeleafConfiguration(ThymeleafViewResolver resolver) {
		resolver.setViewClass(ThymeleafView.class);
	}
	
}
