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
import com.gs.dmn.feel.lib.StandardFEELLib;
import com.gs.dmn.runtime.Context;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.tck.ast.Component;
import com.gs.dmn.tck.ast.ValueType;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;
import org.apache.commons.lang3.StringUtils;

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;
import java.util.List;

public class TCKValueInterpreter<NUMBER, DATE, TIME, DATE_TIME, DURATION> extends TCKValueProcessor<NUMBER, DATE, TIME, DATE_TIME, DURATION> {
    public TCKValueInterpreter(BasicDMNToNativeTransformer<Type, DMNContext> transformer, StandardFEELLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> feelLib) {
        super(transformer, feelLib);
    }

    //
    // Make Java value from ValueType
    //
    public Object makeValue(ValueType valueType) {
        if (valueType.getValue() != null) {
            Object value = anySimpleTypeValue(valueType.getValue());
            String text = getTextContent(value);
            if (text == null) {
                return null;
            } else if (isNumber(value)) {
                return this.feelLib.number(text);
            } else if (isString(value)) {
                return text;
            } else if (isBoolean(value)) {
                if (StringUtils.isBlank(text)) {
                    return null;
                } else {
                    return Boolean.parseBoolean(text);
                }
            } else if (isDate(value)) {
                return this.feelLib.date(text);
            } else if (isTime(value)) {
                return this.feelLib.time(text);
            } else if (isDateTime(value)) {
                return this.feelLib.dateAndTime(text);
            } else if (isDurationTime(value)) {
                return this.feelLib.duration(text);
            } else {
                Object obj = anySimpleTypeValue(valueType.getValue());
                if (obj instanceof Number) {
                    obj = this.feelLib.number(obj.toString());
                }
                return obj;
            }
        } else if (valueType.getList() != null) {
            return makeList(valueType);
        } else if (valueType.getComponent() != null) {
            return makeContext(valueType);
        }
        throw new DMNRuntimeException(String.format("Cannot make value for input '%s'", valueType));
    }

    private List<?> makeList(ValueType valueType) {
        List<Object> javaList = new ArrayList<>();
        com.gs.dmn.tck.ast.List list = valueType.getList();
        for (ValueType listValueType : list.getItem()) {
            Object value = makeValue(listValueType);
            javaList.add(value);
        }
        return javaList;
    }

    private Context makeContext(ValueType valueType) {
        Context context = new Context();
        List<Component> components = valueType.getComponent();
        for (Component c : components) {
            String name = c.getName();
            Object value = makeValue(c);
            context.add(name, value);
        }
        return context;
    }

    private boolean isNumber(Object value) {
        return value instanceof Number;
    }

    private boolean isString(Object value) {
        return value instanceof String;
    }

    private boolean isBoolean(Object value) {
        return value instanceof Boolean;
    }

    private boolean isDate(Object value) {
        if (value instanceof XMLGregorianCalendar) {
            return ((XMLGregorianCalendar) value).getXMLSchemaType() == DatatypeConstants.DATE;
        } else {
            return false;
        }
    }

    private boolean isTime(Object value) {
        if (value instanceof XMLGregorianCalendar) {
            return ((XMLGregorianCalendar) value).getXMLSchemaType() == DatatypeConstants.TIME;
        } else {
            return false;
        }
    }

    private boolean isDateTime(Object value) {
        if (value instanceof XMLGregorianCalendar) {
            return ((XMLGregorianCalendar) value).getXMLSchemaType() == DatatypeConstants.DATETIME;
        } else {
            return false;
        }
    }

    private boolean isDurationTime(Object value) {
        return value instanceof Duration;
    }
}
