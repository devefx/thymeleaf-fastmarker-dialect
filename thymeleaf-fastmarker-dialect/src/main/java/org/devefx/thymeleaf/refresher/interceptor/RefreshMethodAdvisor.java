package org.devefx.thymeleaf.refresher.interceptor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.thymeleaf.TemplateEngine;

import net.sf.cglib.proxy.Enhancer;

@SuppressWarnings("rawtypes")
public class RefreshMethodAdvisor {
    
    private final Map<Class, Object> proxyObjectCache 
        = new ConcurrentHashMap<Class, Object>(256);

    private TemplateEngine templateEngine;
    
    public void setTemplateEngine(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }
    
    @SuppressWarnings("unchecked")
    public<T> T getInstance(Class<T> type) {
        
        if (type == null) {
            return null;
        }
        
        T result = (T) proxyObjectCache.get(type);
        
        if (result == null) {
            
            RefreshMethodInterceptor interceptor = new RefreshMethodInterceptor(
                    this.templateEngine);
            
            result = (T) Enhancer.create(type, interceptor);
            
            if (result != null) {
                proxyObjectCache.put(type, result);
            }
        }
        
        return result;
    }

}
