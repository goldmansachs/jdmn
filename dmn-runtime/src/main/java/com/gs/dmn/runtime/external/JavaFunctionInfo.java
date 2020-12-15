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

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class JavaFunctionInfo {
    public static Object[] makeArgs(Method declaredMethod, List<Object> argList) {
        if (declaredMethod != null && declaredMethod.isVarArgs()) {
            int parameterCount = declaredMethod.getParameterCount();
            int mandatoryParameterCount = parameterCount - 1;
            Object[] args = new Object[parameterCount];
            for (int i = 0; i < mandatoryParameterCount; i++) {
                args[i] = argList.get(i);
            }
            args[parameterCount - 1] = argList.subList(mandatoryParameterCount, argList.size()).toArray();
            return args;
        } else {
            return argList.toArray();
        }
    }

    private final String className;
    private final String methodName;
    private final List<String> paramTypes;

    public JavaFunctionInfo(String className, String methodName, List<String> paramTypes) {
        this.className = className;
        this.methodName = methodName;
        this.paramTypes = paramTypes;
    }

    public String getClassName() {
        return className;
    }

    public String getMethodName() {
        return methodName;
    }

    public List<String> getParamTypes() {
        return paramTypes;
    }

    public List<Object> convertArguments(List<Object> argList) {
        List<Object> convertedArgList = new ArrayList<>();
        List<String> javaParamTypes = getParamTypes();
        for (int i=0; i < argList.size(); i++) {
            Object arg = argList.get(i);
            String javaParamType = javaParamTypes.get(i);
            if ("double".equals(javaParamType)) {
                if (arg instanceof BigDecimal) {
                    convertedArgList.add(((BigDecimal) arg).doubleValue());
                } else if (arg instanceof Double) {
                    convertedArgList.add(((Double) arg).doubleValue());
                } else {
                    throw new DMNRuntimeException(String.format("Conversion from '%s' to '%s' is not supported yet", arg.getClass().getSimpleName(), javaParamType));
                }
            } else if ("float".equals(javaParamType)) {
                if (arg instanceof BigDecimal) {
                    convertedArgList.add((float)((BigDecimal) arg).doubleValue());
                } else if (arg instanceof Double) {
                    convertedArgList.add(((Double) arg).doubleValue());
                } else {
                    throw new DMNRuntimeException(String.format("Conversion from '%s' to '%s' is not supported yet", arg.getClass().getSimpleName(), javaParamType));
                }
            } else if ("long".equals(javaParamType)) {
                if (arg instanceof BigDecimal) {
                    convertedArgList.add((long)((BigDecimal) arg).intValue());
                } else if (arg instanceof Double) {
                    convertedArgList.add(((Double) arg).intValue());
                } else {
                    throw new DMNRuntimeException(String.format("Conversion from '%s' to '%s' is not supported yet", arg.getClass().getSimpleName(), javaParamType));
                }
            } else if ("int".equals(javaParamType)) {
                if (arg instanceof BigDecimal) {
                    convertedArgList.add(((BigDecimal) arg).intValue());
                } else if (arg instanceof Double) {
                    convertedArgList.add(((Double) arg).intValue());
                } else {
                    throw new DMNRuntimeException(String.format("Conversion from '%s' to '%s' is not supported yet", arg.getClass().getSimpleName(), javaParamType));
                }
            } else if ("short".equals(javaParamType)) {
                if (arg instanceof BigDecimal) {
                    convertedArgList.add((short)((BigDecimal) arg).intValue());
                } else if (arg instanceof Double) {
                    convertedArgList.add((short)((Double) arg).intValue());
                } else {
                    throw new DMNRuntimeException(String.format("Conversion from '%s' to '%s' is not supported yet", arg.getClass().getSimpleName(), javaParamType));
                }
            } else if ("byte".equals(javaParamType)) {
                if (arg instanceof BigDecimal) {
                    convertedArgList.add((byte)((BigDecimal) arg).intValue());
                } else if (arg instanceof Double) {
                    convertedArgList.add((byte)((Double) arg).intValue());
                } else {
                    throw new DMNRuntimeException(String.format("Conversion from '%s' to '%s' is not supported yet", arg.getClass().getSimpleName(), javaParamType));
                }
            } else if ("char".equals(javaParamType)) {
                if (arg instanceof String) {
                    if (((String) arg).length() == 1) {
                        convertedArgList.add(((String) arg).charAt(0));
                    } else {
                        throw new DMNRuntimeException(String.format("Cannot convert string '%s' to 'char'", arg));
                    }
                } else {
                    throw new DMNRuntimeException(String.format("Conversion from '%s' to '%s' is not supported yet", arg.getClass().getSimpleName(), javaParamType));
                }
            } else if ("java.lang.String".equals(javaParamType)) {
                if (arg instanceof String) {
                    convertedArgList.add(arg);
                } else {
                    throw new DMNRuntimeException(String.format("Conversion from '%s' to '%s' is not supported yet", arg.getClass().getSimpleName(), javaParamType));
                }
            } else {
                convertedArgList.add(arg);
            }
        }
        return convertedArgList;
    }
}
