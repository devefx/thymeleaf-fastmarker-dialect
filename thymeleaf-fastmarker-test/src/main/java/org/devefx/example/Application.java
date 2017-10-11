package org.devefx.example;

import org.devefx.thymeleaf.dialect.FastmarkerDialect;
import org.devefx.thymeleaf.templateresolver.LocalFileFastmarkerTemplateResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {
	
    @Bean
    public FastmarkerDialect fastmarkerDialect() {
    	return new FastmarkerDialect();
    }
    
	@Bean
	public LocalFileFastmarkerTemplateResolver templateResolver() {
		LocalFileFastmarkerTemplateResolver fastmarkerTemplateResolver = new LocalFileFastmarkerTemplateResolver();
		fastmarkerTemplateResolver.setPrefix("F://temp/");
		fastmarkerTemplateResolver.setSuffix(".html");
		return fastmarkerTemplateResolver;
	}
    
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
