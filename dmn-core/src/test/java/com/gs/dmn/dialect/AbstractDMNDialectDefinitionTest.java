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
import com.gs.dmn.feel.synthesis.type.NativeTypeFactory;
import com.gs.dmn.runtime.interpreter.DMNInterpreter;
import com.gs.dmn.serialization.DefaultTypeDeserializationConfigurer;
import com.gs.dmn.transformation.DMNToNativeTransformer;
import com.gs.dmn.transformation.InputParameters;
import com.gs.dmn.transformation.NopDMNTransformer;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;
import com.gs.dmn.transformation.lazy.NopLazyEvaluationDetector;
import com.gs.dmn.transformation.template.TemplateProvider;
import com.gs.dmn.validation.NopDMNValidator;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public abstract class AbstractDMNDialectDefinitionTest<NUMBER, DATE, TIME, DATE_TIME, DURATION, TEST> extends AbstractTest {
    private final DMNDialectDefinition<NUMBER, DATE, TIME, DATE_TIME, DURATION, TEST> dialect = makeDialect();

    private final DMNModelRepository repository = makeRepository();
    private final InputParameters inputParameters = makeInputParameters();

    @Test
    public void testCreateDMNInterpreter() {
        DMNInterpreter<NUMBER, DATE, TIME, DATE_TIME, DURATION> dmnInterpreter = dialect.createDMNInterpreter(repository, inputParameters);
        assertEquals(getExpectedDMNInterpreterClass(), dmnInterpreter.getClass().getName());
    }

    @Test
    public void testCreateDMNToJavaTransformer() {
        DMNToNativeTransformer dmnToJavaTransformer = dialect.createDMNToNativeTransformer(new NopDMNValidator(), new NopDMNTransformer<>(), makeTemplateProvider(), new NopLazyEvaluationDetector(), new DefaultTypeDeserializationConfigurer(), inputParameters, null);
        assertEquals(getExpectedDMNToNativeTransformerClass(), dmnToJavaTransformer.getClass().getName());
    }

    @Test
    public void testCreateBasicTransformer() {
        BasicDMNToNativeTransformer basicTransformer = dialect.createBasicTransformer(repository, new NopLazyEvaluationDetector(), inputParameters);
        assertEquals(getBasicTransformerClass(), basicTransformer.getClass().getName());
    }

    @Test
    public void testCreateNativeTypeFactory() {
        NativeTypeFactory typeTranslator = dialect.createNativeTypeFactory();
        assertEquals(getExpectedNativeTypeFactoryClass(), typeTranslator.getClass().getName());
    }

    @Test
    public void testCreateFEELLib() {
        FEELLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> feelLib = dialect.createFEELLib();
        assertEquals(getExpectedFEELLibClass(), feelLib.getClass().getName());
    }

    @Test
    public void testGetDecisionBaseClass() {
        String decisionBaseClass = dialect.getDecisionBaseClass();
        assertEquals(getExpectedDecisionBaseClass(), decisionBaseClass);
    }

    @Override
    protected InputParameters makeInputParameters() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("dmnVersion", "1.1");
        map.put("modelVersion", "1.2");
        map.put("platformVersion", "3.2");
        return new InputParameters(map);
    }

    protected abstract DMNDialectDefinition<NUMBER,DATE,TIME,DATE_TIME,DURATION,TEST> makeDialect();
    protected abstract DMNModelRepository makeRepository();
    protected abstract TemplateProvider makeTemplateProvider();

    protected abstract String getExpectedDMNInterpreterClass();
    protected abstract String getExpectedDMNToNativeTransformerClass();
    protected abstract String getBasicTransformerClass();
    protected abstract String getExpectedNativeTypeFactoryClass();
    protected abstract String getExpectedFEELLibClass();
    protected abstract String getExpectedDecisionBaseClass();
}