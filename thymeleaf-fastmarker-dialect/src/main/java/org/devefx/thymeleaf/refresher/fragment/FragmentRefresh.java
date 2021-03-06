package org.devefx.thymeleaf.refresher.fragment;

import org.devefx.thymeleaf.PreFragmentHandler;
import org.devefx.thymeleaf.resourceresolver.FastmarkerResourceResolver;
import org.devefx.thymeleaf.templateresolver.FastmarkerTemplateResolver;
import org.thymeleaf.Configuration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.TemplateProcessingParameters;
import org.thymeleaf.context.DialectAwareProcessingContext;
import org.thymeleaf.context.IContext;
import org.thymeleaf.context.IProcessingContext;
import org.thymeleaf.exceptions.TemplateProcessingException;
import org.thymeleaf.standard.expression.IStandardExpression;
import org.thymeleaf.standard.expression.IStandardExpressionParser;
import org.thymeleaf.standard.expression.StandardExpressions;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolution;
import org.thymeleaf.util.Validate;

public class FragmentRefresh {
    
    private static final String OPERATOR = "|";
    
    private final TemplateEngine templateEngine;
    
    private final PreFragmentHandler preFragmentHandler;
    
    private final IContext context;
    
    public FragmentRefresh(final PreFragmentHandler preFragmentHandler,
            final TemplateEngine templateEngine, final IContext context) {
        
        Validate.notNull(preFragmentHandler, "preFragmentHandler must con't be null.");
        Validate.notNull(preFragmentHandler, "templateEngine must con't be null.");
        Validate.notNull(context, "context must con't be null.");
        
        this.preFragmentHandler = preFragmentHandler;
        this.templateEngine = templateEngine;
        this.context = context;
    }
    
    public void refresh() {
        final Configuration configuration = this.templateEngine.getConfiguration();
        
        final IProcessingContext processingContext = 
                new DialectAwareProcessingContext(this.context, configuration.getDialectSet());
        
        final String fileNameExpresion = OPERATOR + this.preFragmentHandler.getFileNameExpression() + OPERATOR;
        final IStandardExpressionParser expressionParser = StandardExpressions.getExpressionParser(configuration);
        final IStandardExpression expression = expressionParser.parseExpression(configuration, processingContext, fileNameExpresion);
        
        final Object templateNameObject = expression.execute(configuration, processingContext);
        final String templateName = templateNameObject.toString();
        
        final TemplateProcessingParameters templateProcessingParameters = 
                new TemplateProcessingParameters(configuration, templateName, processingContext);
        
        TemplateResolution templateResolution = null;
        
        for (final ITemplateResolver templateResolver : configuration.getTemplateResolvers()) {
            
            if (templateResolver instanceof FastmarkerTemplateResolver) {
                
                templateResolution = templateResolver.resolveTemplate(templateProcessingParameters);
                
                if (templateResolution != null) {
                    
                    final FastmarkerResourceResolver resourceResolver =
                            (FastmarkerResourceResolver) templateResolution.getResourceResolver();
                    
                    final String resourceName = templateResolution.getResourceName();
                    
                    boolean deleted = resourceResolver.deleteFragment(resourceName);
                    
                    if (!deleted) {
                        // FIXME
                        
                    }
                }
                
            }
            
        }
        
        if (templateResolution == null) {
            throw new TemplateProcessingException(
                    "Error resolving template \"" + templateProcessingParameters.getTemplateName() + "\", " +
                    "template might not exist or might not be accessible by " +
                    "any of the configured Template Resolvers");
        }
        
    }
    
}
