package org.devefx.thymeleaf.resourceresolver;

import org.thymeleaf.Arguments;
import org.thymeleaf.resourceresolver.IResourceResolver;
import org.thymeleaf.templatewriter.ITemplateWriter;

public interface FastmarkerResourceResolver extends IResourceResolver {

    void writeFragment(final String resourceName, final Arguments arguments, ITemplateWriter writer);
    
}
