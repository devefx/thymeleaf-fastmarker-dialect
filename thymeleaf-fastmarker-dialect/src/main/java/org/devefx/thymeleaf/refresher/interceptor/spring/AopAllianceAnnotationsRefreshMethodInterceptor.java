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

package org.devefx.thymeleaf.refresher.interceptor.spring;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.devefx.thymeleaf.context.PreFragmentHandlerContent;
import org.devefx.thymeleaf.refresher.fragment.FragmentRefresh;
import org.devefx.thymeleaf.refresher.fragment.FragmentRefreshProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.thymeleaf.TemplateEngine;

public class AopAllianceAnnotationsRefreshMethodInterceptor implements
        MethodInterceptor {
    
    private static final Logger logger = LoggerFactory.getLogger(AopAllianceAnnotationsRefreshMethodInterceptor.class);
    
    private final ApplicationContext applicationContext;
    
    private TemplateEngine templateEngine;
    
    private PreFragmentHandlerContent preFragmentHandlerContent;
    
    public AopAllianceAnnotationsRefreshMethodInterceptor(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        // FIXME 
        final Method method = invocation.getMethod();
        
        final Object[] args = invocation.getArguments();
        
        final Object result = invocation.proceed();
        
        final FragmentRefresh fragmentRefresh = FragmentRefreshProcessor.computeFragmentRefreshSpec(
                this.templateEngine, this.preFragmentHandlerContent, method, args, result);
        
        if (fragmentRefresh != null) {
            
            fragmentRefresh.refresh();
        }
        
        return result;
    }
}
