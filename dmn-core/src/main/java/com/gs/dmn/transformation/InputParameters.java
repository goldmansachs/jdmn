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

import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.serialization.DMNConstants;
import com.gs.dmn.serialization.SerializationFormat;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

public class InputParameters {
    // Frequently used parameter keys
    public static final String DMN_FORMAT_KEY = "dmnFormat";
    public static final String DMN_FORMAT_DEFAULT_VALUE = SerializationFormat.XML.name();
    public static final String DMN_FILE_EXTENSION_KEY = "dmnFileExtension";
    public static final String DMN_FILE_EXTENSION_DEFAULT_VALUE = DMNConstants.DMN_FILE_EXTENSION;

    public static final String TCK_FILE_EXTENSION_KEY = "tckFileExtension";
    public static final String TCK_FORMAT_DEFAULT_VALUE = SerializationFormat.XML.name();
    public static final String TCK_FORMAT_KEY = "tckFormat";
    public static final String TCK_FILE_EXTENSION_DEFAULT_VALUE = DMNConstants.XML_FILE_EXTENSION;

    public static final String APPLY_NAME_KIND_KEY = "applyNameKind";
    public static final String APPLY_NAME_KIND_DEFAULT_VALUE = NameKind.QualifiedName.name();

    protected static String getRequiredParam(Map<String, String> parameters, String parameterKey) {
        if (parameters == null || parameters.get(parameterKey) == null || parameters.get(parameterKey).trim().isEmpty()) {
            throw new DMNRuntimeException("A '" + parameterKey + "' parameter is required.");
        } else {
            return parameters.get(parameterKey);
        }
    }

    protected static String getOptionalParam(Map<String, String> parameters, String parameterKey, String defaultValue) {
        if (parameters == null || parameters.get(parameterKey) == null || parameters.get(parameterKey).trim().isEmpty()) {
            return defaultValue;
        } else {
            return parameters.get(parameterKey);
        }
    }

    protected static String getOptionalParam(Map<String, String> parameters, String parameterKey) {
        if (parameters == null || parameters.get(parameterKey) == null || parameters.get(parameterKey).trim().isEmpty()) {
            return null;
        } else {
            return parameters.get(parameterKey);
        }
    }

    protected static boolean getOptionalBooleanParam(Map<String, String> parameters, String paramKey) {
        String param = getOptionalParam(parameters, paramKey);
        return Boolean.parseBoolean(param);
    }

    protected static boolean getOptionalBooleanParam(Map<String, String> parameters, String paramKey, String defaultValue) {
        String param = getOptionalParam(parameters, paramKey, defaultValue);
        return Boolean.parseBoolean(param);
    }

    //
    // Versioning
    //
    // Version of the DMN standard.
    private final String dmnVersion;
    // Version of the DMN model.
    private final String modelVersion;
    // Version of the DMN platform.
    private final String platformVersion;

    //
    // Serialization
    //
    // Whether to validate files against the XSD schema.
    private final boolean xsdValidation;
    // Namespace for the DMN models.
    private final String schemaNamespace;
    private final String namespace;
    private final String prefix;
    // Format for the DMN models (e.g., XML, JSON).
    private final SerializationFormat dmnFormat;
    // File extension for the generated DMN models.
    private final String dmnFileExtension;
    // Format for the TCK tests (e.g., XML, JSON).
    private final SerializationFormat tckFormat;
    // File extension for the TCK test cases.
    private final String tckFileExtension;
    // Character encoding for reading and writing DMN models and TCK files.
    private final Charset charset;

    //
    // Semantics
    //
    // Whether recursive calls are supported for BKMs.
    private final boolean recursiveCalls;
    // Whether the generated code supported the singleton pattern for decisions.
    private final boolean singletonDecision;
    // Whether to use strong typing during semantic analyses.
    private final boolean strongTyping;
    // Whether to check constraints during evaluation / code generation.
    private final boolean checkConstraints;

    //
    // Translation and execution
    //
    // The root package.
    private final String javaRootPackage;
    // Whether to generate all classes in one package or to use a package structure based on the names of the models.
    private final boolean onePackage;

    // Whether to cache the results of decision evaluations.
    private final boolean caching;
    // Threshold for caching decision results. If the number of evaluations of a decision exceeds this threshold, caching will be enabled for that decision.
    private final int cachingThreshold;
    // Threshold for treating a decision table as sparse. If the ratio of empty cells to total cells in a decision table exceeds this threshold, the decision table will be treated as sparse, which may lead to more efficient evaluation.
    private final double sparsityThreshold;
    // Whether to use parallel streams for evaluating decision tables. This can improve performance for large decision tables, but may not be suitable for all environments or use cases.
    private final boolean parallelStream;

    // Whether to use default values for DRG elements.
    private final boolean mockTesting;
    // Whether to generate extra information in the generated code, such as POMs.
    private final boolean generateExtra;

    // Path to the libraries configuration file.
    private final String librariesConfigPath;

    // What names to use in applyMap().
    NameKind applyNameKind;

    public InputParameters() {
        this(new LinkedHashMap<>());
    }

    public InputParameters(Map<String, String> inputParameters) {
        // Versioning
        this.dmnVersion = InputParameters.getOptionalParam(inputParameters, "dmnVersion");
        this.modelVersion = InputParameters.getOptionalParam(inputParameters, "modelVersion");
        this.platformVersion = InputParameters.getOptionalParam(inputParameters, "platformVersion");

        // Serialization
        this.xsdValidation = InputParameters.getOptionalBooleanParam(inputParameters, "xsdValidation");
        this.namespace = InputParameters.getOptionalParam(inputParameters, "namespace");
        this.schemaNamespace = InputParameters.getOptionalParam(inputParameters, "signavioSchemaNamespace");
        this.prefix = InputParameters.getOptionalParam(inputParameters, "prefix");
        this.dmnFormat = SerializationFormat.valueOf(InputParameters.getOptionalParam(inputParameters, DMN_FORMAT_KEY, DMN_FORMAT_DEFAULT_VALUE));
        this.dmnFileExtension = InputParameters.getOptionalParam(inputParameters, DMN_FILE_EXTENSION_KEY, DMN_FILE_EXTENSION_DEFAULT_VALUE);
        this.tckFormat = SerializationFormat.valueOf(InputParameters.getOptionalParam(inputParameters, TCK_FORMAT_KEY, TCK_FORMAT_DEFAULT_VALUE));
        this.tckFileExtension = InputParameters.getOptionalParam(inputParameters, TCK_FILE_EXTENSION_KEY, TCK_FILE_EXTENSION_DEFAULT_VALUE);
        String charsetName = InputParameters.getOptionalParam(inputParameters, "encoding");
        this.charset = charsetName == null ? StandardCharsets.UTF_8 : Charset.forName(charsetName);

        // Semantics
        this.strongTyping = InputParameters.getOptionalBooleanParam(inputParameters, "strongTyping", "true");
        this.recursiveCalls = InputParameters.getOptionalBooleanParam(inputParameters, "recursiveCalls", "true");
        this.singletonDecision = InputParameters.getOptionalBooleanParam(inputParameters, "singletonDecision", "false");
        this.checkConstraints = InputParameters.getOptionalBooleanParam(inputParameters, "checkConstraints", "false");

        // Translation and execution
        this.javaRootPackage = InputParameters.getOptionalParam(inputParameters, "javaRootPackage");
        this.onePackage = InputParameters.getOptionalBooleanParam(inputParameters, "onePackage", "false");
        this.caching = InputParameters.getOptionalBooleanParam(inputParameters, "caching");
        String cachingThresholdParam = InputParameters.getOptionalParam(inputParameters, "cachingThreshold", "1");
        this.cachingThreshold = Integer.parseInt(cachingThresholdParam);
        this.parallelStream = InputParameters.getOptionalBooleanParam(inputParameters, "parallelStream", "false");

        String sparsityThresholdParam = InputParameters.getOptionalParam(inputParameters, "sparsityThreshold", "0.0");
        this.sparsityThreshold = Double.parseDouble(sparsityThresholdParam);

        this.mockTesting = InputParameters.getOptionalBooleanParam(inputParameters, "mockTesting");
        this.generateExtra = InputParameters.getOptionalBooleanParam(inputParameters, "generateExtra", "false");

        this.librariesConfigPath = InputParameters.getOptionalParam(inputParameters, "librariesConfigPath", "feel/library/libraries.json");

        this.applyNameKind = NameKind.valueOf(InputParameters.getOptionalParam(inputParameters, APPLY_NAME_KIND_KEY, APPLY_NAME_KIND_DEFAULT_VALUE));
    }

    public String getDmnVersion() {
        return dmnVersion;
    }

    public String getModelVersion() {
        return modelVersion;
    }

    public String getPlatformVersion() {
        return platformVersion;
    }

    public String getSchemaNamespace() {
        return schemaNamespace;
    }

    public String getNamespace() {
        return namespace;
    }

    public String getPrefix() {
        return prefix;
    }

    public boolean isXsdValidation() {
        return xsdValidation;
    }

    public SerializationFormat getDmnFormat() {
        return dmnFormat;
    }

    public String getDmnFileExtension() {
        return dmnFileExtension;
    }

    public SerializationFormat getTckFormat() {
        return tckFormat;
    }

    public String getTckFileExtension() {
        return tckFileExtension;
    }

    public Charset getCharset() {
        return this.charset;
    }

    public boolean isStrongTyping() {
        return strongTyping;
    }

    public boolean isRecursiveCalls() {
        return recursiveCalls;
    }

    public String getJavaRootPackage() {
        return javaRootPackage;
    }

    public boolean isOnePackage() {
        return onePackage;
    }

    public boolean isSingletonDecision() {
        return singletonDecision;
    }

    public boolean isCaching() {
        return caching;
    }

    public int getCachingThreshold() {
        return cachingThreshold;
    }

    public double getSparsityThreshold() {
        return sparsityThreshold;
    }

    public boolean isParallelStream() {
        return parallelStream;
    }

    public boolean isMockTesting() {
        return mockTesting;
    }

    public boolean isGenerateExtra() {
        return generateExtra;
    }

    public boolean isCheckConstraints() {
        return checkConstraints;
    }

    public String getLibrariesConfigPath() {
        return librariesConfigPath;
    }

    public NameKind getApplyNameKind() {
        return applyNameKind;
    }
}