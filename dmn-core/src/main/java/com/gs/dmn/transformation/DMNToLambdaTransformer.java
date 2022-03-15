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
import com.gs.dmn.context.DMNContext;
import com.gs.dmn.dialect.DMNDialectDefinition;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.serialization.TypeDeserializationConfigurer;
import com.gs.dmn.transformation.basic.BasicDMNToJavaTransformer;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;
import com.gs.dmn.transformation.lazy.LazyEvaluationDetector;
import com.gs.dmn.transformation.template.TemplateProvider;
import com.gs.dmn.validation.DMNValidator;
import freemarker.template.*;
import org.omg.spec.dmn._20191111.model.TDRGElement;
import org.omg.spec.dmn._20191111.model.TDefinitions;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class DMNToLambdaTransformer<NUMBER, DATE, TIME, DATE_TIME, DURATION, TEST> extends AbstractDMNToNativeTransformer<NUMBER, DATE, TIME, DATE_TIME, DURATION, TEST> {
    private static final Version VERSION = new Version("2.3.23");

    public DMNToLambdaTransformer(
            DMNDialectDefinition<NUMBER, DATE, TIME, DATE_TIME, DURATION, TEST> dialectDefinition,
            DMNValidator dmnValidator,
            DMNTransformer<TEST> dmnTransformer,
            TemplateProvider templateProvider,
            LazyEvaluationDetector lazyEvaluationDetector,
            TypeDeserializationConfigurer typeDeserializationConfigurer,
            InputParameters inputParameters,
            BuildLogger logger
    ) {
        super(dialectDefinition, dmnValidator, dmnTransformer, templateProvider, lazyEvaluationDetector, typeDeserializationConfigurer, inputParameters, logger);
    }

    @Override
    protected void transform(BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer, DMNModelRepository repository, Path outputPath) {
        // Check if there is only one model
        List<TDefinitions> allDefinitions = repository.getAllDefinitions();
        if (allDefinitions.size() != 1) {
            throw new DMNRuntimeException(String.format("Only one model supported at this stage, found '%s'", allDefinitions.size()));
        }
        TDefinitions definitions = allDefinitions.get(0);
        BasicDMNToJavaTransformer basicTransformer = dialectDefinition.createBasicTransformer(repository, this.lazyEvaluationDetector, this.inputParameters);

        // Generate pom file
        String modelName = definitions.getName();
        String lambdaFolderName = lambdaFolderName(modelName, basicTransformer);
        File outputFolder = outputPath.toFile();
        generateLambdaPom(lambdaFolderName, outputFolder);

        // Generate code for DMN model
        Path targetPath = Paths.get(outputFolder.toPath().toString(), lambdaFolderName, "src", "main", "java");
        super.transform(basicTransformer, repository, targetPath);

        // Generate handlers
        List<? extends TDRGElement> elements = findRootElements(repository, definitions, inputParameters);
        for (TDRGElement element: elements) {
            generateLambdaRequestHandler(modelName, element, Paths.get(outputFolder.toPath().toString(), lambdaFolderName, "src", "main", "java"), basicTransformer);
        }

        // Generate SAM template
        generateTemplate(modelName, lambdaFolderName, elements, outputFolder, basicTransformer);
    }

    private List<? extends TDRGElement> findRootElements(DMNModelRepository repository, TDefinitions definitions, InputParameters inputParameters) {
        return repository.findDecisions(definitions);
    }

    private void generateLambdaRequestHandler(String modelName, TDRGElement element, Path functionPath, BasicDMNToJavaTransformer transformer) {
        // Template
        String baseTemplatePath = getAWSBaseTemplatePath();
        String templateName = "lambdaClass.ftl";
        String elementName = element.getName();
        String lambdaName = lambdaName(elementName, transformer);

        try {
            // Output file
            String outputFileName = transformer.upperCaseFirst(lambdaName);
            String javaPackageName = transformer.nativeModelPackageName(modelName);
            String relativeFilePath = javaPackageName.replace('.', '/');
            String fileExtension = ".java";
            File outputFile = makeOutputFile(functionPath, relativeFilePath, outputFileName, fileExtension);

            // Make parameters
            Map<String, Object> params = new HashMap<>();
            params.put("modelName", modelName);
            params.put("element", element);
            params.put("transformer", transformer);
            params.put("javaPackageName", javaPackageName);
            params.put("javaClassName", outputFileName);

            processTemplate(baseTemplatePath, templateName, params, outputFile);
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot generate from template '%s' for element '%s'", templateName, lambdaName), e);
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
            throw new DMNRuntimeException(String.format("Cannot generate from template '%s' for element '%s'", templateName, lambdaFolderName), e);
        }
    }

    private void generateTemplate(String modelName, String stackName, List<? extends TDRGElement> elements, File outputFolder, BasicDMNToJavaTransformer transformer) {
        // Template
        String baseTemplatePath = getAWSBaseTemplatePath();
        String templateName = "template.ftl";

        List<FunctionResource> functionResources = makeFunctionResources(modelName, elements, transformer);

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
            throw new RuntimeException(String.format("Cannot generate from template '%s' for '%s'", templateName, elements), e);
        }
    }

    private List<FunctionResource> makeFunctionResources(String modelName, List<? extends TDRGElement> elements, BasicDMNToJavaTransformer transformer) {
        List<FunctionResource> resources = new ArrayList<>();
        for (TDRGElement element: elements) {
            String elementName = element.getName();
            String lambdaName = lambdaName(elementName, transformer);
            String folderName = lambdaFolderName(modelName, transformer);
            String codeUri = String.format("%s", folderName);
            String javaPackageName = transformer.nativeModelPackageName(modelName);
            String javaClassName = transformer.upperCaseFirst(lambdaName);
            String handler = String.format("%s.%s::handleRequest", javaPackageName, javaClassName);
            String path = restPath(elementName);
            resources.add(new FunctionResource(javaClassName, codeUri, handler, path));
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

    public String lambdaFolderName(String modelName, BasicDMNToJavaTransformer transformer) {
        String name = transformer.nativeFriendlyName(modelName);
        if (!Character.isLetter(name.charAt(0))) {
            name = "F" + name;
        }
        return name.toLowerCase();
    }

    public String lambdaName(String elementName, BasicDMNToJavaTransformer transformer) {
        return transformer.upperCaseFirst(elementName) + "Lambda";
    }

    private String restPath(String name) {
        StringBuilder restPath = new StringBuilder();
        boolean foundGap = false;
        for (int i=0; i<name.length(); i++) {
            char ch = name.charAt(i);
            if (Character.isLetterOrDigit(ch)) {
                if (foundGap) {
                    restPath.append("-");
                }
                foundGap = false;
                restPath.append(ch);
            } else {
                foundGap = true;
            }
        }
        String result = restPath.toString();
        if (!Character.isLetter(result.charAt(0))) {
            result = "F" + result;
        }
        return "apply/" + result.toLowerCase();
    }

    @Override
    public String getFileExtension() {
        return ".java";
    }
}
