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

import com.gs.dmn.dialect.StandardDMNDialectDefinition;
import com.gs.dmn.maven.configuration.components.DMNTransformerComponent;
import com.gs.dmn.transformation.ToSimpleNameTransformer;
import com.gs.dmn.transformation.lazy.NopLazyEvaluationDetector;
import com.gs.dmn.transformation.template.TreeTemplateProvider;
import com.gs.dmn.validation.NopDMNValidator;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertTrue;

public class DMNToKotlinMojoTest<NUMBER, DATE, TIME, DATE_TIME, DURATION, TEST> extends AbstractDMNMojoTest<NUMBER, DATE, TIME, DATE_TIME, DURATION, TEST> {
    @Test
    public void testExecute() throws Exception {
        DMNToKotlinMojo<NUMBER, DATE, TIME, DATE_TIME, DURATION, TEST> mojo = getMojo();

        String input = this.getClass().getClassLoader().getResource("input/0004-lending.dmn").getFile();
        mojo.project = project;
        mojo.dmnDialect = StandardDMNDialectDefinition.class.getName();
        mojo.dmnValidators = new String[] {NopDMNValidator.class.getName()};
        mojo.dmnTransformers = new DMNTransformerComponent[] { new DMNTransformerComponent(ToSimpleNameTransformer.class.getName()) };
        mojo.lazyEvaluationDetectors = new String[] {NopLazyEvaluationDetector.class.getName()};
        mojo.templateProvider = TreeTemplateProvider.class.getName();
        mojo.inputFileDirectory = new File(input);
        mojo.outputFileDirectory = new File("target/output");
        mojo.inputParameters = makeParams();
        mojo.execute();
        assertTrue(true);
    }

    @Override
    protected DMNToKotlinMojo<NUMBER, DATE, TIME, DATE_TIME, DURATION, TEST> getMojo() {
        return new DMNToKotlinMojo<>();
    }
}