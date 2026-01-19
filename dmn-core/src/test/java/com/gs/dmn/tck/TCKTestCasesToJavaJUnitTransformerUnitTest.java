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
package com.gs.dmn.tck;

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.context.DMNContext;
import com.gs.dmn.dialect.JavaTimeDMNDialectDefinition;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.log.NopBuildLogger;
import com.gs.dmn.serialization.DefaultTypeDeserializationConfigurer;
import com.gs.dmn.tck.ast.TestCases;
import com.gs.dmn.transformation.InputParameters;
import com.gs.dmn.transformation.ToQuotedNameTransformer;
import com.gs.dmn.transformation.basic.BasicDMNToJavaTransformer;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;
import com.gs.dmn.transformation.lazy.NopLazyEvaluationDetector;
import com.gs.dmn.transformation.template.TreeTemplateProvider;
import com.gs.dmn.validation.NopDMNValidator;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAmount;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class TCKTestCasesToJavaJUnitTransformerUnitTest {
    @Test
    public void testClassNameRemovesExtensionAndHandlesLeadingDigit() {
        InputParameters inputParameters = new InputParameters();
        TestTransformer transformer = makeTransformer(inputParameters);
        BasicDMNToNativeTransformer<Type, DMNContext> basicTransformer = makeBasicTransformer(inputParameters);

        TestCases testCases = new TestCases();
        testCases.setModelName("loan.dmn");
        assertEquals("LoanTest", transformer.exposeTestClassName(testCases, basicTransformer));

        testCases.setModelName("1-loan.dmn");
        assertEquals("_1LoanTest", transformer.exposeTestClassName(testCases, basicTransformer));
    }

    @Test
    public void testTemplateParamsIncludeTestCasesAndTckUtil() throws Exception {
        InputParameters inputParameters = new InputParameters();
        TestTransformer transformer = makeTransformer(inputParameters);
        BasicDMNToNativeTransformer<Type, DMNContext> basicTransformer = makeBasicTransformer(inputParameters);

        TCKUtil<Number, LocalDate, TemporalAccessor, TemporalAccessor, TemporalAmount> tckUtil =
                new TCKUtil<>(basicTransformer, new JavaTimeDMNDialectDefinition().createFEELLib());

        TestCases testCases = new TestCases();
        testCases.setModelName("loan.dmn");

        Map<String, Object> params = invokeMakeTemplateParams(transformer, testCases, tckUtil);

        assertSame(testCases, params.get("testCases"));
        assertSame(tckUtil, params.get("tckUtil"));
    }

    private static TestTransformer makeTransformer(InputParameters inputParameters) {
        return new TestTransformer(
                new JavaTimeDMNDialectDefinition(),
                new NopDMNValidator(),
                new ToQuotedNameTransformer(new NopBuildLogger()),
                new TreeTemplateProvider(),
                new NopLazyEvaluationDetector(),
                new DefaultTypeDeserializationConfigurer(),
                Paths.get("."),
                inputParameters,
                new NopBuildLogger()
        );
    }

    private static BasicDMNToNativeTransformer<Type, DMNContext> makeBasicTransformer(InputParameters inputParameters) {
        DMNModelRepository repository = new DMNModelRepository();
        return new BasicDMNToJavaTransformer(
                new JavaTimeDMNDialectDefinition(),
                repository,
                new NopLazyEvaluationDetector(),
                inputParameters
        );
    }

    @SuppressWarnings("unchecked")
    private static Map<String, Object> invokeMakeTemplateParams(
            TCKTestCasesToJavaJUnitTransformer<Number, LocalDate, TemporalAccessor, TemporalAccessor, TemporalAmount> transformer,
            TestCases testCases,
            TCKUtil<Number, LocalDate, TemporalAccessor, TemporalAccessor, TemporalAmount> tckUtil
    ) throws Exception {
        Method method = TCKTestCasesToJavaJUnitTransformer.class.getDeclaredMethod("makeTemplateParams", TestCases.class, TCKUtil.class);
        method.setAccessible(true);
        return (Map<String, Object>) method.invoke(transformer, testCases, tckUtil);
    }

    private static class TestTransformer extends TCKTestCasesToJavaJUnitTransformer<Number, LocalDate, TemporalAccessor, TemporalAccessor, TemporalAmount> {
        TestTransformer(JavaTimeDMNDialectDefinition dialectDefinition, NopDMNValidator dmnValidator, ToQuotedNameTransformer dmnTransformer, TreeTemplateProvider templateProvider, NopLazyEvaluationDetector lazyEvaluationDetector, DefaultTypeDeserializationConfigurer typeDeserializationConfigurer, Path inputModelPath, InputParameters inputParameters, NopBuildLogger logger) {
            super(dialectDefinition, dmnValidator, dmnTransformer, templateProvider, lazyEvaluationDetector, typeDeserializationConfigurer, inputModelPath, inputParameters, logger);
        }

        String exposeTestClassName(TestCases testCases, BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer) {
            return super.testClassName(testCases, dmnTransformer);
        }
    }
}
