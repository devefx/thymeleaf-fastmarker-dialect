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

package org.devefx.thymeleaf.resourceresolver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.Arguments;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.TemplateProcessingParameters;
import org.thymeleaf.exceptions.TemplateOutputException;
import org.thymeleaf.templatewriter.ITemplateWriter;
import org.thymeleaf.util.Validate;

public class LocalFileFastmarkerResourceResolver implements FastmarkerResourceResolver {
    
    private static final Logger logger = LoggerFactory.getLogger(LocalFileFastmarkerResourceResolver.class);

    public static final String NAME = "LOCALFILE-FASTMARKER-RESOURCE";
    
    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public InputStream getResourceAsStream(
            final TemplateProcessingParameters templateProcessingParameters, final String resourceName) {
        
        Validate.notNull(resourceName, "Resource name cannot be null");
        
        try {
            
            final File file = new File(resourceName);
            if (!file.isFile()) {
                return null;
            }
            return new FileInputStream(file);
            
        } catch (final IOException e) {
            
            if (logger.isDebugEnabled()) {
                if (logger.isTraceEnabled()) {
                    logger.trace(
                            String.format(
                                    "[THYMELEAF][%s][%s] Resource \"%s\" could not be resolved. This can be normal as " +
                                            "maybe this resource is not intended to be resolved by this resolver. " +
                                            "Exception is provided for tracing purposes: ",
                                    TemplateEngine.threadIndex(), templateProcessingParameters.getTemplateName(),
                                    resourceName),
                            e);
                } else {
                    logger.debug(
                            String.format(
                                    "[THYMELEAF][%s][%s] Resource \"%s\" could not be resolved. This can be normal as " +
                                            "maybe this resource is not intended to be resolved by this resolver. " +
                                            "Exception message is provided: %s: %s",
                                    TemplateEngine.threadIndex(), templateProcessingParameters.getTemplateName(),
                                    resourceName, e.getClass().getName(), e.getMessage()));
                }
            }
            
            return null;
        }
    }
    
    @Override
    public void writeFragment(final String resourceName,
            final Arguments arguments, final ITemplateWriter templateWriter) {
        
        Writer writer = null;
        
        try {
            
            File file = new File(resourceName);
            if (!file.canWrite()) {
                File parentFile = file.getParentFile();
                if (!parentFile.exists()) {
                    parentFile.mkdirs();
                }
            }
            
            writer = new FileWriter(file);
            
            // It depends on the ITemplateWriter implementation to allow nulls or not.
            // Standard writer will simply not write anything for null.
            templateWriter.write(arguments, writer, arguments.getDocument());
            
        } catch (IOException e) {
            
            throw new TemplateOutputException("Error during creation of output", e);
        } finally {
            
            if (writer != null) {
                
                try {
                    writer.flush();
                    writer.close();
                } catch (IOException e) {
                    throw new TemplateOutputException("Error during flush & close of output", e);
                }
                
            }
            
        }
        
    }
}
