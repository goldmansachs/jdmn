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
package com.gs.dmn.dialect;

import com.gs.dmn.feel.lib.DefaultFEELLib;
import com.gs.dmn.feel.lib.FEELLib;
import com.gs.dmn.feel.synthesis.type.FEELTypeTranslator;
import com.gs.dmn.feel.synthesis.type.StandardFEELTypeTranslator;
import com.gs.dmn.runtime.DefaultDMNBaseDecision;
import com.gs.dmn.runtime.interpreter.DMNInterpreter;
import com.gs.dmn.serialization.DMNValidator;
import com.gs.dmn.serialization.StandardDMNValidator;
import com.gs.dmn.transformation.DMNToJavaTransformer;
import com.gs.dmn.transformation.NopDMNTransformer;
import com.gs.dmn.transformation.basic.BasicDMN2JavaTransformer;
import com.gs.dmn.transformation.template.TreeTemplateProvider;
import org.junit.Test;
import org.omg.spec.dmn._20151101.dmn.TDefinitions;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class StandardDMNDialectDefinitionTest {
    private DMNDialectDefinition dialect = new StandardDMNDialectDefinition();
    private static final TDefinitions definitions = new TDefinitions();

    @Test
    public void testCreateDMNInterpreter() throws Exception {
        DMNInterpreter dmnInterpreter = dialect.createDMNInterpreter(definitions);
        assertEquals(DMNInterpreter.class.getName(), dmnInterpreter.getClass().getName());
    }

    @Test
    public void testCreateDMNToJavaTransformer() throws Exception {
        Map<String, String> inputParameters = new LinkedHashMap<>();
        inputParameters.put("dmnVersion", "1.1");
        inputParameters.put("modelVersion", "1.2");
        inputParameters.put("platformVersion", "3.2");
        DMNToJavaTransformer dmnToJavaTransformer = dialect.createDMNToJavaTransformer(new NopDMNTransformer(), new TreeTemplateProvider(), inputParameters, null);
        assertEquals(DMNToJavaTransformer.class.getName(), dmnToJavaTransformer.getClass().getName());
    }

    @Test
    public void testCreateBasicTransformer() throws Exception {
        BasicDMN2JavaTransformer basicTransformer = dialect.createBasicTransformer(definitions, null);
        assertEquals(BasicDMN2JavaTransformer.class.getName(), basicTransformer.getClass().getName());
    }

    @Test
    public void testCreateTypeTranslator() throws Exception {
        FEELTypeTranslator typeTranslator = dialect.createTypeTranslator();
        assertEquals(StandardFEELTypeTranslator.class.getName(), typeTranslator.getClass().getName());
    }

    @Test
    public void testCreateFEELLib() throws Exception {
        FEELLib feelLib = dialect.createFEELLib();
        assertEquals(DefaultFEELLib.class.getName(), feelLib.getClass().getName());
    }

    @Test
    public void testGetDecisionBaseClass() throws Exception {
        String decisionBaseClass = dialect.getDecisionBaseClass();
        assertEquals(DefaultDMNBaseDecision.class.getName(), decisionBaseClass);
    }

    @Test
    public void testCreateValidator() throws Exception {
        DMNValidator validator = dialect.createValidator(true);
        assertEquals(StandardDMNValidator.class.getName(), validator.getClass().getName());
    }

}