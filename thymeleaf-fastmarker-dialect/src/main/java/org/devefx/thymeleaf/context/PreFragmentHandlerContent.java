package org.devefx.thymeleaf.context;

import org.devefx.thymeleaf.PreFragmentHandler;

public interface PreFragmentHandlerContent {

    PreFragmentHandler getPreFragmentHandler(String handlerName);
    
}
