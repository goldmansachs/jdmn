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
import com.gs.dmn.transformation.DMNTransformer;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

public abstract class AbstractDMNMojo extends AbstractMojo {
    @Parameter(defaultValue = "${project}", readonly = true, required = true)
    public MavenProject project;

    protected void checkMandatoryField(Object fieldValue, String fieldName) {
        if (fieldValue == null) {
            throw new IllegalArgumentException(String.format("'%s' is mandatory.", fieldName));
        }
    }

    protected DMNTransformer makeDMNTransformer(String dmnTransformerClassName, BuildLogger logger) throws Exception {
        Class<?> dmnTransformerClass = Class.forName(dmnTransformerClassName);
        try {
            return (DMNTransformer) dmnTransformerClass.getConstructor(new Class[]{BuildLogger.class}).newInstance(new Object[]{logger});
        } catch (Exception e) {
            return (DMNTransformer) dmnTransformerClass.newInstance();
        }
    }
}
