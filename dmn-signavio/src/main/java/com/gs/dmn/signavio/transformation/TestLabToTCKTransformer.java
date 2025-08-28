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
package com.gs.dmn.signavio.transformation;

import com.gs.dmn.ast.TDefinitions;
import com.gs.dmn.dialect.JavaTimeDMNDialectDefinition;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.log.Slf4jBuildLogger;
import com.gs.dmn.serialization.DMNSerializer;
import com.gs.dmn.signavio.SignavioDMNModelRepository;
import com.gs.dmn.signavio.dialect.JavaTimeSignavioDMNDialectDefinition;
import com.gs.dmn.signavio.testlab.*;
import com.gs.dmn.signavio.testlab.visitor.TestLabContext;
import com.gs.dmn.signavio.testlab.visitor.TestLabEnhancer;
import com.gs.dmn.signavio.testlab.visitor.ToTCKVisitor;
import com.gs.dmn.tck.ast.TestCases;
import com.gs.dmn.tck.serialization.xstream.XMLTCKSerializer;
import com.gs.dmn.transformation.InputParameters;
import com.gs.dmn.transformation.basic.BasicDMNToJavaTransformer;
import com.gs.dmn.transformation.lazy.NopLazyEvaluationDetector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class TestLabToTCKTransformer {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestLabToTCKTransformer.class);

    private static final JavaTimeDMNDialectDefinition STANDARD_DIALECT = new JavaTimeDMNDialectDefinition();
    private static final JavaTimeSignavioDMNDialectDefinition SIGNAVIO_DIALECT = new JavaTimeSignavioDMNDialectDefinition();

    private final InputParameters inputParameters;
    private final TestLabValidator testLabValidator;

    public TestLabToTCKTransformer(InputParameters inputParameters) {
        this.inputParameters = inputParameters;
        this.testLabValidator = new TestLabValidator();
    }

    public void transformFolder(File sourceFolder, File targetFolder) throws Exception {
        // Read Signavio models and TestLab files from folder
        SignavioDMNModelRepository repository = readSignavioModels(sourceFolder);
        List<File> testFiles = Arrays.stream(Objects.requireNonNull(sourceFolder.listFiles())).filter(TestLabSerializer::isTestLabFile).toList();
        for (File testFile : testFiles) {
            TestLab testLab = readTestLab(testFile);
            transformFile(testLab, repository, targetFolder);
        }
    }

    public void transformFile(File testLabFile, File modelFile, File targetFolder) throws Exception {
        TestLab testLab = readTestLab(testLabFile);
        SignavioDMNModelRepository repository = readSignavioModels(modelFile);
        transformFile(testLab, repository, targetFolder);
    }

    private void transformFile(TestLab testLab, SignavioDMNModelRepository repository, File targetFolder) {
        TestCases tckTestCases = transform(testLab, repository);
        String modelName = removeExtensionFromModelName(tckTestCases);
        writeTCK(tckTestCases, new File(targetFolder, modelName + inputParameters.getTckFileExtension()));
    }

    private TestCases transform(TestLab testLab, SignavioDMNModelRepository repository) {
        // Validate TestLab
        testLabValidator.validate(testLab);

        // Enhance TestLab by adding itemComponent names
        BasicDMNToJavaTransformer basicTransformer = STANDARD_DIALECT.createBasicTransformer(repository, new NopLazyEvaluationDetector(), this.inputParameters);
        TestLabUtil testLabUtil = new TestLabUtil(basicTransformer);
        TestLabEnhancer testLabEnhancer = new TestLabEnhancer(testLabUtil);
        testLabEnhancer.enhance(testLab);

        // Transform TestLab to TCK
        ToTCKVisitor visitor = new ToTCKVisitor(basicTransformer);
        return (TestCases) testLab.accept(visitor, new TestLabContext());
    }

    private TestLab readTestLab(File inputFile) throws Exception {
        TestLabSerializer testLabSerializer = makeTestLabSerializer();
        return testLabSerializer.read(inputFile);
    }

    private SignavioDMNModelRepository readSignavioModels(File file) {
        DMNSerializer dmnSerializer = makeSignavioSerializer();
        List<TDefinitions> definitions = dmnSerializer.readModels(file);
        return new SignavioDMNModelRepository(definitions, inputParameters.getSchemaNamespace());
    }

    private void writeTCK(TestCases tckTestCases, File file) {
        BuildLogger logger = new Slf4jBuildLogger(LOGGER);
        XMLTCKSerializer tckSerializer = new XMLTCKSerializer(logger, inputParameters);
        tckSerializer.write(tckTestCases, file);
    }

    private static TestLabSerializer makeTestLabSerializer() {
        return new TestLabSerializer();
    }

    private DMNSerializer makeSignavioSerializer() {
        BuildLogger logger = new Slf4jBuildLogger(LOGGER);
        return SIGNAVIO_DIALECT.createDMNSerializer(logger, this.inputParameters);
    }

    private String removeExtensionFromModelName(TestCases tckTestCases) {
        String modelName = tckTestCases.getModelName();
        int index = modelName.lastIndexOf(inputParameters.getDmnFileExtension());
        modelName = index == - 1 ? modelName : modelName.substring(0, index);
        return modelName;
    }
}
