package org.devefx.thymeleaf;

import java.util.Map;

import org.thymeleaf.context.IContext;

public interface PreFragmentHandler {
    
    String getFileNameExpression();

    void handle(Map<String, Object> parameters, IContext context);
    
}
