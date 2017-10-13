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

package org.devefx.thymeleaf.spring.boot.autoconfigure;

import java.nio.charset.Charset;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "fastmarker.thymeleaf")
public class FastmarkerThymeleafProperties {
    
    private static final Charset DEFAULT_ENCODING = Charset.forName("UTF-8");

    public static final String DEFAULT_PREFIX = System.getProperty("java.io.tmpdir");

    public static final String DEFAULT_SUFFIX = ".html";

    /**
     * Prefix that gets prepended to view names when building a URL.
     */
    private String prefix = DEFAULT_PREFIX;

    /**
     * Suffix that gets appended to view names when building a URL.
     */
    private String suffix = DEFAULT_SUFFIX;

    /**
     * Template mode to be applied to templates. See also StandardTemplateModeHandlers.
     */
    private String mode = "HTML5";

    /**
     * Template encoding.
     */
    private Charset encoding = DEFAULT_ENCODING;

    /**
     * Order of the template resolver in the chain. By default, the template resolver is
     * first in the chain. Order start at 1 and should only be set if you have defined
     * additional "TemplateResolver" beans.
     */
    private Integer templateResolverOrder;


    public String getPrefix() {
        return this.prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return this.suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getMode() {
        return this.mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public Charset getEncoding() {
        return this.encoding;
    }

    public void setEncoding(Charset encoding) {
        this.encoding = encoding;
    }

    public Integer getTemplateResolverOrder() {
        return this.templateResolverOrder;
    }

    public void setTemplateResolverOrder(Integer templateResolverOrder) {
        this.templateResolverOrder = templateResolverOrder;
    }
}
