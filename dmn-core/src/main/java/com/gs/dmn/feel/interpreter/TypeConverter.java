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

import com.gs.dmn.feel.analysis.semantics.type.*;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.Conversion;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.ConversionKind;
import com.gs.dmn.feel.lib.FEELLib;
import com.gs.dmn.runtime.Context;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.interpreter.Result;

import javax.xml.datatype.Duration;
import java.util.List;

import static com.gs.dmn.feel.analysis.semantics.type.AnyType.ANY;
import static com.gs.dmn.feel.analysis.semantics.type.BooleanType.BOOLEAN;
import static com.gs.dmn.feel.analysis.semantics.type.NumberType.NUMBER;
import static com.gs.dmn.feel.analysis.semantics.type.StringType.STRING;
import static com.gs.dmn.feel.analysis.syntax.ast.expression.function.ConversionKind.ELEMENT_TO_SINGLETON_LIST;
import static com.gs.dmn.feel.analysis.syntax.ast.expression.function.ConversionKind.SINGLETON_LIST_TO_ELEMENT;

public class TypeConverter {
    public Result convertResult(Result result, Type expectedType, FEELLib<?, ?, ?, ?, ?> lib) {
        Object value = Result.value(result);
        Type actualType = Result.type(result);
        if (expectedType == null) {
            expectedType = ANY;
        }

        if (Type.conformsTo(actualType, expectedType)) {
            return new Result(value, expectedType);
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
        ConversionKind conversionKind = conversionKind(value, expectedType);
        Object newValue = convertValue(value, conversionKind, lib);
        return new Result(newValue, expectedType);
    }

    public Object convertValue(Object value, Conversion conversion, FEELLib<?, ?, ?, ?, ?> lib) {
        ConversionKind kind = conversion.getKind();
        return convertValue(value, kind, lib);
    }

    private ConversionKind conversionKind(Object value, Type expectedType) {
        if (conformsTo(value, expectedType)) {
            return ConversionKind.NONE;
        } else if (isSingletonList(value) && conformsTo(((List) value).get(0), expectedType)) {
            return SINGLETON_LIST_TO_ELEMENT;
        } else if (expectedType instanceof ListType && conformsTo(value, ((ListType) expectedType).getElementType())) {
            return ELEMENT_TO_SINGLETON_LIST;
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
        } else {
            throw new DMNRuntimeException(String.format("'%s' is not supported yet", kind));
        }
    }

    private static boolean conformsTo(Object value, Type expectedType) {
        if (expectedType == ANY) {
            return true;
        } else if (value instanceof Number && expectedType == NUMBER) {
            return true;
        } else if (value instanceof String && expectedType == STRING) {
            return true;
        } else if (value instanceof Boolean && expectedType == BOOLEAN) {
            return true;
        } else if (value instanceof Duration && expectedType instanceof DurationType) {
            return true;
        } else if (value instanceof Context && (expectedType instanceof ContextType || expectedType instanceof ItemDefinitionType)) {
            Context context = (Context) value;
            CompositeDataType contextType = (CompositeDataType) expectedType;
            for (String member: contextType.getMembers()) {
                if (!conformsTo(context.get(member), contextType.getMemberType(member))) {
                    return false;
                }
            }
            return true;
        } else if (value instanceof List && expectedType instanceof ListType) {
            for (Object obj : (List) value) {
                if (!conformsTo(obj, ((ListType) expectedType).getElementType())) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    private static boolean isSingletonList(Object value) {
        return value instanceof List && ((List) value).size() == 1;
    }
}
