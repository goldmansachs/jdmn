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
package com.gs.dmn.signavio.runtime.interpreter;

import com.gs.dmn.context.DMNContext;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.el.interpreter.ELInterpreter;
import com.gs.dmn.feel.analysis.semantics.type.ListType;
import com.gs.dmn.runtime.interpreter.TypeChecker;
import com.gs.dmn.feel.lib.FEELLib;
import com.gs.dmn.runtime.interpreter.Result;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;

import java.util.List;

public class SignavioTypeChecker extends TypeChecker {
    public SignavioTypeChecker(BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer, ELInterpreter<Type, DMNContext> elInterpreter, FEELLib<?, ?, ?, ?, ?> lib) {
        super(dmnTransformer, elInterpreter, lib);
    }

    @Override
    public Result checkResult(Result result, Type expectedType) {
        return convertResult(result, expectedType);
    }

    protected Result convertResult(Result result, Type expectedType) {
        expectedType = Type.extractTypeFromConstraint(expectedType);
        Object value = Result.value(result);
        if (value == null) {
            return null;
        }
        if (value instanceof List && ((List) value).size() == 1 && !(expectedType instanceof ListType)) {
            value = lib.asElement((List) value);
        }
        return Result.of(value, expectedType);
    }
}
