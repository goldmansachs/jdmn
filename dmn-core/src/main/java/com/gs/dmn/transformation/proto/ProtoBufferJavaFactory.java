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

import com.gs.dmn.transformation.basic.BasicDMNToJavaTransformer;
import org.omg.spec.dmn._20191111.model.TDRGElement;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.gs.dmn.feel.analysis.semantics.type.AnyType.ANY;
import static com.gs.dmn.feel.analysis.semantics.type.BooleanType.BOOLEAN;
import static com.gs.dmn.feel.analysis.semantics.type.DateTimeType.DATE_AND_TIME;
import static com.gs.dmn.feel.analysis.semantics.type.DateType.DATE;
import static com.gs.dmn.feel.analysis.semantics.type.DurationType.DAYS_AND_TIME_DURATION;
import static com.gs.dmn.feel.analysis.semantics.type.DurationType.YEARS_AND_MONTHS_DURATION;
import static com.gs.dmn.feel.analysis.semantics.type.EnumerationType.ENUMERATION;
import static com.gs.dmn.feel.analysis.semantics.type.NumberType.NUMBER;
import static com.gs.dmn.feel.analysis.semantics.type.StringType.STRING;
import static com.gs.dmn.feel.analysis.semantics.type.TimeType.TIME;

public class ProtoBufferJavaFactory extends ProtoBufferFactory {
    private static final Map<String, String> FEEL_TYPE_TO_NATIVE_PROTO_TYPE = new LinkedHashMap<>();

    static {
        FEEL_TYPE_TO_NATIVE_PROTO_TYPE.put(ENUMERATION.getName(), "String");
        FEEL_TYPE_TO_NATIVE_PROTO_TYPE.put(YEARS_AND_MONTHS_DURATION.getName(), "String");
        FEEL_TYPE_TO_NATIVE_PROTO_TYPE.put(DAYS_AND_TIME_DURATION.getName(), "String");
        FEEL_TYPE_TO_NATIVE_PROTO_TYPE.put(DATE_AND_TIME.getName(), "String");
        FEEL_TYPE_TO_NATIVE_PROTO_TYPE.put(TIME.getName(), "String");
        FEEL_TYPE_TO_NATIVE_PROTO_TYPE.put(DATE.getName(), "String");
        FEEL_TYPE_TO_NATIVE_PROTO_TYPE.put(STRING.getName(), "String");
        FEEL_TYPE_TO_NATIVE_PROTO_TYPE.put(BOOLEAN.getName(), "Boolean");
        FEEL_TYPE_TO_NATIVE_PROTO_TYPE.put(NUMBER.getName(), "Double");
        FEEL_TYPE_TO_NATIVE_PROTO_TYPE.put(ANY.getName(), "Object");
    }

    public ProtoBufferJavaFactory(BasicDMNToJavaTransformer transformer) {
        super(transformer);
    }

    @Override
    public String drgElementSignatureProto(TDRGElement element) {
        return String.format("%s %s", qualifiedRequestMessageName(element), requestVariableName(element));
    }

    @Override
    protected String toNativeProtoType(String feelType) {
        return FEEL_TYPE_TO_NATIVE_PROTO_TYPE.get(feelType);
    }
}
