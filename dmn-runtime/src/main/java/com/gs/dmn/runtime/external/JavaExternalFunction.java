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

import java.math.BigDecimal;
import java.util.Arrays;

public class JavaExternalFunction<R> {
    private final JavaFunctionInfo javaFunctionInfo;
    private final ExternalFunctionExecutor externalExecutor;
    private final Class returnType;

    public JavaExternalFunction(JavaFunctionInfo javaFunctionInfo, ExternalFunctionExecutor externalExecutor_, Class returnType) {
        this.javaFunctionInfo = javaFunctionInfo;
        this.externalExecutor = externalExecutor_;
        this.returnType = returnType;
    }

    public R apply(Object... args) {
        Object result = this.externalExecutor.execute(this.javaFunctionInfo, Arrays.asList(args));
        if (result == null) {
            return (R) result;
        }
        if (this.returnType == result.getClass()) {
            return (R) result;
        } else if (returnType == BigDecimal.class) {
            return (R) new BigDecimal(result.toString());
        } else if (returnType == Double.class) {
            return (R) new Double(result.toString());
        } else {
            return (R) result;
        }
    }

    private static Object convertNumber(Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof Number) {
            return new BigDecimal(object.toString());
        }
        return object;
    }
}
