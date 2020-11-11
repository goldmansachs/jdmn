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

public class InputParameters extends LinkedHashMap<String, String> {
    public InputParameters() {
    }

    public InputParameters(Map<String, String> inputParameters) {
        this.putAll(inputParameters);
    }

    public static String getRequiredParam(Map<String, String> parameters, String parameterKey) {
        if (parameters == null || parameters.get(parameterKey) == null || parameters.get(parameterKey).trim().isEmpty()) {
            throw new DMNRuntimeException("A '" + parameterKey + "' parameter is required.");
        } else {
            return parameters.get(parameterKey);
        }
    }

    public static String getOptionalParam(Map<String, String> parameters, String parameterKey, String defaultValue) {
        if (parameters == null || parameters.get(parameterKey) == null || parameters.get(parameterKey).trim().isEmpty()) {
            return defaultValue;
        } else {
            return parameters.get(parameterKey);
        }
    }

    public static String getOptionalParam(Map<String, String> parameters, String parameterKey) {
        if (parameters == null || parameters.get(parameterKey) == null || parameters.get(parameterKey).trim().isEmpty()) {
            return null;
        } else {
            return parameters.get(parameterKey);
        }
    }

    public static boolean getOptionalBooleanParam(Map<String, String> parameters, String paramKey) {
        String param = getOptionalParam(parameters, paramKey);
        return Boolean.parseBoolean(param);
    }

    public static boolean getOptionalBooleanParam(Map<String, String> parameters, String paramKey, String defaultValue) {
        String param = getOptionalParam(parameters, paramKey, defaultValue);
        return Boolean.parseBoolean(param);
    }
}
