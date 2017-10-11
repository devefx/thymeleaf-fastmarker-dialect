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
