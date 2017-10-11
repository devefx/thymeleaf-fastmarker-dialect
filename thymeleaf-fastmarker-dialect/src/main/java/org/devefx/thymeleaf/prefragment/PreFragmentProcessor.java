package org.devefx.thymeleaf.prefragment;

import java.util.HashMap;
import java.util.Map;

import org.devefx.thymeleaf.expression.PreFragmentSelection;
import org.devefx.thymeleaf.expression.PreFragmentSelectionUtils;
import org.thymeleaf.Configuration;
import org.thymeleaf.context.IProcessingContext;
import org.thymeleaf.dom.Element;
import org.thymeleaf.exceptions.TemplateProcessingException;
import org.thymeleaf.standard.expression.Assignation;
import org.thymeleaf.standard.expression.AssignationSequence;
import org.thymeleaf.standard.expression.IStandardExpression;
import org.thymeleaf.util.Validate;

public final class PreFragmentProcessor {
    
    public static PreFragment computePreFragmentSpec(final Configuration configuration, final IProcessingContext processingContext, 
            final String standardFragmentSpec, final Element fragmentElement) {
        
        Validate.notNull(processingContext, "Processing Context cannot be null");
        Validate.notEmpty(standardFragmentSpec, "Fragment Spec cannot be null");
        // Target element and attribute names can be null
        
        final PreFragmentSelection fragmentSelection =
                PreFragmentSelectionUtils.parsePrepareSelection(configuration, processingContext, standardFragmentSpec);
        
        final IStandardExpression handleNameExpression = fragmentSelection.getHandleName();
        final String handleName;
        if (handleNameExpression != null) {
            final Object handleNameObject = handleNameExpression.execute(configuration, processingContext);
            if (handleNameObject == null) {
                throw new TemplateProcessingException(
                        "Evaluation of handle name from spec \"" + standardFragmentSpec + "\" " +
                                "returned null.");
            }
            handleName = handleNameObject.toString();
        } else {
            handleName = null;
        }
        
        // Resolve pre-fragment parameters, if specified (null if not)
        final Map<String,Object> fragmentParameters =
                resolveFragmentParameters(configuration, processingContext, fragmentSelection.getParameters());
        
        return new PreFragment(handleName, fragmentParameters, fragmentElement);
    }
    
    private static Map<String,Object> resolveFragmentParameters(
            final Configuration configuration, final IProcessingContext processingContext,
            final AssignationSequence parameters) {

        if (parameters == null) {
            return null;
        }

        final Map<String,Object> parameterValues = new HashMap<String, Object>(parameters.size() + 2);
        for (final Assignation assignation : parameters.getAssignations()) {

            final IStandardExpression parameterNameExpr = assignation.getLeft();
            final Object parameterNameValue = parameterNameExpr.execute(configuration, processingContext);

            final String parameterName = (parameterNameValue == null? null : parameterNameValue.toString());

            final IStandardExpression parameterValueExpr = assignation.getRight();
            final Object parameterValueValue = parameterValueExpr.execute(configuration, processingContext);

            parameterValues.put(parameterName, parameterValueValue);

        }

        return parameterValues;

    }
    
    private PreFragmentProcessor() {
    }
}
