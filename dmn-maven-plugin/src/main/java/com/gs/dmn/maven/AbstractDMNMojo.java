/**
 * Copyright 2016 Goldman Sachs.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.gs.dmn.maven;

import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.transformation.CompositeDMNTransformer;
import com.gs.dmn.transformation.DMNTransformer;
import com.gs.dmn.transformation.NopDMNTransformer;
import com.gs.dmn.transformation.template.TemplateProvider;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDMNMojo extends AbstractMojo {
    @Parameter(defaultValue = "${project}", readonly = true, required = true)
    public MavenProject project;

    protected void checkMandatoryField(Object fieldValue, String fieldName) {
        if (fieldValue == null) {
            throw new IllegalArgumentException(String.format("'%s' is mandatory.", fieldName));
        }
    }

    protected DMNTransformer makeDMNTransformer(String[] dmnTransformerClassNames, BuildLogger logger) throws Exception {
        if (dmnTransformerClassNames == null) {
            return new NopDMNTransformer();
        }
        List<DMNTransformer> dmnTransformers = new ArrayList();
        for(String dmnTransformerClassName: dmnTransformerClassNames) {
            Class<?> dmnTransformerClass = Class.forName(dmnTransformerClassName);
            try {
                dmnTransformers.add((DMNTransformer) dmnTransformerClass.getConstructor(new Class[]{BuildLogger.class}).newInstance(new Object[]{logger}));
            } catch (Exception e) {
                dmnTransformers.add((DMNTransformer) dmnTransformerClass.newInstance());
            }
        }
        return new CompositeDMNTransformer(dmnTransformers);
    }

    protected TemplateProvider makeTemplateProvider(String templateProviderClassName, BuildLogger logger) throws Exception {
        Class<?> templateProviderClass = Class.forName(templateProviderClassName);
        try {
            return (TemplateProvider) templateProviderClass.newInstance();
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format("Cannot build template provider '%s'", templateProviderClass));
        }
    }
}
