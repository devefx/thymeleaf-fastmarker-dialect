/*
 * Copyright 2016-2017, Youqian Yue (devefx@163.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.devefx.thymeleaf.spring.boot.autoconfigure;

import org.devefx.thymeleaf.dialect.FastmarkerDialect;
import org.devefx.thymeleaf.spring.boot.autoconfigure.view.FastMarkerThymeleafView;
import org.devefx.thymeleaf.templateresolver.LocalFileFastmarkerTemplateResolver;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

@Configuration
@EnableConfigurationProperties(FastmarkerThymeleafProperties.class)
@ConditionalOnClass(SpringTemplateEngine.class)
@AutoConfigureAfter(ThymeleafAutoConfiguration.class)
public class FastmarkerAutoConfiguration {
    
    @Bean
    @ConditionalOnMissingBean
    public FastmarkerDialect fastmarkerDialect() {
        return new FastmarkerDialect();
    }
    
    @Configuration
    @ConditionalOnBean(ThymeleafViewResolver.class)
    static class ThymeleafViewResolverConfiguration {
        public ThymeleafViewResolverConfiguration(ThymeleafViewResolver resolver) {
            resolver.setViewClass(FastMarkerThymeleafView.class);
        }
    }
    
    @Configuration
    static class DefaultFastmarkerTemplateResolverConfiguration {
        
        @Bean
        @ConditionalOnMissingBean(name = "defaultFastmarkerTemplateResolver")
        public LocalFileFastmarkerTemplateResolver fastmarkerTemplateResolver(FastmarkerThymeleafProperties properties) {
            LocalFileFastmarkerTemplateResolver resolver = new LocalFileFastmarkerTemplateResolver();
            resolver.setPrefix(properties.getPrefix());
            resolver.setSuffix(properties.getSuffix());
            resolver.setTemplateMode(properties.getMode());
            if (properties.getEncoding() != null) {
                resolver.setCharacterEncoding(properties.getEncoding().name());
            }
            Integer order = properties.getTemplateResolverOrder();
            if (order != null) {
                resolver.setOrder(order);
            }
            return resolver;
        }
    }
}
