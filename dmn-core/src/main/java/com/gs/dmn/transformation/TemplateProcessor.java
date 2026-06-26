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

import com.gs.dmn.ast.*;
import com.gs.dmn.context.DMNContext;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.serialization.TypeDeserializationConfigurer;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;
import com.gs.dmn.transformation.formatter.JavaFormatter;
import com.gs.dmn.transformation.formatter.NopJavaFormatter;
import com.gs.dmn.transformation.repository.OutputElement;
import com.gs.dmn.transformation.repository.OutputRepository;
import com.gs.dmn.transformation.template.TemplateProvider;
import freemarker.template.*;

import java.io.IOException;
import java.io.Writer;
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
    private final InputParameters inputParameters;

    public TemplateProcessor(BuildLogger logger, TemplateProvider templateProvider, String fileExtension, TypeDeserializationConfigurer typeDeserializationConfigurer, InputParameters inputParameters) {
        this.logger = logger;
        this.templateProvider = templateProvider;
        this.fileExtension = fileExtension;
        this.typeDeserializationConfigurer = typeDeserializationConfigurer;
        this.inputParameters = inputParameters;
    }

    public void processTemplate(String baseTemplatePath, String templateName, Map<String, Object> params, OutputElement outElement, boolean formatOutput) throws IOException, TemplateException {
        processTemplate(baseTemplatePath, templateName, params, outElement);
        try {
            String text = outElement.readText(inputParameters.getCharset());
            if (formatOutput) {
                text = FORMATTER.formatSource(text);
            }
            outElement.writeText(text, inputParameters.getCharset());
        } catch (Exception e) {
            logger.error(String.format("Formatting error for file %s", outElement.getName()), e);
        }
    }

    void processTemplate(String baseTemplatePath, String templateName, Map<String, Object> params, OutputElement outElement) throws IOException, TemplateException {
        Configuration cfg = makeConfiguration(baseTemplatePath);
        Template template = cfg.getTemplate("/" + templateName);

        try (Writer writer = outElement.getWriter()) {
            template.process(params, writer);
        }
    }

    void processTemplate(TDefinitions definitions, TItemDefinition itemDefinition, String baseTemplatePath, String templateName, BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer, OutputRepository outputRepository, String nativePackageName, String nativeClassName) {
        try {
            // Make parameters
            Map<String, Object> params = makeTemplateParams(definitions, itemDefinition, nativePackageName, nativeClassName, dmnTransformer);

            // Make output file
            OutputElement outElement = outputRepository.makeOutputElement(nativePackageName, nativeClassName, this.fileExtension);

            // Process template
            processTemplate(baseTemplatePath, templateName, params, outElement, false);
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot process template '%s' for itemDefinition '%s'", templateName, itemDefinition.getName()), e);
        }
    }

    void processTemplate(TDefinitions definitions, TInvocable in, String baseTemplatePath, String templateName, BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer, OutputRepository outputRepository, String nativePackageName, String nativeClassName, String decisionBaseClass) {
        try {
            // Make parameters
            Map<String, Object> params = makeTemplateParams(definitions, in, nativePackageName, nativeClassName, decisionBaseClass, dmnTransformer);

            // Make output file
            OutputElement outElement = outputRepository.makeOutputElement(nativePackageName, nativeClassName, this.fileExtension);

            // Process template
            processTemplate(baseTemplatePath, templateName, params, outElement, true);
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot process template '%s' for BKM '%s'", templateName, in.getName()), e);
        }
    }

    void processTemplate(TDefinitions definitions, TDecision decision, String baseTemplatePath, String templateName, BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer, OutputRepository outputRepository, String nativePackageName, String nativeClassName, String decisionBaseClass) {
        try {
            // Make parameters
            Map<String, Object> params = makeTemplateParams(definitions, decision, nativePackageName, nativeClassName, decisionBaseClass, dmnTransformer);

            // Make output file
            OutputElement outElement = outputRepository.makeOutputElement(nativePackageName, nativeClassName, this.fileExtension);

            // Process template
            processTemplate(baseTemplatePath, templateName, params, outElement, true);
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot process template '%s' for decision '%s'", templateName, decision.getName()), e);
        }
    }

    void processTemplate(TDefinitions definitions, TDRGElement element, String baseTemplatePath, String templateName, BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer, OutputRepository outputRepository, String nativePackageName, String nativeClassName, String decisionBaseClass) {
        try {
            // Make parameters
            Map<String, Object> params = makeTemplateParams(definitions, element, nativePackageName, nativeClassName, decisionBaseClass, dmnTransformer);

            // Make output file
            OutputElement outElement = outputRepository.makeOutputElement(nativePackageName, nativeClassName, this.fileExtension);

            // Process template
            processTemplate(baseTemplatePath, templateName, params, outElement, true);
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot process template '%s' for element '%s'", templateName, element.getName()), e);
        }
    }

    //
    // FreeMarker model methods
    //
    private Configuration makeConfiguration(String basePackagePath) {
        Configuration cfg = new Configuration(VERSION);

        // Some recommended settings:
        cfg.setIncompatibleImprovements(VERSION);
        cfg.setDefaultEncoding(inputParameters.getCharset().name());
        cfg.setLocale(Locale.US);
        cfg.setNumberFormat("#");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        // Where do we load the templates from:
        cfg.setClassForTemplateLoading(this.getClass(), basePackagePath);
        return cfg;
    }

    private Map<String, Object> makeTemplateParams(TDefinitions definitions, TItemDefinition itemDefinition, String nativePackageName, String nativeClassName, BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer) {
        Map<String, Object> params = new HashMap<>();
        params.put("modelName", definitions.getName());
        params.put("itemDefinition", itemDefinition);

        String qualifiedName = dmnTransformer.qualifiedNativeName(nativePackageName, dmnTransformer.itemDefinitionNativeSimpleInterfaceName(itemDefinition));
        String serializationClass = this.typeDeserializationConfigurer.deserializeTypeAs(qualifiedName);
        params.put("serializationClass", serializationClass);

        addCommonParams(params, nativePackageName, nativeClassName, dmnTransformer);
        return params;
    }

    private Map<String, Object> makeTemplateParams(TDefinitions definitions, TDRGElement element, String nativePackageName, String nativeClassName, String decisionBaseClass, BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer) {
        Map<String, Object> params = new HashMap<>();
        params.put("modelName", definitions.getName());
        params.put("drgElement", element);
        params.put("decisionBaseClass", decisionBaseClass);
        addCommonParams(params, nativePackageName, nativeClassName, dmnTransformer);
        return params;
    }

    Map<String, Object> makeModelRegistryTemplateParams(List<TDefinitions> definitionsList, String nativePackageName, String nativeClassName, BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer) {
        Map<String, Object> params = new HashMap<>();
        params.put("definitionsList", definitionsList);
        addCommonParams(params, nativePackageName, nativeClassName, dmnTransformer);
        return params;
    }


    private void addCommonParams(Map<String, Object> params, String nativePackageName, String nativeClassName, BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer) {
        params.put("nativePackageName", nativePackageName);
        params.put("nativeClassName", nativeClassName);
        params.put("transformer", dmnTransformer);
        params.put("modelRepository", dmnTransformer.getDMNModelRepository());
    }

    public TemplateProvider getTemplateProvider() {
        return templateProvider;
    }
}
