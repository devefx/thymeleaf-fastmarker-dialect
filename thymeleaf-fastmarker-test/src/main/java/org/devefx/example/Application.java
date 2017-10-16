package org.devefx.example;

import org.devefx.thymeleaf.refresher.interceptor.spring.RefreshMethodSourceAdvisor;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {
    
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }
    
    @Bean
    public RefreshMethodSourceAdvisor fastmarkerSourceAdvisor() {
        return new RefreshMethodSourceAdvisor();
    }
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
