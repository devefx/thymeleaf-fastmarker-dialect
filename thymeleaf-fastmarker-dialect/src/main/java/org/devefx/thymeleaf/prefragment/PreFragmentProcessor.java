/*
 * Copyright 2016-2017, Youqian Yue (devefx@163.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
