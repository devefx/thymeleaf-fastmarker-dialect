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

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.devefx.thymeleaf.refresher.RefreshFragment;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ObjectUtils;

@SuppressWarnings("unchecked")
public class MethodInterceptorUtils {
    
    private static final Class<? extends Annotation>[] FASK_ANNOTATION_CLASSES = new Class[] {
        RefreshFragment.class
    };
    
    static final String ARGUMENT_PREFIX = "arg";
    
    static final String ARGUMENT_RESULT = "result";

    public static boolean matches(Method method, Class<?> targetClass) {
        for(Class<? extends Annotation> annClass : FASK_ANNOTATION_CLASSES) {
            Annotation a = AnnotationUtils.findAnnotation(method, annClass);
            if (a != null) {
               return true;
            }
         }
        return false;
    }
    
    public static Map<String, Object> extractVariables(Object[] args, Object result) {
        Map<String, Object> variables = new HashMap<String, Object>(args.length + 1);
        
        variables.put(ARGUMENT_RESULT, result);
        
        if (!ObjectUtils.isEmpty(args)) {
            for (int i = 0; i < args.length; i++) {
                variables.put(ARGUMENT_PREFIX + i, args[i]);
            }
        }
        return variables;
    }
}
