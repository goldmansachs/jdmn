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
package com.gs.dmn.transformation;

import com.gs.dmn.dialect.DMNDialectDefinition;
import com.gs.dmn.dialect.JavaTimeDMNDialectDefinition;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.serialization.DefaultTypeDeserializationConfigurer;
import com.gs.dmn.serialization.TypeDeserializationConfigurer;
import com.gs.dmn.tck.ast.TestCases;
import com.gs.dmn.transformation.lazy.LazyEvaluationDetector;
import com.gs.dmn.transformation.lazy.NopLazyEvaluationDetector;
import com.gs.dmn.transformation.template.TemplateProvider;
import com.gs.dmn.transformation.template.TreeTemplateProvider;
import com.gs.dmn.validation.DMNValidator;
import com.gs.dmn.validation.NopDMNValidator;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDate;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAmount;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CL3DMNToLambdaTransformerTest extends AbstractTckDMNToJavaTransformerTest {
    @Override
    protected String getInputPath() {
        return "tck/%s/cl3/%s/translator";
    }

    @Override
    protected String getExpectedPath() {
        return "tck/%s/cl3/%s/translator/expected/aws/dmn";
    }

    @Override
    protected FileTransformer makeTransformer(InputParameters inputParameters, BuildLogger logger) {
        // Create transformer
        DMNDialectDefinition<Number, LocalDate, TemporalAccessor, TemporalAccessor, TemporalAmount, TestCases> dmnDialect = makeDialectDefinition();
        DMNValidator dmnValidator = makeDMNValidator(logger);
        DMNTransformer<TestCases> dmnTransformer = makeDMNTransformer(logger);
        TemplateProvider templateProvider = makeTemplateProvider();
        LazyEvaluationDetector lazyEvaluationDetector = makeLazyEvaluationDetector(inputParameters, logger);
        TypeDeserializationConfigurer typeDeserializationConfigurer = makeTypeDeserializationConfigurer(logger);
        return new DMNToLambdaTransformer<>(
                dmnDialect, dmnValidator, dmnTransformer, templateProvider, lazyEvaluationDetector, typeDeserializationConfigurer, inputParameters, logger
        );
    }

    @Test
    public void testCL3() throws Exception {
        doFolderTest("1.3", "0020-vacation-days");
    }

    public static void main(String[] args) {
        // Create transformer
        JavaTimeDMNDialectDefinition dmnDialect = new JavaTimeDMNDialectDefinition();
        DMNValidator dmnValidator = new NopDMNValidator();
        DMNTransformer<TestCases> dmnTransformer = new ToQuotedNameTransformer();
        TemplateProvider templateProvider = new TreeTemplateProvider();
        LazyEvaluationDetector lazyEvaluationDetector = new NopLazyEvaluationDetector();
        TypeDeserializationConfigurer typeDeserializationConfigurer = new DefaultTypeDeserializationConfigurer();
        InputParameters inputParameters = makeLambdaInputParameters();
        DMNToLambdaTransformer<Number, LocalDate, TemporalAccessor, TemporalAccessor, TemporalAmount, TestCases> transformer = new DMNToLambdaTransformer<>(
                dmnDialect, dmnValidator, dmnTransformer, templateProvider, lazyEvaluationDetector, typeDeserializationConfigurer, inputParameters, LOGGER
        );

        // Transform
        File inputFile = new File( "C:\\Work\\Projects\\jdmn-opatrascoiu\\dmn-test-cases\\standard\\tck\\1.3\\cl3\\0020-vacation-days\\0020-vacation-days.dmn");
        File outputFileDirectory = new File("C:/Work/Projects/aws/bpmn-to-aws-examples/dmn-lambda/");
        transformer.transformFiles(List.of(inputFile), inputFile, outputFileDirectory.toPath());
    }

    private static InputParameters makeLambdaInputParameters() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("dmnVersion", "1.3");
        map.put("modelVersion", "1.1");
        map.put("platformVersion", "8.0.0");

        map.put("javaRootPackage", "com.gs.lambda");
        map.put("caching", "true");
        map.put("singletonDecision", "true");

        return new InputParameters(map);
    }
}
