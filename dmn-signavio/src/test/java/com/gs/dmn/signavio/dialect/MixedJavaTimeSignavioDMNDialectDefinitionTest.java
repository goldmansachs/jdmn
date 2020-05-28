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
package com.gs.dmn.signavio.dialect;

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.dialect.DMNDialectDefinition;
import com.gs.dmn.feel.lib.FEELLib;
import com.gs.dmn.feel.synthesis.type.MixedJavaTimeNativeTypeFactory;
import com.gs.dmn.feel.synthesis.type.NativeTypeFactory;
import com.gs.dmn.runtime.interpreter.DMNInterpreter;
import com.gs.dmn.serialization.DefaultTypeDeserializationConfigurer;
import com.gs.dmn.signavio.SignavioDMNModelRepository;
import com.gs.dmn.signavio.feel.lib.MixedJavaTimeSignavioLib;
import com.gs.dmn.signavio.runtime.MixedJavaTimeSignavioBaseDecision;
import com.gs.dmn.signavio.runtime.interpreter.SignavioDMNInterpreter;
import com.gs.dmn.signavio.testlab.TestLab;
import com.gs.dmn.signavio.transformation.SignavioDMNToJavaTransformer;
import com.gs.dmn.signavio.transformation.basic.BasicSignavioDMN2JavaTransformer;
import com.gs.dmn.signavio.transformation.template.SignavioTreeTemplateProvider;
import com.gs.dmn.transformation.DMNToNativeTransformer;
import com.gs.dmn.transformation.NopDMNTransformer;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;
import com.gs.dmn.transformation.lazy.NopLazyEvaluationDetector;
import com.gs.dmn.validation.NopDMNValidator;
import org.junit.Test;

import javax.xml.datatype.Duration;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetTime;
import java.time.ZonedDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class MixedJavaTimeSignavioDMNDialectDefinitionTest {
    private final DMNDialectDefinition<BigDecimal, LocalDate, OffsetTime, ZonedDateTime, Duration, TestLab> dialect = new MixedJavaTimeSignavioDMNDialectDefinition();
    private static final DMNModelRepository REPOSITORY = new SignavioDMNModelRepository();

    @Test
    public void testCreateDMNInterpreter() {
        DMNInterpreter<BigDecimal, LocalDate, OffsetTime, ZonedDateTime, Duration> dmnInterpreter = dialect.createDMNInterpreter(REPOSITORY, new LinkedHashMap<>());
        assertEquals(SignavioDMNInterpreter.class.getName(), dmnInterpreter.getClass().getName());
    }

    @Test
    public void testCreateDMNToJavaTransformer() {
        Map<String, String> inputParameters = new LinkedHashMap<>();
        inputParameters.put("dmnVersion", "1.1");
        inputParameters.put("modelVersion", "1.2");
        inputParameters.put("platformVersion", "3.2");
        DMNToNativeTransformer dmnToJavaTransformer = dialect.createDMNToNativeTransformer(new NopDMNValidator(), new NopDMNTransformer<>(), new SignavioTreeTemplateProvider(), new NopLazyEvaluationDetector(), new DefaultTypeDeserializationConfigurer(), inputParameters, null);
        assertEquals(SignavioDMNToJavaTransformer.class.getName(), dmnToJavaTransformer.getClass().getName());
    }

    @Test
    public void testCreateBasicTransformer() {
        BasicDMNToNativeTransformer basicTransformer = dialect.createBasicTransformer(REPOSITORY, new NopLazyEvaluationDetector(), new LinkedHashMap<>());
        assertEquals(BasicSignavioDMN2JavaTransformer.class.getName(), basicTransformer.getClass().getName());
    }

    @Test
    public void testCreateTypeTranslator() {
        NativeTypeFactory typeTranslator = dialect.createNativeTypeFactory();
        assertEquals(MixedJavaTimeNativeTypeFactory.class.getName(), typeTranslator.getClass().getName());
    }

    @Test
    public void testCreateFEELLib() {
        FEELLib<BigDecimal, LocalDate, OffsetTime, ZonedDateTime, Duration> feelLib = dialect.createFEELLib();
        assertEquals(MixedJavaTimeSignavioLib.class.getName(), feelLib.getClass().getName());
    }

    @Test
    public void testGetDecisionBaseClass() {
        String decisionBaseClass = dialect.getDecisionBaseClass();
        assertEquals(MixedJavaTimeSignavioBaseDecision.class.getName(), decisionBaseClass);
    }
}