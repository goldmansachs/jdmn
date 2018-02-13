/**
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
package com.gs.dmn.runtime.external;

import com.gs.dmn.runtime.DMNRuntimeException;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

public class DefaultExternalFunctionExecutor implements ExternalFunctionExecutor {
    @Override
    public Object execute(String className, String methodName, Object[] args) {
        try {
            Class<?> cls = Class.forName(className);
            Method[] methods = cls.getMethods();
            for(Method m: methods) {
                if (methodName.equals(m.getName())) {
                    Object instance = cls.newInstance();
                    return m.invoke(instance, args);
                }
            }
            throw new DMNRuntimeException(String.format("Cannot execute external function '%s.%s(%s)'", className, methodName, argsToString(args)));
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot execute external function '%s.%s(%s)'", className, methodName, argsToString(args)), e);
        }
    }

    private String argsToString(Object[] args) {
        return Arrays.stream(args).map(o -> o == null ? "null" : o.toString()).collect(Collectors.joining(", "));
    }
}
