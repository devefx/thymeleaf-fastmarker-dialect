package org.devefx.thymeleaf.dialect;

import java.util.HashSet;
import java.util.Set;

import org.devefx.thymeleaf.processor.attr.PreFragmentAttrProcessor;
import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.processor.IProcessor;

public class FastmarkerDialect extends AbstractDialect {
    
    static final String DIALECT_NAMESPACE_FASTMARKER = "http://www.devefx.org/thymeleaf/fastmarker";
    static final String DIALECT_PREFIX_FASTMARKER = "fa";

    @Override
    public String getPrefix() {
        return DIALECT_PREFIX_FASTMARKER;
    }

    @Override
    public Set<IProcessor> getProcessors() {
        final Set<IProcessor> processors = new HashSet<IProcessor>();
        processors.add(new PreFragmentAttrProcessor());
        return processors;
    }
}
