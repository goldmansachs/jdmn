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
package com.gs.dmn.feel.interpreter;

import java.lang.reflect.Method;

class MethodUtils {
    public static Method resolveMethod(String methodName, Class cls, Class[] argumentTypes) {
        // try the exact match
        try {
            return cls.getMethod(methodName, argumentTypes);
        } catch (final NoSuchMethodException e) {
        }

        // search through all methods
        Method bestMatch = null;
        final Method[] methods = cls.getMethods();
        for (final Method method : methods) {
            // compare name and parameters
            if (isCompatible(method, methodName, argumentTypes)) {
                // get accessible version of method
                final Method accessibleMethod = org.apache.commons.lang3.reflect.MethodUtils.getAccessibleMethod(method);
                if (accessibleMethod != null && (bestMatch == null || MemberUtils.compareParameterTypes(
                        accessibleMethod.getParameterTypes(),
                        bestMatch.getParameterTypes(),
                        argumentTypes) < 0)) {
                    bestMatch = accessibleMethod;
                }
            }
        }
        return bestMatch;
    }

    private static boolean isCompatible(Method method, String methodName, Class<?>[] argumentTypes) {
        if (!method.getName().equals(methodName)) {
            return false;
        }

        if (method.isVarArgs()) {
            Class<?>[] parameterTypes = method.getParameterTypes();
            int varArgIndex = parameterTypes.length - 1;
            for (int i = 0; i < varArgIndex; i++) {
                if (parameterTypes[i] != null && argumentTypes[i] != null && !parameterTypes[i].isAssignableFrom(argumentTypes[i])) {
                    return false;
                }
            }
            Class<?> varArgType = parameterTypes[varArgIndex].getComponentType();
            for (int i = varArgIndex; i < argumentTypes.length; i++) {
                if (argumentTypes[i] != null && !varArgType.isAssignableFrom(argumentTypes[i])) {
                    return false;
                }
            }
        } else {
            Class<?>[] parameterTypes = method.getParameterTypes();
            if (parameterTypes.length != argumentTypes.length) {
                return false;
            }
            for (int i = 0; i < parameterTypes.length; i++) {
                if (parameterTypes[i] != null && argumentTypes[i] != null && !parameterTypes[i].isAssignableFrom(argumentTypes[i])) {
                    return false;
                }
            }
        }
        return true;
    }

}
