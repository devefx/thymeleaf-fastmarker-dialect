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

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.devefx.thymeleaf.refresher.RefreshFragment;
import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;

@SuppressWarnings({"serial", "unchecked"})
public class RefreshMethodSourceAdvisor extends StaticMethodMatcherPointcutAdvisor implements ApplicationContextAware {

    private static final Class<? extends Annotation>[] FASK_ANNOTATION_CLASSES = new Class[] {
        RefreshFragment.class
    };
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
         throws BeansException {
       setAdvice(new AopAllianceAnnotationsRefreshMethodInterceptor(applicationContext));
    }
   
    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        for(Class<? extends Annotation> annClass : FASK_ANNOTATION_CLASSES) {
            Annotation a = AnnotationUtils.findAnnotation(method, annClass);
            if (a != null) {
                return true;
            }
        }
        return false;
    }

}
