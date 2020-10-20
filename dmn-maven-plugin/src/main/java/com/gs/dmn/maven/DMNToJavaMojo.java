/*
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
import com.gs.dmn.transformation.FileTransformer;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@SuppressWarnings("CanBeFinal")
@Mojo(name = "dmn-to-java", defaultPhase = LifecyclePhase.GENERATE_SOURCES, configurator = "dmn-mojo-configurator")
public class DMNToJavaMojo<NUMBER, DATE, TIME, DATE_TIME, DURATION, TEST> extends AbstractDMNToNativeMojo<NUMBER, DATE, TIME, DATE_TIME, DURATION, TEST> {
    @Parameter(required = true, defaultValue = "com.gs.dmn.dialect.StandardDMNDialectDefinition")
    public String dmnDialect;

    @Parameter(required = true, defaultValue = "com.gs.dmn.transformation.template.TreeTemplateProvider")
    public String templateProvider;

    @Override
    protected void checkMandatoryFields() {
        super.checkMandatoryFields(this.dmnDialect);
    }

    @Override
    protected FileTransformer makeTransformer(BuildLogger logger) throws Exception {
        return super.makeTransformer(logger, this.dmnDialect, this.templateProvider);
    }
}
