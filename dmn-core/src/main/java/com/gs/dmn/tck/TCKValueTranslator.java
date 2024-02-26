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
package com.gs.dmn.tck;

import com.gs.dmn.ast.TDRGElement;
import com.gs.dmn.context.DMNContext;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.semantics.type.*;
import com.gs.dmn.feel.lib.StandardFEELLib;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.tck.ast.Component;
import com.gs.dmn.tck.ast.ValueType;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.*;
import java.util.stream.Collectors;

public class TCKValueTranslator<NUMBER, DATE, TIME, DATE_TIME, DURATION> extends TCKValueProcessor<NUMBER, DATE, TIME, DATE_TIME, DURATION> {
    public TCKValueTranslator(BasicDMNToNativeTransformer<Type, DMNContext> transformer, StandardFEELLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> feelLib) {
        super(transformer, feelLib);
    }

    //
    // Make java expressions from ValueType
    //
    public String toNativeExpression(ValueType valueType, Type type, TDRGElement element) {
        type = Type.extractTypeFromConstraint(type);
        if (valueType.getValue() != null) {
            Object value = anySimpleTypeValue(valueType.getValue());
            String text = getTextContent(value);
            if (text == null || "null".equals(text)) {
                return this.nativeFactory.nullLiteral();
            } else if (isNumber(value, type)) {
                return makeLiteral("number", text);
            } else if (isBoolean(value, type)) {
                return this.nativeFactory.booleanLiteral(text);
            } else if (isDate(value, type)) {
                return makeLiteral("date", text);
            } else if (isTime(value, type)) {
                return makeLiteral("time", text);
            } else if (isDateTime(value, type)) {
                return makeLiteral("dateAndTime", text);
            } else if (isDurationTime(value, type)) {
                return makeLiteral("duration", text);
            } else if (isString(value, type)) {
                // Last one to deal with xsd:string but different value
                return "\"%s\"".formatted(text.replace("\n", "\\n"));
            } else {
                throw new DMNRuntimeException("Cannot make value for input '%s' with type '%s'".formatted(valueType, type));
            }
        } else if (valueType.getList() != null) {
            return toNativeExpression(valueType.getList(), (ListType) type, element);
        } else if (valueType.getComponent() != null) {
            if (type instanceof ItemDefinitionType definitionType) {
                return toNativeExpression(valueType.getComponent(), definitionType, element);
            } else if (type instanceof ContextType contextType) {
                return toNativeExpression(valueType.getComponent(), contextType, element);
            } else {
                throw new DMNRuntimeException("Cannot make value for input '%s' with type '%s'".formatted(valueType, type));
            }
        }
        throw new DMNRuntimeException("Cannot make value for input '%s' with type '%s'".formatted(valueType, type));
    }

    private String makeLiteral(String function, String text) {
        return this.nativeFactory.makeBuiltinFunctionInvocation(function, "\"%s\"".formatted(text.trim()));
    }

    private String toNativeExpression(com.gs.dmn.tck.ast.List list, ListType listType, TDRGElement element) {
        List<String> javaList = new ArrayList<>();
        for (ValueType listValueType : list.getItem()) {
            Type elementType = listType.getElementType();
            String value = toNativeExpression(listValueType, elementType, element);
            javaList.add(value);
        }
        return this.nativeFactory.makeBuiltinFunctionInvocation("asList", String.join(", ", javaList));
    }

    private String toNativeExpression(List<Component> components, ItemDefinitionType type, TDRGElement element) {
        List<Pair<String, String>> argumentList = new ArrayList<>();
        Set<String> members = type.getMembers();
        Set<String> present = new LinkedHashSet<>();
        for (Component c : components) {
            String name = c.getName();
            Type memberType = type.getMemberType(name);
            String value = toNativeExpression(c, memberType, element);
            argumentList.add(new Pair<>(name, value));
            present.add(name);
        }
        // Add the missing members
        for (String member: members) {
            if (!present.contains(member)) {
                Pair<String, String> pair = new Pair<>(member, this.nativeFactory.nullLiteral());
                argumentList.add(pair);
            }
        }
        sortParameters(argumentList);
        String interfaceName = this.transformer.toNativeType(type);
        String arguments = argumentList.stream().map(Pair::getRight).collect(Collectors.joining(", "));
        return this.transformer.constructor(this.transformer.itemDefinitionNativeClassName(interfaceName), arguments);
    }

    private String toNativeExpression(List<Component> components, ContextType type, TDRGElement element) {
        // Initialized members
        List<Pair<String, String>> membersList = new ArrayList<>();
        for (Component c : components) {
            String name = c.getName();
            Type memberType = type.getMemberType(name);
            String value = toNativeExpression(c, memberType, element);
            membersList.add(new Pair<>(name, value));
        }
        // Use builder pattern in Context
        sortParameters(membersList);
        String builder = this.transformer.defaultConstructor(this.transformer.contextClassName());
        String parts = membersList.stream().map(a -> "add(\"%s\", %s)".formatted(a.getLeft(), a.getRight())).collect(Collectors.joining("."));
        return "%s.%s".formatted(builder, parts);
    }

    protected void sortParameters(List<Pair<String, String>> parameters) {
        parameters.sort(Comparator.comparing(Pair::getLeft));
    }

    private boolean isNumber(Object value, Type type) {
        if (value instanceof Number) {
            return true;
        }
        if (Type.isNull(type)) {
            return false;
        }
        return type == NumberType.NUMBER || Type.equivalentTo(type, ListType.NUMBER_LIST);
    }

    private boolean isString(Object value, Type type) {
        if (value instanceof String) {
            return true;
        }
        if (Type.isNull(type)) {
            return false;
        }
        return type == StringType.STRING || Type.equivalentTo(type, ListType.STRING_LIST);
    }

    private boolean isBoolean(Object value, Type type) {
        if (value instanceof Boolean) {
            return true;
        }
        if (Type.isNull(type)) {
            return false;
        }
        return type == BooleanType.BOOLEAN || Type.equivalentTo(type, ListType.BOOLEAN_LIST);
    }

    private boolean isDate(Object value, Type type) {
        if (value instanceof XMLGregorianCalendar calendar) {
            return calendar.getXMLSchemaType() == DatatypeConstants.DATE;
        }
        if (Type.isNull(type)) {
            return false;
        }
        return type == DateType.DATE || Type.equivalentTo(type, ListType.DATE_LIST);
    }

    private boolean isTime(Object value, Type type) {
        if (value instanceof XMLGregorianCalendar calendar) {
            return calendar.getXMLSchemaType() == DatatypeConstants.TIME;
        }
        if (Type.isNull(type)) {
            return false;
        }
        return type == TimeType.TIME || Type.equivalentTo(type, ListType.TIME_LIST);
    }

    private boolean isDateTime(Object value, Type type) {
        if (value instanceof XMLGregorianCalendar calendar) {
            return calendar.getXMLSchemaType() == DatatypeConstants.DATETIME;
        }
        if (Type.isNull(type)) {
            return false;
        }
        return type == DateTimeType.DATE_AND_TIME || Type.equivalentTo(type, ListType.DATE_AND_TIME_LIST);
    }

    private boolean isDurationTime(Object value, Type type) {
        if (value instanceof Duration) {
            return true;
        }
        if (Type.isNull(type)) {
            return false;
        }
        return type instanceof DurationType
                || Type.equivalentTo(type, ListType.DAYS_AND_TIME_DURATION_LIST)
                || Type.equivalentTo(type, ListType.YEARS_AND_MONTHS_DURATION_LIST);
    }
}
