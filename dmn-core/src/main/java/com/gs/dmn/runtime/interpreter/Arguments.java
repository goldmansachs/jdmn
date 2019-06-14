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

import com.gs.dmn.feel.analysis.syntax.ast.expression.function.Conversion;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.ConversionKind;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FormalParameter;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.ParameterConversions;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public abstract class Arguments {
    public List<Object> convertArguments(List<FormalParameter> formalParameters, ParameterConversions parameterConversions, BiFunction<Object, Conversion, Object> convertArgument) {
        List<Object> argList = argumentList(formalParameters);
        argList = convertArguments(argList, parameterConversions.getConversions(formalParameters), convertArgument);
        return argList;
    }

    private List<Object> convertArguments(List<Object> argList, List<Conversion> parameterConversions, BiFunction<Object, Conversion, Object> convertArgument) {
        if (requiresConversion(parameterConversions)) {
            List<Object> convertedArgList = new ArrayList<>();
            for (int i = 0; i < parameterConversions.size(); i++) {
                Object arg = argList.get(i);
                Conversion conversion = parameterConversions.get(i);
                Object convertedArg = convertArgument.apply(arg, conversion);
                convertedArgList.add(convertedArg);
            }
            return convertedArgList;
        } else {
            return argList;
        }
    }

    private boolean requiresConversion(List<Conversion> parameterConversions) {
        if (parameterConversions == null) {
            return false;
        }
        return parameterConversions.stream().anyMatch(c -> c.getKind() != ConversionKind.NONE);
    }

    protected abstract List<Object> argumentList(List<FormalParameter> formalParameters);
}
