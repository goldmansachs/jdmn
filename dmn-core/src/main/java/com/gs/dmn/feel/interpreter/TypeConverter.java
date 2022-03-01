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
package com.gs.dmn.feel.interpreter;

import com.gs.dmn.feel.analysis.semantics.type.DateTimeType;
import com.gs.dmn.feel.analysis.semantics.type.ListType;
import com.gs.dmn.feel.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.Conversion;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.ConversionKind;
import com.gs.dmn.feel.lib.FEELLib;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.interpreter.Result;

import java.util.List;

import static com.gs.dmn.feel.analysis.semantics.type.AnyType.ANY;
import static com.gs.dmn.feel.analysis.syntax.ast.expression.function.ConversionKind.*;

public class TypeConverter {
    public Result convertResult(Result result, Type expectedType, FEELLib<?, ?, ?, ?, ?> lib) {
        Object value = Result.value(result);
        Type actualType = Result.type(result);
        if (expectedType == null) {
            expectedType = ANY;
        }

        if (Type.conformsTo(actualType, expectedType)) {
            return Result.of(value, expectedType);
        } else {
            // Dynamic conversion
            return convertValue(value, expectedType, lib);
        }
    }

    public Result convertValue(Object value, Type expectedType, FEELLib<?, ?, ?, ?, ?> lib) {
        // Dynamic conversion
        if (expectedType == null) {
            expectedType = ANY;
        }
        ConversionKind conversionKind = conversionKind(value, expectedType, lib);
        Object newValue = convertValue(value, conversionKind, lib);
        return Result.of(newValue, expectedType);
    }

    public Object convertValue(Object value, Conversion<Type> conversion, FEELLib<?, ?, ?, ?, ?> lib) {
        ConversionKind kind = conversion.getKind();
        return convertValue(value, kind, lib);
    }

    private ConversionKind conversionKind(Object value, Type expectedType, FEELLib<?, ?, ?, ?, ?> lib) {
        if (Type.conformsTo(value, expectedType)) {
            return ConversionKind.NONE;
        } else if (isSingletonList(value) && Type.conformsTo(((List) value).get(0), expectedType)) {
            return SINGLETON_LIST_TO_ELEMENT;
        } else if (expectedType instanceof ListType && Type.conformsTo(value, ((ListType) expectedType).getElementType())) {
            return ELEMENT_TO_SINGLETON_LIST;
        } else if (lib.isDate(value) && expectedType instanceof DateTimeType) {
            return DATE_TO_UTC_MIDNIGHT;
        } else {
            return ConversionKind.CONFORMS_TO;
        }
    }

    private Object convertValue(Object value, ConversionKind kind, FEELLib<?, ?, ?, ?, ?> lib) {
        if (kind == ConversionKind.NONE) {
            return value;
        } else if (kind == ConversionKind.ELEMENT_TO_SINGLETON_LIST) {
            return lib.asList(value);
        } else if (kind == ConversionKind.SINGLETON_LIST_TO_ELEMENT) {
            return lib.asElement((List) value);
        } else if (kind == ConversionKind.CONFORMS_TO) {
            return null;
        } else if (kind == ConversionKind.DATE_TO_UTC_MIDNIGHT) {
            return lib.toDate(value);
        } else {
            throw new DMNRuntimeException(String.format("'%s' is not supported yet", kind));
        }
    }

    private static boolean isSingletonList(Object value) {
        return value instanceof List && ((List) value).size() == 1;
    }
}
