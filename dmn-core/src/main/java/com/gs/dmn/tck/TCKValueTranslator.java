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
    public String toNativeExpression(ValueType valueType, Type type) {
        if (valueType.getValue() != null) {
            Object value = anySimpleTypeValue(valueType.getValue());
            String text = getTextContent(value);
            if (text == null || "null".equals(text)) {
                return "null";
            } else if (isNumber(value, type)) {
                return String.format("number(\"%s\")", text.trim());
            } else if (isBoolean(value, type)) {
                return text.trim();
            } else if (isDate(value, type)) {
                return String.format("date(\"%s\")", text.trim());
            } else if (isTime(value, type)) {
                return String.format("time(\"%s\")", text.trim());
            } else if (isDateTime(value, type)) {
                return String.format("dateAndTime(\"%s\")", text.trim());
            } else if (isDurationTime(value, type)) {
                return String.format("duration(\"%s\")", text.trim());
            } else if (isString(value, type)) {
                // Last one to deal with xsd:string but different value
                return String.format("\"%s\"", text.replace("\n", "\\n"));
            } else {
                throw new DMNRuntimeException(String.format("Cannot make value for input '%s' with type '%s'", valueType, type));
            }
        } else if (valueType.getList() != null) {
            return toNativeExpression(valueType.getList(), (ListType) type);
        } else if (valueType.getComponent() != null) {
            if (type instanceof ItemDefinitionType) {
                return toNativeExpression(valueType.getComponent(), (ItemDefinitionType) type);
            } else if (type instanceof ContextType) {
                return toNativeExpression(valueType.getComponent(), (ContextType) type);
            } else {
                throw new DMNRuntimeException(String.format("Cannot make value for input '%s' with type '%s'", valueType, type));
            }
        }
        throw new DMNRuntimeException(String.format("Cannot make value for input '%s' with type '%s'", valueType, type));
    }

    private String toNativeExpression(com.gs.dmn.tck.ast.List list, ListType listType) {
        List<String> javaList = new ArrayList<>();
        for (ValueType listValueType : list.getItem()) {
            Type elementType = listType.getElementType();
            String value = toNativeExpression(listValueType, elementType);
            javaList.add(value);
        }
        return String.format("asList(%s)", String.join(", ", javaList));
    }

    private String toNativeExpression(List<Component> components, ItemDefinitionType type) {
        List<Pair<String, String>> argumentList = new ArrayList<>();
        Set<String> members = type.getMembers();
        Set<String> present = new LinkedHashSet<>();
        for (Component c : components) {
            String name = c.getName();
            Type memberType = type.getMemberType(name);
            String value = toNativeExpression(c, memberType);
            argumentList.add(new Pair<>(name, value));
            present.add(name);
        }
        // Add the missing members
        for (String member: members) {
            if (!present.contains(member)) {
                Pair<String, String> pair = new Pair<>(member, "null");
                argumentList.add(pair);
            }
        }
        sortParameters(argumentList);
        String interfaceName = this.transformer.toNativeType(type);
        String arguments = argumentList.stream().map(Pair::getRight).collect(Collectors.joining(", "));
        return this.transformer.constructor(this.transformer.itemDefinitionNativeClassName(interfaceName), arguments);
    }

    private String toNativeExpression(List<Component> components, ContextType type) {
        // Initialized members
        List<Pair<String, String>> membersList = new ArrayList<>();
        for (Component c : components) {
            String name = c.getName();
            Type memberType = type.getMemberType(name);
            String value = toNativeExpression(c, memberType);
            membersList.add(new Pair<>(name, value));
        }
        // Use builder pattern in Context
        sortParameters(membersList);
        String builder = this.transformer.defaultConstructor(this.transformer.contextClassName());
        String parts = membersList.stream().map(a -> String.format("add(\"%s\", %s)", a.getLeft(), a.getRight())).collect(Collectors.joining("."));
        return String.format("%s.%s", builder, parts);
    }

    private void sortParameters(List<Pair<String, String>> parameters) {
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
        if (value instanceof XMLGregorianCalendar) {
            return ((XMLGregorianCalendar) value).getXMLSchemaType() == DatatypeConstants.DATE;
        }
        if (Type.isNull(type)) {
            return false;
        }
        return type == DateType.DATE || Type.equivalentTo(type, ListType.DATE_LIST);
    }

    private boolean isTime(Object value, Type type) {
        if (value instanceof XMLGregorianCalendar) {
            return ((XMLGregorianCalendar) value).getXMLSchemaType() == DatatypeConstants.TIME;
        }
        if (Type.isNull(type)) {
            return false;
        }
        return type == TimeType.TIME || Type.equivalentTo(type, ListType.TIME_LIST);
    }

    private boolean isDateTime(Object value, Type type) {
        if (value instanceof XMLGregorianCalendar) {
            return ((XMLGregorianCalendar) value).getXMLSchemaType() == DatatypeConstants.DATETIME;
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
