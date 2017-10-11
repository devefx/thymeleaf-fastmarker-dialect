package org.devefx.thymeleaf.processor.attr;

import java.util.List;

import org.devefx.thymeleaf.prefragment.PreFragment;
import org.devefx.thymeleaf.prefragment.PreFragmentProcessor;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.Node;
import org.thymeleaf.processor.ProcessorResult;
import org.thymeleaf.processor.attr.AbstractAttrProcessor;

public class PreFragmentAttrProcessor extends AbstractAttrProcessor {
    
    static final String PROCESSOR_NAME_PREFRAGMENT = "prefragment";
    static final int ATTR_PRECEDENCE = 100;
    
    public PreFragmentAttrProcessor() {
        super(PROCESSOR_NAME_PREFRAGMENT);
    }

    @Override
    protected ProcessorResult processAttribute(Arguments arguments,
            Element element, String attributeName) {
        
        final String attributeValue = element.getAttributeValue(attributeName);
        
        element.removeAttribute(attributeName);
        
        final PreFragment fragment =
                PreFragmentProcessor.computePreFragmentSpec(arguments.getConfiguration(), arguments, attributeValue, element);
        
        final List<Node> fragmentNodes =
                fragment.extractFragment(arguments.getConfiguration(), arguments,
                        arguments.getTemplateRepository(), arguments.getTemplateEngine());
        
        element.clearChildren();
        element.removeAttribute(attributeName);
        element.setChildren(fragmentNodes);
        element.getParent().extractChild(element);
        
        return ProcessorResult.OK;
    }

    @Override
    public int getPrecedence() {
        return ATTR_PRECEDENCE;
    }
}
