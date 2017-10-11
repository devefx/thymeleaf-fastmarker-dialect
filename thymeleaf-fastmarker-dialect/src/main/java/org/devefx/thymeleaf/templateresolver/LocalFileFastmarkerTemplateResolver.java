package org.devefx.thymeleaf.templateresolver;

import org.devefx.thymeleaf.resourceresolver.LocalFileFastmarkerResourceResolver;
import org.thymeleaf.exceptions.ConfigurationException;
import org.thymeleaf.resourceresolver.IResourceResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

public class LocalFileFastmarkerTemplateResolver extends TemplateResolver {
    
    public LocalFileFastmarkerTemplateResolver() {
        super();
        super.setResourceResolver(new LocalFileFastmarkerResourceResolver());
    }
    
    @Override
    public void setResourceResolver(final IResourceResolver resourceResolver) {
        throw new ConfigurationException(
                "Cannot set a resource resolver on " + this.getClass().getName() + ". If " +
                "you want to set your own resource resolver, use " + TemplateResolver.class.getName() + 
                "instead");
    }
}
