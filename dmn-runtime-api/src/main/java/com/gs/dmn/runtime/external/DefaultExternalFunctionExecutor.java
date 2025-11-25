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
package com.gs.dmn.runtime.external;

import com.gs.dmn.runtime.DMNRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

public class DefaultExternalFunctionExecutor implements ExternalFunctionExecutor {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultExternalFunctionExecutor.class);

    @Override
    public Object execute(JavaFunctionInfo info, List<Object> argList) {
        String className = info.getClassName();
        String methodName = info.getMethodName();
        List<String> paramTypes = info.getParamTypes();
        try {
            // Convert arguments
            List<Object> convertedArgList = info.convertArguments(argList);

            // Prepare data for reflection
            Class<?> cls = Class.forName(className);

            // Method declaredMethod = MethodUtils.resolveMethod(info.getMethodName(), cls, argTypes);
            Method declaredMethod = null;
            Method[] declaredMethods = cls.getDeclaredMethods();
            for (Method m: declaredMethods) {
                if (m.getName().equals(methodName)) {
                    if (m.getParameterCount() == paramTypes.size()) {
                        boolean typesMatch = true;
                        for (int i=0; i<paramTypes.size(); i++) {
                            Class<?> javaClass = m.getParameterTypes()[i];
                            if (! (paramTypes.get(i).equals(javaClass.getSimpleName()) || paramTypes.get(i).equals(javaClass.getName()))) {
                                typesMatch = false;
                                break;
                            }
                        }
                        if (typesMatch) {
                            declaredMethod = m;
                            break;
                        }
                    }
                }
            }
            if (declaredMethod == null) {
                throw new DMNRuntimeException(String.format("Cannot resolve '%s.%s(%s)", className, methodName, String.join(", ", paramTypes)));
            }
            Object[] args = JavaFunctionInfo.makeArgs(declaredMethod, convertedArgList);

            // Try both static and instant calls
            if ((declaredMethod.getModifiers() & Modifier.STATIC) != 0) {
                return declaredMethod.invoke(null, args);
            } else {
                Object obj = cls.getDeclaredConstructor().newInstance();
                return declaredMethod.invoke(obj, args);
            }
        } catch (Exception e) {
            LOGGER.error(String.format("Cannot evaluate function '%s(%s)'", methodName, String.join(", ", paramTypes)), e);
            return null;
        }
    }
}
