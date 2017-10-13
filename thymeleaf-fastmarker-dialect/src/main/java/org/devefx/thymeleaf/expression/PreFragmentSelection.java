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

import java.io.Serializable;

import org.thymeleaf.standard.expression.AssignationSequence;
import org.thymeleaf.standard.expression.IStandardExpression;
import org.thymeleaf.util.StringUtils;

public final class PreFragmentSelection implements Serializable {

    private static final long serialVersionUID = 4332906946628318396L;
    
    private final IStandardExpression handleName;
    private final AssignationSequence parameters;
    
    public PreFragmentSelection(
            final IStandardExpression handleName,
            final AssignationSequence parameters) {
        super();
        this.handleName = handleName;
        this.parameters = parameters;
    }
    
    public IStandardExpression getHandleName() {
        return handleName;
    }
    
    public AssignationSequence getParameters() {
        return parameters;
    }
    
    public boolean hasParameters() {
        return this.parameters != null && this.parameters.size() > 0;
    }
    
    public String getStringRepresentation() {

        final String handleNameStringRepresentation =
                (this.handleName != null? this.handleName.getStringRepresentation() : "");

        final String handleSelectionParameters;
        if (this.parameters == null || this.parameters.size() > 0) {
            handleSelectionParameters = "";
        } else {
            final StringBuilder paramBuilder = new StringBuilder();
            paramBuilder.append(' ');
            paramBuilder.append('(');
            paramBuilder.append(StringUtils.join(this.parameters.getAssignations(), ','));
            paramBuilder.append(')');
            handleSelectionParameters = paramBuilder.toString();
        }

        return handleNameStringRepresentation + handleSelectionParameters;
    }

}
