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
package com.gs.dmn.runtime.interpreter;

import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FormalParameter;
import com.gs.dmn.runtime.DMNRuntimeException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class NamedArguments extends Arguments {
    private final Map<String, Object> arguments;

    public NamedArguments() {
        this(new LinkedHashMap<>());
    }

    public NamedArguments(Map<String, Object> arguments) {
        if (arguments == null) {
            arguments = new LinkedHashMap<>();
        }
        this.arguments = arguments;
    }

    public Map<String, Object> getArguments() {
        return arguments;
    }

    public void add(String name, Object arg) {
        this.arguments.put(name, arg);
    }

    @Override
    public List<Object> argumentList(List<FormalParameter> formalParameters) {
        List<Object> argList = new ArrayList<>();
        if (arguments == null) {
            return argList;
        }
        for(FormalParameter parameter: formalParameters) {
            if (arguments.containsKey(parameter.getName())) {
                Object arg = arguments.get(parameter.getName());
                argList.add(arg);
            } else {
                if (parameter instanceof FormalParameter) {
                    if (parameter.isOptional()) {
                    } else if (parameter.isVarArg()) {
                    } else {
                        throw new DMNRuntimeException(String.format("Missing argument for parameter '%s'", parameter.getName()));
                    }
                } else {
                    throw new DMNRuntimeException(String.format("Missing argument for parameter '%s'", parameter.getName()));
                }
            }
        }
        return argList;
    }
}
