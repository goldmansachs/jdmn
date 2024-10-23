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

import com.gs.dmn.dialect.JavaTimeKotlinStandardDMNDialectDefinition;
import com.gs.dmn.maven.configuration.components.DMNTransformerComponent;
import com.gs.dmn.tck.ast.TestCases;
import com.gs.dmn.transformation.ToQuotedNameTransformer;
import com.gs.dmn.transformation.template.TreeTemplateProvider;
import com.gs.dmn.validation.NopDMNValidator;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TCKToKotlinJUnitMojoTest<NUMBER, DATE, TIME, DATE_TIME, DURATION> extends AbstractDMNMojoTest<NUMBER, DATE, TIME, DATE_TIME, DURATION, TestCases> {
    @Test
    public void testExecute() throws Exception {
        TCKToKotlinJUnitMojo<NUMBER, DATE, TIME, DATE_TIME, DURATION> mojo = getMojo();

        String inputModel = this.getClass().getClassLoader().getResource("input/0004-lending.dmn").getFile();
        String inputTest = this.getClass().getClassLoader().getResource("input/0004-lending-test-01.xml").getFile();
        mojo.project = project;
        mojo.dmnDialect = JavaTimeKotlinStandardDMNDialectDefinition.class.getName();
        mojo.dmnValidators = new String[] {NopDMNValidator.class.getName()};
        mojo.dmnTransformers = new DMNTransformerComponent[] { new DMNTransformerComponent(ToQuotedNameTransformer.class.getName()) };
        mojo.templateProvider = TreeTemplateProvider.class.getName();
        mojo.inputModelFileDirectory = new File(inputModel);
        mojo.inputTestFileDirectory = new File(inputTest);
        mojo.outputFileDirectory = new File("target/output");
        mojo.inputParameters = makeParams();
        mojo.execute();
        assertTrue(true);
    }

    @Override
    protected TCKToKotlinJUnitMojo<NUMBER, DATE, TIME, DATE_TIME, DURATION> getMojo() {
        return new TCKToKotlinJUnitMojo<>();
    }
}