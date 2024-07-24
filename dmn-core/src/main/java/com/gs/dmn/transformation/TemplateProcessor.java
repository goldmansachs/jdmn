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

import com.gs.dmn.ast.TDecision;
import com.gs.dmn.ast.TDefinitions;
import com.gs.dmn.ast.TInvocable;
import com.gs.dmn.ast.TItemDefinition;
import com.gs.dmn.context.DMNContext;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.serialization.TypeDeserializationConfigurer;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;
import com.gs.dmn.transformation.formatter.JavaFormatter;
import com.gs.dmn.transformation.formatter.NopJavaFormatter;
import com.gs.dmn.transformation.proto.MessageType;
import com.gs.dmn.transformation.proto.Service;
import com.gs.dmn.transformation.template.TemplateProvider;
import freemarker.template.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class TemplateProcessor {
    private static final Version VERSION = new Version("2.3.23");
    private static final JavaFormatter FORMATTER = new NopJavaFormatter();

    private final BuildLogger logger;
    private final TemplateProvider templateProvider;
    private final String fileExtension;
    private final TypeDeserializationConfigurer typeDeserializationConfigurer;

    public TemplateProcessor(BuildLogger logger, TemplateProvider templateProvider, String fileExtension, TypeDeserializationConfigurer typeDeserializationConfigurer) {
        this.logger = logger;
        this.templateProvider = templateProvider;
        this.fileExtension = fileExtension;
        this.typeDeserializationConfigurer = typeDeserializationConfigurer;
    }

    public File makeOutputFile(Path outputPath, String relativeFilePath, String fileName, String fileExtension) {
        String absoluteFilePath = outputPath.toAbsolutePath().toString();
        if (!StringUtils.isBlank(relativeFilePath)) {
            absoluteFilePath += "/" + relativeFilePath;
        }
        absoluteFilePath += "/" + fileName + fileExtension;
        File outputFile = new File(absoluteFilePath);
        outputFile.getParentFile().mkdirs();
        return outputFile;
    }

    public void processTemplate(String baseTemplatePath, String templateName, Map<String, Object> params, File outputFile, boolean formatOutput) throws IOException, TemplateException {
        processTemplate(baseTemplatePath, templateName, params, outputFile);
        try {
            String text = FileUtils.readFileToString(outputFile, Charset.defaultCharset());
            if (formatOutput) {
                text = FORMATTER.formatSource(text);
            }
            FileUtils.write(outputFile, text, Charset.defaultCharset(), false);
        } catch (Exception e) {
            logger.error(String.format("Formatting error for file %s", outputFile.getName()));
        }
    }

    void processTemplate(String baseTemplatePath, String templateName, Map<String, Object> params, File outputFile) throws IOException, TemplateException {
        Configuration cfg = makeConfiguration(baseTemplatePath);
        Template template = cfg.getTemplate("/" + templateName);

        try (Writer fileWriter = new FileWriter(outputFile)) {
            template.process(params, fileWriter);
        }
    }

    void processTemplate(TDefinitions definitions, TItemDefinition itemDefinition, String baseTemplatePath, String templateName, BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer, Path outputPath, String javaPackageName, String javaClassName) {
        try {
            // Make parameters
            Map<String, Object> params = makeTemplateParams(definitions, itemDefinition, javaPackageName, javaClassName, dmnTransformer);

            // Make output file
            String relativeFilePath = javaPackageName.replace('.', '/');
            File outputFile = makeOutputFile(outputPath, relativeFilePath, javaClassName, this.fileExtension);

            // Process template
            processTemplate(baseTemplatePath, templateName, params, outputFile, false);
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot process template '%s' for itemDefinition '%s'", templateName, itemDefinition.getName()), e);
        }
    }

    void processTemplate(TDefinitions definitions, TInvocable in, String baseTemplatePath, String templateName, BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer, Path outputPath, String javaPackageName, String javaClassName, String decisionBaseClass) {
        try {
            // Make parameters
            Map<String, Object> params = makeTemplateParams(definitions, in, javaPackageName, javaClassName, decisionBaseClass, dmnTransformer);

            // Make output file
            String relativeFilePath = javaPackageName.replace('.', '/');
            File outputFile = makeOutputFile(outputPath, relativeFilePath, javaClassName, this.fileExtension);

            // Process template
            processTemplate(baseTemplatePath, templateName, params, outputFile, true);
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot process template '%s' for BKM '%s'", templateName, in.getName()), e);
        }
    }

    void processTemplate(TDefinitions definitions, TDecision decision, String baseTemplatePath, String templateName, BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer, Path outputPath, String javaPackageName, String javaClassName, String decisionBaseClass) {
        try {
            // Make parameters
            Map<String, Object> params = makeTemplateParams(definitions, decision, javaPackageName, javaClassName, decisionBaseClass, dmnTransformer);

            // Make output file
            String relativeFilePath = javaPackageName.replace('.', '/');
            File outputFile = makeOutputFile(outputPath, relativeFilePath, javaClassName, this.fileExtension);

            // Process template
            processTemplate(baseTemplatePath, templateName, params, outputFile, true);
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot process template '%s' for decision '%s'", templateName, decision.getName()), e);
        }
    }

    //
    // FreeMarker model methods
    //
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

    private Map<String, Object> makeTemplateParams(TDefinitions definitions, TItemDefinition itemDefinition, String javaPackageName, String javaClassName, BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer) {
        Map<String, Object> params = new HashMap<>();
        params.put("modelName", definitions.getName());
        params.put("itemDefinition", itemDefinition);

        String qualifiedName = dmnTransformer.qualifiedName(javaPackageName, dmnTransformer.itemDefinitionNativeSimpleInterfaceName(itemDefinition));
        String serializationClass = this.typeDeserializationConfigurer.deserializeTypeAs(qualifiedName);
        params.put("serializationClass", serializationClass);

        addCommonParams(params, javaPackageName, javaClassName, dmnTransformer);
        return params;
    }

    private Map<String, Object> makeTemplateParams(TDefinitions definitions, TInvocable invocable, String javaPackageName, String javaClassName, String decisionBaseClass, BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer) {
        Map<String, Object> params = new HashMap<>();
        params.put("modelName", definitions.getName());
        params.put("drgElement", invocable);
        params.put("decisionBaseClass", decisionBaseClass);
        addCommonParams(params, javaPackageName, javaClassName, dmnTransformer);
        return params;
    }

    private Map<String, Object> makeTemplateParams(TDefinitions definitions, TDecision decision, String javaPackageName, String javaClassName, String decisionBaseClass, BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer) {
        Map<String, Object> params = new HashMap<>();
        params.put("modelName", definitions.getName());
        params.put("drgElement", decision);
        params.put("decisionBaseClass", decisionBaseClass);
        addCommonParams(params, javaPackageName, javaClassName, dmnTransformer);
        return params;
    }

    Map<String, Object> makeProtoTemplateParams(Pair<List<MessageType>, List<MessageType>> messageTypes, List<Service> services, String javaPackageName, BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer) {
        Map<String, Object> params = new HashMap<>();
        params.put("protoPackageName", dmnTransformer.protoPackage(javaPackageName));
        params.put("dataTypes", messageTypes.getLeft());
        params.put("responseRequestTypes", messageTypes.getRight());
        params.put("services", services);
        params.put("transformer", dmnTransformer);
        return params;
    }

    Map<String, Object> makeModelRegistryTemplateParams(List<TDefinitions> definitionsList, String javaPackageName, String javaClassName, BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer) {
        Map<String, Object> params = new HashMap<>();
        params.put("definitionsList", definitionsList);
        addCommonParams(params, javaPackageName, javaClassName, dmnTransformer);
        return params;
    }


    private void addCommonParams(Map<String, Object> params, String javaPackageName, String javaClassName, BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer) {
        params.put("javaPackageName", javaPackageName);
        params.put("javaClassName", javaClassName);
        params.put("transformer", dmnTransformer);
        params.put("modelRepository", dmnTransformer.getDMNModelRepository());
    }

    public TemplateProvider getTemplateProvider() {
        return templateProvider;
    }
}
