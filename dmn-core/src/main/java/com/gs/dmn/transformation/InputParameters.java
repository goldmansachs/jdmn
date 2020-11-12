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

import java.util.LinkedHashMap;
import java.util.Map;

public class InputParameters {
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

    private final String dmnVersion;
    private final String modelVersion;
    private final String platformVersion;

    private final boolean xsdValidation;
    private final String schemaNamespace;
    private final String namespace;
    private final String prefix;

    private final String javaRootPackage;
    private final boolean onePackage;
    private final boolean singletonInputData;

    private final boolean caching;
    private final int cachingThreshold;
    private final double sparsityThreshold;
    private final boolean parallelStream;

    private final boolean generateProtoMessages;
    private final boolean generateProtoServices;
    private final String protoVersion;

    public InputParameters() {
        this(new LinkedHashMap<>());
    }

    public InputParameters(Map<String, String> inputParameters) {
        this.dmnVersion = InputParameters.getOptionalParam(inputParameters, "dmnVersion");
        this.modelVersion = InputParameters.getOptionalParam(inputParameters, "modelVersion");
        this.platformVersion = InputParameters.getOptionalParam(inputParameters, "platformVersion");

        this.xsdValidation = InputParameters.getOptionalBooleanParam(inputParameters, "xsdValidation");
        this.namespace = InputParameters.getOptionalParam(inputParameters, "namespace");
        this.schemaNamespace = InputParameters.getOptionalParam(inputParameters, "signavioSchemaNamespace");
        this.prefix = InputParameters.getOptionalParam(inputParameters, "prefix");

        this.javaRootPackage = InputParameters.getOptionalParam(inputParameters, "javaRootPackage");
        this.onePackage = InputParameters.getOptionalBooleanParam(inputParameters, "onePackage", "false");
        this.caching = InputParameters.getOptionalBooleanParam(inputParameters, "caching");
        String cachingThresholdParam = InputParameters.getOptionalParam(inputParameters, "cachingThreshold", "1");
        this.cachingThreshold = Integer.parseInt(cachingThresholdParam);
        this.singletonInputData = InputParameters.getOptionalBooleanParam(inputParameters, "singletonInputData", "true");
        this.parallelStream = InputParameters.getOptionalBooleanParam(inputParameters, "parallelStream", "false");
        this.generateProtoMessages = InputParameters.getOptionalBooleanParam(inputParameters, "generateProtoMessages", "false");
        this.generateProtoServices = InputParameters.getOptionalBooleanParam(inputParameters, "generateProtoServices", "false");
        this.protoVersion = InputParameters.getOptionalParam(inputParameters, "protoVersion", "proto3");

        String sparsityThresholdParam = InputParameters.getOptionalParam(inputParameters, "sparsityThreshold", "0.0");
        this.sparsityThreshold = Double.parseDouble(sparsityThresholdParam);
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

    public String getJavaRootPackage() {
        return javaRootPackage;
    }

    public boolean isOnePackage() {
        return onePackage;
    }

    public boolean isSingletonInputData() {
        return singletonInputData;
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

    public boolean isGenerateProtoMessages() {
        return generateProtoMessages;
    }

    public boolean isGenerateProtoServices() {
        return generateProtoServices;
    }

    public String getProtoVersion() {
        return protoVersion;
    }
}