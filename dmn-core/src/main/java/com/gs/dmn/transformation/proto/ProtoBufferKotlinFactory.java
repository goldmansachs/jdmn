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
package com.gs.dmn.transformation.proto;

import com.gs.dmn.ast.TDRGElement;
import com.gs.dmn.feel.analysis.semantics.type.*;
import com.gs.dmn.transformation.basic.BasicDMNToJavaTransformer;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.gs.dmn.el.analysis.semantics.type.AnyType.ANY;
import static com.gs.dmn.el.analysis.semantics.type.NullType.NULL;
import static com.gs.dmn.feel.analysis.semantics.type.BooleanType.BOOLEAN;
import static com.gs.dmn.feel.analysis.semantics.type.DateTimeType.*;
import static com.gs.dmn.feel.analysis.semantics.type.DateType.DATE;
import static com.gs.dmn.feel.analysis.semantics.type.DaysAndTimeDurationType.DAYS_AND_TIME_DURATION;
import static com.gs.dmn.feel.analysis.semantics.type.DaysAndTimeDurationType.DAY_TIME_DURATION;
import static com.gs.dmn.feel.analysis.semantics.type.EnumerationType.ENUMERATION;
import static com.gs.dmn.feel.analysis.semantics.type.NumberType.NUMBER;
import static com.gs.dmn.feel.analysis.semantics.type.StringType.STRING;
import static com.gs.dmn.feel.analysis.semantics.type.TimeType.TIME;
import static com.gs.dmn.feel.analysis.semantics.type.YearsAndMonthsDurationType.YEARS_AND_MONTHS_DURATION;
import static com.gs.dmn.feel.analysis.semantics.type.YearsAndMonthsDurationType.YEAR_MONTH_DURATION;

public class ProtoBufferKotlinFactory extends ProtoBufferFactory {
    static final Map<String, String> FEEL_TYPE_TO_NATIVE_PROTO_TYPE = new LinkedHashMap<>();

    static {
        FEEL_TYPE_TO_NATIVE_PROTO_TYPE.put(NUMBER.getName(), "Double");
        FEEL_TYPE_TO_NATIVE_PROTO_TYPE.put(STRING.getName(), "String");
        FEEL_TYPE_TO_NATIVE_PROTO_TYPE.put(BOOLEAN.getName(), "Boolean");
        FEEL_TYPE_TO_NATIVE_PROTO_TYPE.put(DATE.getName(), "String");
        FEEL_TYPE_TO_NATIVE_PROTO_TYPE.put(TIME.getName(), "String");
        FEEL_TYPE_TO_NATIVE_PROTO_TYPE.put(DATE_AND_TIME.getName(), "String");
        FEEL_TYPE_TO_NATIVE_PROTO_TYPE.put(DATE_TIME_CAMEL.getName(), "String");
        FEEL_TYPE_TO_NATIVE_PROTO_TYPE.put(DATE_TIME.getName(), "String");
        FEEL_TYPE_TO_NATIVE_PROTO_TYPE.put(YEARS_AND_MONTHS_DURATION.getName(), "String");
        FEEL_TYPE_TO_NATIVE_PROTO_TYPE.put(YEAR_MONTH_DURATION.getName(), "String");
        FEEL_TYPE_TO_NATIVE_PROTO_TYPE.put(DAYS_AND_TIME_DURATION.getName(), "String");
        FEEL_TYPE_TO_NATIVE_PROTO_TYPE.put(DAY_TIME_DURATION.getName(), "String");
        FEEL_TYPE_TO_NATIVE_PROTO_TYPE.put(ENUMERATION.getName(), "String");

        FEEL_TYPE_TO_NATIVE_PROTO_TYPE.put(ANY.getName(), "Any");
        FEEL_TYPE_TO_NATIVE_PROTO_TYPE.put(ANY.getName(), "Any");
        FEEL_TYPE_TO_NATIVE_PROTO_TYPE.put(NULL.getName(), "Any");
        FEEL_TYPE_TO_NATIVE_PROTO_TYPE.put(TemporalType.TEMPORAL.getName(), "String");
        FEEL_TYPE_TO_NATIVE_PROTO_TYPE.put(DurationType.DURATION.getName(), "String");
        FEEL_TYPE_TO_NATIVE_PROTO_TYPE.put(ComparableDataType.COMPARABLE.getName(), "Any");
        FEEL_TYPE_TO_NATIVE_PROTO_TYPE.put(FEELType.typeName(ContextType.CONTEXT), "Any");
    }

    public ProtoBufferKotlinFactory(BasicDMNToJavaTransformer transformer) {
        super(transformer);
    }

    @Override
    public String drgElementSignatureProto(TDRGElement element) {
        return String.format("%s: %s", requestVariableName(element), qualifiedRequestMessageName(element));
    }

    @Override
    protected String toNativeProtoType(String feelType) {
        return FEEL_TYPE_TO_NATIVE_PROTO_TYPE.get(feelType);
    }
}
