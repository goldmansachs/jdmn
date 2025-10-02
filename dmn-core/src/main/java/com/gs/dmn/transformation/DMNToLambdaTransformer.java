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
import com.gs.dmn.ast.TDefinitions;
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

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DMNToLambdaTransformer<NUMBER, DATE, TIME, DATE_TIME, DURATION, TEST> extends AbstractDMNToNativeTransformer<NUMBER, DATE, TIME, DATE_TIME, DURATION, TEST> {
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
    protected void transformModels(DMNModelRepository repository, BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer, Path outputPath) {
        // Check if there is only one model
        List<TDefinitions> allDefinitions = repository.getAllDefinitions();
        if (allDefinitions.size() != 1) {
            throw new DMNRuntimeException(String.format("Only one model supported at this stage, found '%s'", allDefinitions.size()));
        }
        TDefinitions definitions = allDefinitions.get(0);
        BasicDMNToJavaTransformer basicTransformer = dialectDefinition.createBasicTransformer(repository, this.lazyEvaluationDetector, this.inputParameters);

        // Generate code for DMN model
        File outputFolder = outputPath.toFile();
        String modelName = definitions.getName();
        String lambdaFolderName = lambdaFolderName(modelName, basicTransformer);
        Path targetPath = Paths.get(outputFolder.toPath().toString(), lambdaFolderName, "src", "main", "java");
        super.transformModels(repository, basicTransformer, targetPath);

        // Generate pom file
        generateLambdaPom(lambdaFolderName, outputFolder);

        // Generate handlers
        generateLambdaRequestHandler(modelName, Paths.get(outputFolder.toPath().toString(), lambdaFolderName, "src", "main", "java"), basicTransformer);

        // Generate SAM template
        generateTemplate(modelName, lambdaFolderName, outputFolder, basicTransformer);
    }

    private void generateLambdaRequestHandler(String modelName, Path functionPath, BasicDMNToJavaTransformer transformer) {
        // Template
        String baseTemplatePath = getAWSBaseTemplatePath();
        String templateName = "lambdaClass.ftl";
        String lambdaName = lambdaName(modelName, transformer);

        try {
            // Output file
            String outputFileName = transformer.upperCaseFirst(lambdaName);
            String javaPackageName = transformer.nativeModelPackageName(modelName);
            String relativeFilePath = javaPackageName.replace('.', '/');
            String fileExtension = ".java";
            File outputFile = this.templateProcessor.makeOutputFile(functionPath, relativeFilePath, outputFileName, fileExtension);

            // Make parameters
            Map<String, Object> params = new HashMap<>();
            params.put("modelName", modelName);
            params.put("transformer", transformer);
            params.put("javaPackageName", javaPackageName);
            params.put("javaClassName", outputFileName);

            this.templateProcessor.processTemplate(baseTemplatePath, templateName, params, outputFile);
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
            File outputFile = this.templateProcessor.makeOutputFile(outputPath, relativeFilePath, fileName, fileExtension);

            // Make parameters
            Map<String, Object> params = new HashMap<>();
            params.put("lambdaGroupId", "com.gs.dmn");
            params.put("lambdaArtifactId", lambdaFolderName);

            this.templateProcessor.processTemplate(baseTemplatePath, templateName, params, outputFile);
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot generate from template '%s' for element '%s'", templateName, lambdaFolderName), e);
        }
    }

    private void generateTemplate(String modelName, String stackName, File outputFolder, BasicDMNToJavaTransformer transformer) {
        // Template
        String baseTemplatePath = getAWSBaseTemplatePath();
        String templateName = "template.ftl";

        List<FunctionResource> functionResources = makeFunctionResources(modelName, transformer);

        try {
            // Output file
            String fileName = "template";
            String fileExtension = ".yaml";
            String relativeFilePath = "";
            Path outputPath = outputFolder.toPath();
            File outputFile = this.templateProcessor.makeOutputFile(outputPath, relativeFilePath, fileName, fileExtension);

            // Make parameters
            Map<String, Object> params = new HashMap<>();
            params.put("modelName", modelName);
            params.put("stackName", stackName);
            params.put("functionResources", functionResources);

            this.templateProcessor.processTemplate(baseTemplatePath, templateName, params, outputFile);
        } catch (Exception e) {
            throw new RuntimeException(String.format("Cannot generate from template '%s' for '%s'", templateName, modelName), e);
        }
    }

    private List<FunctionResource> makeFunctionResources(String modelName, BasicDMNToJavaTransformer transformer) {
        List<FunctionResource> resources = new ArrayList<>();

        String lambdaName = lambdaName(modelName, transformer);
        String folderName = lambdaFolderName(modelName, transformer);
        String codeUri = String.format("%s", folderName);
        String javaPackageName = transformer.nativeModelPackageName(modelName);
        String javaClassName = transformer.upperCaseFirst(lambdaName);
        String handler = String.format("%s.%s::handleRequest", javaPackageName, javaClassName);
        String path = restPath(modelName);
        resources.add(new FunctionResource(javaClassName, codeUri, handler, path));

        return resources;
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

    public String lambdaName(String modelName, BasicDMNToJavaTransformer transformer) {
        String name = transformer.upperCaseFirst(modelName) + "MapRequestHandler";
        return Character.isLetter(name.charAt(0)) ? name : "F" + name;
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
