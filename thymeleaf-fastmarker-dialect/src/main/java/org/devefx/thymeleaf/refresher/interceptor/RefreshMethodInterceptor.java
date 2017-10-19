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

package org.devefx.thymeleaf.refresher.interceptor;

import java.lang.reflect.Method;

import org.devefx.thymeleaf.context.PreFragmentHandlerContent;
import org.devefx.thymeleaf.refresher.fragment.FragmentRefresh;
import org.devefx.thymeleaf.refresher.fragment.FragmentRefreshProcessor;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.util.Validate;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class RefreshMethodInterceptor implements MethodInterceptor {

    private final TemplateEngine templateEngine;
    
    private PreFragmentHandlerContent preFragmentHandlerContent;
    
    public RefreshMethodInterceptor(TemplateEngine templateEngine) {
        Validate.notNull(templateEngine, "templateEngine must con't be null.");
        this.templateEngine = templateEngine;
    }
    
    @Override
    public Object intercept(Object object, Method method, Object[] args,
            MethodProxy proxy) throws Throwable {
        
        final Object result = proxy.invokeSuper(object, args);
        
        final FragmentRefresh fragmentRefresh = FragmentRefreshProcessor.computeFragmentRefreshSpec(
                this.templateEngine, this.preFragmentHandlerContent, method, args, result);
        
        if (fragmentRefresh != null) {
            
            fragmentRefresh.refresh();
            
        }
        return result;
    }
    
}
