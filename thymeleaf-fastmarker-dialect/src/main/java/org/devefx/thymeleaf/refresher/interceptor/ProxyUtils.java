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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;



import net.sf.cglib.proxy.Enhancer;

@SuppressWarnings("rawtypes")
public final class ProxyUtils {
    
    private static final Map<Class, Object> proxyObjectCache =
            new ConcurrentHashMap<Class, Object>(256);
    
    @SuppressWarnings("unchecked")
    public static<T> T getInstance(Class<T> type) {
        if (type == null) {
            return null;
        }
        
        T result = (T) proxyObjectCache.get(type);
        
        if (result == null) {
            
            RefreshMethodInterceptor interceptor = new RefreshMethodInterceptor();
            
            result = (T) Enhancer.create(type, interceptor);
            
            if (result != null) {
                proxyObjectCache.put(type, result);
            }
        }
        return result;
    }
    
    private ProxyUtils() {
    }
}
