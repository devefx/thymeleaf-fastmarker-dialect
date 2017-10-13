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
