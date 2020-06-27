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
package com.gs.dmn.feel.synthesis.type;

import java.util.Arrays;

public abstract class KotlinTypeFactory implements NativeTypeFactory {
    @Override
    public String nullableType(String type) {
        return type.endsWith("?") ? type : type + "?";
    }

    @Override
    public String constructorOfGenericType(String typeName, String... typeParameters) {
        if (typeParameters == null || typeParameters.length == 0) {
            return typeName;
        } else {
            return String.format("%s<%s>", typeName, String.join(", ", Arrays.asList(typeParameters)));
        }
    }

    @Override
    public String javaClass(String className) {
        return String.format("%s::class.java", className);
    }
}
