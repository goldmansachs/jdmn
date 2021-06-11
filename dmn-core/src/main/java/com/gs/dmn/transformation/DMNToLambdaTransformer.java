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

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.dialect.DMNDialectDefinition;
import com.gs.dmn.dialect.StandardDMNDialectDefinition;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.log.Slf4jBuildLogger;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.serialization.DMNReader;
import com.gs.dmn.serialization.DefaultTypeDeserializationConfigurer;
import com.gs.dmn.serialization.TypeDeserializationConfigurer;
import com.gs.dmn.transformation.lazy.LazyEvaluationDetector;
import com.gs.dmn.transformation.lazy.NopLazyEvaluationDetector;
import com.gs.dmn.transformation.template.TemplateProvider;
import com.gs.dmn.transformation.template.TreeTemplateProvider;
import com.gs.dmn.validation.DMNValidator;
import com.gs.dmn.validation.NopDMNValidator;
import freemarker.template.*;
import org.apache.commons.lang3.StringUtils;
import org.omg.dmn.tck.marshaller._20160719.TestCases;
import org.omg.spec.dmn._20191111.model.TDefinitions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class DMNToLambdaTransformer<NUMBER, DATE, TIME, DATE_TIME, DURATION, TEST> {
    private static final Version VERSION = new Version("2.3.23");

    private static final Logger LOGGER = LoggerFactory.getLogger(DMNToLambdaTransformer.class);

    protected static final File STANDARD_FOLDER = new File("dmn-test-cases/standard");

    private final DMNToNativeTransformer dmnToNativeTransformer;
    private final InputParameters inputParameters;
    private final BuildLogger logger;

    public DMNToLambdaTransformer(
            DMNDialectDefinition<NUMBER, DATE, TIME, DATE_TIME, DURATION, TEST> dmnDialect,
            DMNValidator dmnValidator,
            DMNTransformer<TEST> dmnTransformer,
            TemplateProvider templateProvider,
            LazyEvaluationDetector lazyEvaluationDetector,
            TypeDeserializationConfigurer typeDeserializationConfigurer,
            InputParameters inputParameters,
            BuildLogger logger
    ) {
        this.inputParameters = inputParameters;
        this.logger = logger;
        // Create native transformer
        this.dmnToNativeTransformer = dmnDialect.createDMNToNativeTransformer(
                dmnValidator,
                dmnTransformer,
                templateProvider,
                lazyEvaluationDetector,
                typeDeserializationConfigurer,
                inputParameters,
                logger
        );
    }

    private void transform(File inputFile, File outputFolder) {
        // Read models
        DMNReader reader = new DMNReader(logger, false);
        DMNModelRepository repository = new DMNModelRepository(reader.read(inputFile));
        List<TDefinitions> allDefinitions = repository.getAllDefinitions();
        if (allDefinitions.size() != 1) {
            throw new DMNRuntimeException(String.format("Only one model supported at this stage, found '%s'", allDefinitions.size()));
        }
        TDefinitions definitions = allDefinitions.get(0);

        // Generate lambda
        String modelName = definitions.getName();
        String lambdaFolderName = lambdaFolderName(modelName);
        String lambdaName = javaClassName(modelName);
        Path srcPath = Paths.get(outputFolder.toPath().toString(), lambdaFolderName, "src", "main", "java");
        generateLambdaRequestHandler(modelName, lambdaName, srcPath);
        generateLambdaPom(lambdaFolderName, outputFolder);
        Path targetPath = srcPath;
        dmnToNativeTransformer.transform(inputFile.toPath(), targetPath);

        // Generate SAM template
        generateTemplate(modelName, lambdaFolderName, Arrays.asList(lambdaName), outputFolder);
    }

    private void generateLambdaRequestHandler(String modelName, String lambdaName, Path functionPath) {
        // Template
        String baseTemplatePath = getAWSBaseTemplatePath();
        String templateName = "lambdaClass.ftl";

        try {
            // Output file
            String outputFileName = javaClassName(lambdaName);
            String javaPackageName = javaPackageName(lambdaName);
            String relativeFilePath = javaPackageName.replace('.', '/');
            String fileExtension = ".java";
            File outputFile = makeOutputFile(functionPath, relativeFilePath, outputFileName, fileExtension);

            // Make parameters
            Map<String, Object> params = new HashMap<>();
            params.put("modelName", modelName);
            params.put("javaPackageName", javaPackageName);
            params.put("javaClassName", outputFileName);

            processTemplate(baseTemplatePath, templateName, params, outputFile);
        } catch (Exception e) {
            throw new RuntimeException(String.format("Cannot generate from template '%s' for element '%s'", templateName, lambdaName), e);
        }
    }

    private void generateLambdaPom(String lambdaFolderName, File outputFolder) {
        // Template
        String baseTemplatePath = getAWSBaseTemplatePath();
        String templateName = "pomFile.ftl";

        try {
            // Function folder
            Path outputPath = Paths.get(outputFolder.toPath().toString(), lambdaFolderName);

            // Output file
            String fileName = "pom";
            String fileExtension = ".xml";
            String relativeFilePath = "";
            File outputFile = makeOutputFile(outputPath, relativeFilePath, fileName, fileExtension);

            // Make parameters
            Map<String, Object> params = new HashMap<>();
            params.put("lambdaGroupId", "com.gs.dmn");
            params.put("lambdaArtifactId", lambdaFolderName);

            processTemplate(baseTemplatePath, templateName, params, outputFile);
        } catch (Exception e) {
            throw new RuntimeException(String.format("Cannot generate from template '%s' for element '%s'", templateName, lambdaFolderName), e);
        }
    }

    private void generateTemplate(String modelName, String stackName, List<String> lambdaNames, File outputFolder) {
        // Template
        String baseTemplatePath = getAWSBaseTemplatePath();
        String templateName = "template.ftl";

        List<FunctionResource> functionResources = makeFunctionResources(lambdaNames);

        try {
            // Output file
            String fileName = "template";
            String fileExtension = ".yaml";
            String relativeFilePath = "";
            Path outputPath = outputFolder.toPath();
            File outputFile = makeOutputFile(outputPath, relativeFilePath, fileName, fileExtension);

            // Make parameters
            Map<String, Object> params = new HashMap<>();
            params.put("modelName", modelName);
            params.put("stackName", stackName);
            params.put("functionResources", functionResources);

            processTemplate(baseTemplatePath, templateName, params, outputFile);
        } catch (Exception e) {
            throw new RuntimeException(String.format("Cannot generate from template '%s' for '%s'", templateName, lambdaNames), e);
        }
    }

    private List<FunctionResource> makeFunctionResources(List<String> lambdaNames) {
        List<FunctionResource> resources = new ArrayList<>();
        for (String lambdaName: lambdaNames) {
            String folderName = lambdaFolderName(lambdaName);
            String codeUri = String.format("%s", folderName);
            String javaPackageName = javaPackageName(lambdaName);
            String javaClassName = javaClassName(lambdaName);
            String handler = String.format("%s.%s::handleRequest", javaPackageName, javaClassName);
            resources.add(new FunctionResource(javaClassName, codeUri, handler));
        }
        return resources;
    }

    protected void processTemplate(String baseTemplatePath, String templateName, Map<String, Object> params, File outputFile) throws IOException, TemplateException {
        Configuration cfg = makeConfiguration(baseTemplatePath);
        Template template = cfg.getTemplate("/" + templateName);

        try (Writer fileWriter = new FileWriter(outputFile)) {
            template.process(params, fileWriter);
        }
    }

    private Configuration makeConfiguration(String basePackagePath) {
        Configuration cfg = new Configuration(VERSION);

        // Some recommended settings:
        cfg.setIncompatibleImprovements(VERSION);
        cfg.setDefaultEncoding("UTF-8");
        cfg.setLocale(Locale.US);
        cfg.setNumberFormat("#");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        // Where do we load the templates from:
        cfg.setClassForTemplateLoading(this.getClass(), basePackagePath);
        return cfg;
    }

    protected String getAWSBaseTemplatePath() {
        return "/templates/aws";
    }

    protected File makeOutputFile(Path outputPath, String relativeFilePath, String fileName, String fileExtension) {
        String absoluteFilePath = outputPath.toAbsolutePath().toString();
        if (!StringUtils.isBlank(relativeFilePath)) {
            absoluteFilePath += "/" + relativeFilePath;
        }
        absoluteFilePath += "/" + fileName + fileExtension;
        File outputFile = new File(absoluteFilePath);
        outputFile.getParentFile().mkdirs();
        return outputFile;
    }

    private static InputParameters makeInputParameters() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("dmnVersion", "1.3");
        map.put("modelVersion", "1.1");
        map.put("platformVersion", "5.0.0");

        map.put("javaRootPackage", "com.gs.lambda");
        map.put("caching", "true");

        return new InputParameters(map);
    }

    public String lambdaFolderName(String modelName) {
        return javaFriendlyName(modelName).toLowerCase();
    }

    // Names
    public String javaPackageName(String lambdaName) {
        return inputParameters.getJavaRootPackage();
    }

    private String javaFriendlyName(String name) {
        StringBuilder javaName = new StringBuilder();
        boolean upperCase = true;
        for (int i=0; i<name.length(); i++) {
            char ch = name.charAt(i);
            if (Character.isLetterOrDigit(ch)) {
                if (upperCase) {
                    ch = Character.toUpperCase(ch);
                }
                upperCase = false;
                javaName.append(ch);
            } else {
                upperCase = true;
            }
        }
        String result = javaName.toString();
        if (!Character.isLetter(result.charAt(0))) {
            result = "F" + result;
        }
        return result;
    }

    public String javaClassName(String lambdaName) {
        return javaFriendlyName(lambdaName).replaceAll("_", "");
    }

    public String getFileExtension() {
        return ".java";
    }

    public static void main(String[] args) {
        // Create transformer
        StandardDMNDialectDefinition dmnDialect = new StandardDMNDialectDefinition();
        DMNValidator dmnValidator = new NopDMNValidator();
        DMNTransformer<TestCases> dmnTransformer = new ToQuotedNameTransformer();
        TemplateProvider templateProvider = new TreeTemplateProvider();
        LazyEvaluationDetector lazyEvaluationDetector = new NopLazyEvaluationDetector();
        TypeDeserializationConfigurer typeDeserializationConfigurer = new DefaultTypeDeserializationConfigurer();
        InputParameters inputParameters = makeInputParameters();
        BuildLogger logger = new Slf4jBuildLogger(LOGGER);
        DMNToLambdaTransformer<BigDecimal, XMLGregorianCalendar, XMLGregorianCalendar, XMLGregorianCalendar, Duration, TestCases> transformer = new DMNToLambdaTransformer<>(
                dmnDialect, dmnValidator, dmnTransformer, templateProvider, lazyEvaluationDetector, typeDeserializationConfigurer, inputParameters, logger
        );

        // Transform
        File inputFileDirectory = new File(STANDARD_FOLDER, "tck/1.3/cl3/0020-vacation-days/0020-vacation-days.dmn");
        File outputFileDirectory = new File("C:/Work/Projects/aws/bpmn-to-aws-examples/dmn-lambda/");
        transformer.transform(inputFileDirectory, outputFileDirectory);
    }
}
