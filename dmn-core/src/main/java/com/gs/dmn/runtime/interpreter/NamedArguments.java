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
package com.gs.dmn.runtime.interpreter;

import com.gs.dmn.feel.analysis.semantics.environment.Parameter;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FormalParameter;
import com.gs.dmn.runtime.DMNRuntimeException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NamedArguments extends Arguments {
    private final Map<String, Object> arguments;

    public NamedArguments(Map<String, Object> arguments) {
        this.arguments = arguments;
    }

    public Object getArgument(String name) {
        return arguments.get(name);
    }

    @Override
    public boolean isEmpty() {
        return arguments == null || arguments.isEmpty();
    }

    @Override
    public List<Object> argumentList(List<FormalParameter> formalParameters) {
        List<Object> argList = new ArrayList();
        if (arguments == null) {
            return argList;
        }
        for(FormalParameter parameter: formalParameters) {
            if (arguments.keySet().contains(parameter.getName())) {
                Object arg = arguments.get(parameter.getName());
                argList.add(arg);
            } else {
                if (parameter instanceof Parameter) {
                    if (((Parameter) parameter).isOptional()) {
                    } else if (((Parameter)parameter).isVarArg()) {
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
