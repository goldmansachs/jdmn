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
package com.gs.dmn.feel.analysis.syntax.ast.library.json;

import java.util.List;

public class FunctionDeclaration {
    private final String name;
    private final List<Parameter> parameters;
    private final String returnType;
    private final String signature;

    public FunctionDeclaration(String name, List<Parameter> parameters, String returnType) {
        this.name = name;
        this.parameters = parameters;
        this.returnType = returnType;
        List<String> parametersText = parameters.stream().map(Parameter::getDescription).toList();
        this.signature = String.format("%s(%s) : %s", name, String.join(", ", parametersText), returnType);
    }

    public String getName() {
        return name;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public String getReturnType() {
        return returnType;
    }

    public String getSignature() {
        return signature;
    }
}
