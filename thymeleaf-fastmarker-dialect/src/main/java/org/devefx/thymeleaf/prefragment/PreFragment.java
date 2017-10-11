package org.devefx.thymeleaf.prefragment;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.devefx.thymeleaf.PreFragmentHandler;
import org.devefx.thymeleaf.context.PreFragmentHandlerContent;
import org.devefx.thymeleaf.resourceresolver.FastmarkerResourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.Arguments;
import org.thymeleaf.Configuration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.TemplateProcessingParameters;
import org.thymeleaf.TemplateRepository;
import org.thymeleaf.context.IContext;
import org.thymeleaf.context.IProcessingContext;
import org.thymeleaf.dom.Document;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.Node;
import org.thymeleaf.exceptions.TemplateInputException;
import org.thymeleaf.resourceresolver.IResourceResolver;
import org.thymeleaf.standard.expression.IStandardExpression;
import org.thymeleaf.standard.expression.IStandardExpressionParser;
import org.thymeleaf.standard.expression.StandardExpressions;
import org.thymeleaf.templatemode.ITemplateModeHandler;
import org.thymeleaf.templateparser.ITemplateParser;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolution;
import org.thymeleaf.templatewriter.ITemplateWriter;
import org.thymeleaf.util.StringUtils;
import org.thymeleaf.util.Validate;

public final class PreFragment {
    
    private static final Logger logger = LoggerFactory.getLogger(PreFragment.class);
    
    private static final String OPERATOR = "|";
    
    private final String handlerName;
    private final Map<String, Object> parameters;
    private final Element fragmentElement;
    
    public PreFragment(final String handlerName,
            final Map<String, Object> parameters,
            final Element fragmentElement) {
        Validate.notNull(fragmentElement, "Fragment element cannot be null or empty");
        this.handlerName = handlerName;
        this.parameters = parameters;
        this.fragmentElement = fragmentElement;
    }

    public String getHandlerName() {
        return handlerName;
    }
    
    public Map<String, Object> getParameters() {
        return parameters;
    }
    
    public Element getFragmentElement() {
        return fragmentElement;
    }
    
    public List<Node> extractFragment(
            final Configuration configuration, final IProcessingContext context,
            final TemplateRepository templateRepository, final TemplateEngine engine) {
        
        final PreFragmentHandlerContent preFragmentHandlerContent = (PreFragmentHandlerContent) context.getContext();
        final PreFragmentHandler preFragmentHandler = preFragmentHandlerContent.getPreFragmentHandler(this.handlerName);
        
        final String fileNameExpresion = OPERATOR + preFragmentHandler.getFileNameExpression() + OPERATOR;
        final IStandardExpressionParser expressionParser = StandardExpressions.getExpressionParser(configuration);
        final IStandardExpression expression = expressionParser.parseExpression(configuration, context, fileNameExpresion);
        
        final Object templateNameObject = expression.execute(configuration, context);
        final String templateName = templateNameObject.toString();
        
        final TemplateProcessingParameters templateProcessingParameters = 
                new TemplateProcessingParameters(configuration, templateName, context);
        
        final Document document = loadFragmentDocument(configuration, 
                templateProcessingParameters, templateRepository, engine, preFragmentHandler);
        
        return document.getChildren();
    }
    
    protected Document loadFragmentDocument(final Configuration configuration, final TemplateProcessingParameters templateProcessingParameters,
            final TemplateRepository templateRepository, final TemplateEngine engine, final PreFragmentHandler preFragmentHandler) {
        
        final Map<String,ITemplateParser> parsersByTemplateMode = new HashMap<String,ITemplateParser>(10, 1.0f);
        for (final ITemplateModeHandler handler : configuration.getTemplateModeHandlers()) {
            parsersByTemplateMode.put(handler.getTemplateModeName(), handler.getTemplateParser());
        }
        
        final String templateName = templateProcessingParameters.getTemplateName();
        final IContext context = templateProcessingParameters.getContext();
        
        TemplateResolution templateResolution = null;
        InputStream templateInputStream = null;
        Arguments arguments = null;
        
        for (final ITemplateResolver templateResolver : configuration.getTemplateResolvers()) {
            
            templateResolution = templateResolver.resolveTemplate(templateProcessingParameters);
            
            if (templateResolution != null) {
                
                final IResourceResolver resourceResolver = templateResolution.getResourceResolver();
                
                if (resourceResolver instanceof FastmarkerResourceResolver) {
                    
                    final FastmarkerResourceResolver resolver = (FastmarkerResourceResolver) resourceResolver;
                    
                    final String resourceName = templateResolution.getResourceName();
                    
                    if (logger.isTraceEnabled()) {
                        logger.trace("[THYMELEAF][{}] Trying to resolve template \"{}\" as resource \"{}\" with resource resolver \"{}\"", new Object[] {TemplateEngine.threadIndex(), templateName, resourceName, resourceResolver.getName()});
                    }
                    
                    templateInputStream = resolver.getResourceAsStream(templateProcessingParameters, resourceName);
                    
                    // 预构建片段↓↓↓↓↓
                    if (templateInputStream == null) {
                        
                        if (logger.isTraceEnabled()) {
                            logger.trace("[THYMELEAF][{}] Template \"{}\" could not be resolved as resource \"{}\" with resource resolver \"{}\"", new Object[] {TemplateEngine.threadIndex(), templateName, resourceName, resourceResolver.getName()});
                        }
                        
                        if (arguments == null) {
                            
                            preFragmentHandler.handle(this.parameters, context);
                            
                            Document fragmentDocument = new Document();
                            Node cloneNode = this.fragmentElement.cloneNode(null, false);
                            fragmentDocument.addChild(cloneNode);
                            fragmentDocument.precompute(configuration);
                            
                            arguments = new Arguments(engine, templateProcessingParameters, templateResolution, 
                                    templateRepository, fragmentDocument);
                            
                            fragmentDocument.process(arguments);
                        }
                        
                        final String templateMode = templateResolution.getTemplateMode();
                        final ITemplateModeHandler templateModeHandler =
                                configuration.getTemplateModeHandler(templateMode);
                        final ITemplateWriter templateWriter = templateModeHandler.getTemplateWriter();
                        
                        resolver.writeFragment(resourceName, arguments, templateWriter);
                        
                        templateInputStream = resolver.getResourceAsStream(templateProcessingParameters, resourceName);
                    }
                    // 预构建片段↑↑↑↑↑↑
                    
                    if (templateInputStream != null) {
                        if (logger.isDebugEnabled()) {
                            logger.debug("[THYMELEAF][{}] Template \"{}\" was correctly resolved as resource \"{}\" in mode {} with resource resolver \"{}\"", new Object[] {TemplateEngine.threadIndex(), templateName, resourceName, templateResolution.getTemplateMode(), resourceResolver.getName()});
                        }
                        break;
                    }
                    
                } else {
                    
                    templateResolution = null;
                }
                
            } else {
                if (logger.isTraceEnabled()) {
                    logger.trace("[THYMELEAF][{}] Skipping template resolver \"{}\" for template \"{}\"", new Object[] {TemplateEngine.threadIndex(), templateResolver.getName(), templateName});
                }
            }
        }
        
        if (templateResolution == null || templateInputStream == null) {
            throw new TemplateInputException(
                    "Error resolving template \"" + templateProcessingParameters.getTemplateName() + "\", " +
                    "template might not exist or might not be accessible by " +
                    "any of the configured Template Resolvers");
        }
        
        final String templateMode = templateResolution.getTemplateMode();
        
        final ITemplateParser templateParser = parsersByTemplateMode.get(templateMode);
        if (templateParser == null) {
            throw new TemplateInputException(
                    "Template mode \"" + templateMode + "\" has not been configured");
        }
        
        if (logger.isTraceEnabled()) {
            logger.trace("[THYMELEAF][{}] Starting parsing of template \"{}\"", TemplateEngine.threadIndex(), templateName);
        }
        
        final String characterEncoding = templateResolution.getCharacterEncoding();
        Reader reader = null;
        if (!StringUtils.isEmptyOrWhitespace(characterEncoding)) {
            try {
                reader = new InputStreamReader(templateInputStream, characterEncoding);
            } catch (final UnsupportedEncodingException e) {
                throw new TemplateInputException("Exception parsing document", e);
            }
        } else {
            reader = new InputStreamReader(templateInputStream);
        }
        
        final Document document = 
                templateParser.parseTemplate(configuration, templateName, reader);
        
        if (logger.isTraceEnabled()) {
            logger.trace("[THYMELEAF][{}] Finished parsing of template \"{}\"", TemplateEngine.threadIndex(), templateName);
        }
        
        document.precompute(configuration);
        
        return document;
    }
}
