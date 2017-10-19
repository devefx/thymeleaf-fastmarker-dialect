package org.devefx.thymeleaf.refresher.fragment;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.devefx.thymeleaf.PreFragmentHandler;
import org.devefx.thymeleaf.context.PreFragmentHandlerContent;
import org.devefx.thymeleaf.refresher.RefreshFragment;
import org.devefx.thymeleaf.refresher.RefreshFragment.Argument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ObjectUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import ognl.Ognl;
import ognl.OgnlException;

public final class FragmentRefreshProcessor {
    
    private static final Logger logger = LoggerFactory.getLogger(FragmentRefreshProcessor.class);
    
    static final String ARGUMENT_PREFIX = "arg";
    
    static final String ARGUMENT_RESULT = "result";

    public static FragmentRefresh computeFragmentRefreshSpec(final TemplateEngine templateEngine, final PreFragmentHandlerContent preFragmentHandlerContent,
            final Method method, final Object[] args, final Object result) {
        
        RefreshFragment annotation = AnnotationUtils.findAnnotation(method, RefreshFragment.class);
        
        if (annotation != null) {
            
            String handlerName = annotation.name();
            
            PreFragmentHandler preFragmentHandler = preFragmentHandlerContent.getPreFragmentHandler(handlerName);
            
            Context context = new Context();
            
            Map<String, Object> variables = extractVariables(args, result);
            
            for (Argument argument : annotation.arguments()) {
                
                String expression = argument.value();
                
                try {
                    
                    Object value = Ognl.getValue(expression, variables);
                    
                    String name = argument.name();
                    
                    context.setVariable(name, value);
                    
                } catch (OgnlException e) {
                    
                    logger.error("can't eavl expression: " + expression, e);
                    
                    return null;
                }
                
            }
            
            FragmentRefresh fragmentRefresh = new FragmentRefresh(preFragmentHandler,
                    templateEngine, context);
            
            return fragmentRefresh;
        }
        return null;
    }
    
    static Map<String, Object> extractVariables(Object[] args, Object result) {
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
