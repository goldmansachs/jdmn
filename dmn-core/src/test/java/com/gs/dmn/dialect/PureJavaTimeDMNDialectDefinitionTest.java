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
package com.gs.dmn.dialect;

import com.gs.dmn.AbstractTest;
import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.feel.lib.FEELLib;
import com.gs.dmn.feel.lib.PureJavaTimeFEELLib;
import com.gs.dmn.feel.synthesis.type.NativeTypeFactory;
import com.gs.dmn.feel.synthesis.type.PureJavaTimeNativeTypeFactory;
import com.gs.dmn.runtime.PureJavaTimeDMNBaseDecision;
import com.gs.dmn.runtime.interpreter.DMNInterpreter;
import com.gs.dmn.runtime.interpreter.StandardDMNInterpreter;
import com.gs.dmn.serialization.DefaultTypeDeserializationConfigurer;
import com.gs.dmn.transformation.DMNToJavaTransformer;
import com.gs.dmn.transformation.DMNToNativeTransformer;
import com.gs.dmn.transformation.NopDMNTransformer;
import com.gs.dmn.transformation.basic.BasicDMN2JavaTransformer;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;
import com.gs.dmn.transformation.lazy.NopLazyEvaluationDetector;
import com.gs.dmn.transformation.template.TreeTemplateProvider;
import com.gs.dmn.validation.NopDMNValidator;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class PureJavaTimeDMNDialectDefinitionTest extends AbstractTest {
    private final DMNDialectDefinition dialect = new PureJavaTimeDMNDialectDefinition();
    private static final DMNModelRepository REPOSITORY = new DMNModelRepository();

    @Test
    public void testCreateDMNInterpreter() {
        DMNInterpreter dmnInterpreter = dialect.createDMNInterpreter(REPOSITORY, new LinkedHashMap<>());
        assertEquals(StandardDMNInterpreter.class.getName(), dmnInterpreter.getClass().getName());
    }

    @Test
    public void testCreateDMNToJavaTransformer() {
        Map<String, String> inputParameters = new LinkedHashMap<>();
        inputParameters.put("dmnVersion", "1.1");
        inputParameters.put("modelVersion", "1.2");
        inputParameters.put("platformVersion", "3.2");
        DMNToNativeTransformer dmnToJavaTransformer = dialect.createDMNToNativeTransformer(new NopDMNValidator(), new NopDMNTransformer(), new TreeTemplateProvider(), new NopLazyEvaluationDetector(), new DefaultTypeDeserializationConfigurer(), inputParameters, null);
        assertEquals(DMNToJavaTransformer.class.getName(), dmnToJavaTransformer.getClass().getName());
    }

    @Test
    public void testCreateBasicTransformer() {
        BasicDMNToNativeTransformer basicTransformer = dialect.createBasicTransformer(REPOSITORY, new NopLazyEvaluationDetector(), new LinkedHashMap<>());
        assertEquals(BasicDMN2JavaTransformer.class.getName(), basicTransformer.getClass().getName());
    }

    @Test
    public void testCreateTypeTranslator() {
        NativeTypeFactory typeTranslator = dialect.createTypeTranslator();
        assertEquals(PureJavaTimeNativeTypeFactory.class.getName(), typeTranslator.getClass().getName());
    }

    @Test
    public void testCreateFEELLib() {
        FEELLib feelLib = dialect.createFEELLib();
        assertEquals(PureJavaTimeFEELLib.class.getName(), feelLib.getClass().getName());
    }

    @Test
    public void testGetDecisionBaseClass() {
        String decisionBaseClass = dialect.getDecisionBaseClass();
        assertEquals(PureJavaTimeDMNBaseDecision.class.getName(), decisionBaseClass);
    }
}