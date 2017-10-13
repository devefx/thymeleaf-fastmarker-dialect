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

package org.devefx.thymeleaf.templateresolver;

import org.devefx.thymeleaf.resourceresolver.FastmarkerResourceResolver;
import org.thymeleaf.exceptions.ConfigurationException;
import org.thymeleaf.resourceresolver.IResourceResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

public class FastmarkerTemplateResolver extends TemplateResolver {

    @Override
    public void setResourceResolver(IResourceResolver resourceResolver) {
        if (resourceResolver instanceof FastmarkerResourceResolver) {
            super.setResourceResolver(resourceResolver);
        } else {
            throw new ConfigurationException(
                    "Cannot set a resource resolver on " + this.getClass().getName() +
                    ". Resource resolver must be instance of "+ FastmarkerResourceResolver.class.getName());
        }
    }

}
