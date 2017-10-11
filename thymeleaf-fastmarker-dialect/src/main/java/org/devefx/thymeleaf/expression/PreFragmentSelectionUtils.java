package org.devefx.thymeleaf.expression;

import org.thymeleaf.Configuration;
import org.thymeleaf.context.IProcessingContext;
import org.thymeleaf.exceptions.TemplateProcessingException;
import org.thymeleaf.standard.expression.FragmentSelection;
import org.thymeleaf.standard.expression.FragmentSelectionUtils;
import org.thymeleaf.util.Validate;

public final class PreFragmentSelectionUtils {
    
    static final String OPERATOR = "::";
    
    public static PreFragmentSelection parsePrepareSelection(
            final Configuration configuration, final IProcessingContext processingContext, final String input) {
        
        Validate.notNull(configuration, "Configuration cannot be null");
        Validate.notNull(processingContext, "Processing Context cannot be null");
        Validate.notNull(input, "Input cannot be null");
        
        final String preprocessedInput = input;
        
        if (configuration != null) {
            final PreFragmentSelection cachedPreFragmentSelection =
                    ExpressionCache.getPreFragmentSelectionFromCache(configuration, preprocessedInput);
            if (cachedPreFragmentSelection != null) {
                return cachedPreFragmentSelection;
            }
        }
        
        try {
            final FragmentSelection fragmentSelection =
                    FragmentSelectionUtils.parseFragmentSelection(configuration, processingContext, OPERATOR + preprocessedInput.trim());
            
            final PreFragmentSelection preFragmentSelection =
                    new PreFragmentSelection(fragmentSelection.getFragmentSelector(), fragmentSelection.getParameters());
            
            if (configuration != null) {
                ExpressionCache.putPreFragmentSelectionIntoCache(configuration, preprocessedInput, preFragmentSelection);
            }
            
            return preFragmentSelection;
        } catch (Exception e) {
            throw new TemplateProcessingException("Could not parse as pre-fragment selection: \"" + input + "\"");
        }
    }

}
