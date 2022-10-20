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
package com.gs.dmn.tck.ast;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.gs.dmn.serialization.DMNConstants.XSI_NS;

@JsonPropertyOrder({
        "test"
})
public class AnySimpleType extends DMNBaseElement {
    public static AnySimpleType from(String localPart, String value, String prefix, String text) {
        AnySimpleType anySimpleType = new AnySimpleType();
        anySimpleType.getOtherAttributes().put(new QName(XSI_NS, localPart, prefix), value);
        anySimpleType.setText(text);
        return anySimpleType;
    }

    public static AnySimpleType from(String localPart, String value, String text) {
        return from(localPart, value, "xsd", text);
    }

    public static AnySimpleType of(Object value) {
        if (value == null) {
            AnySimpleType result = new AnySimpleType();
            result.setText("");
            result.getOtherAttributes().put(new QName(XSI_NS, "nil"), "true");
            return result;
        } else if (value instanceof String) {
            AnySimpleType result = new AnySimpleType();
            result.setText((String) value);
            result.getOtherAttributes().put(new QName(XSI_NS, "type"), "xsd:string");
            return result;
        } else if (value instanceof Boolean) {
            AnySimpleType result = new AnySimpleType();
            result.setText("" + value);
            result.getOtherAttributes().put(new QName(XSI_NS, "type"), "xsd:boolean");
            return result;
        } else if (value instanceof Float) {
            AnySimpleType result = new AnySimpleType();
            result.setText("" + value);
            result.getOtherAttributes().put(new QName(XSI_NS, "type"), "xsd:float");
            return result;
        } else if (value instanceof Double) {
            AnySimpleType result = new AnySimpleType();
            result.setText("" + value);
            result.getOtherAttributes().put(new QName(XSI_NS, "type"), "xsd:double");
            return result;
        } else if (value instanceof BigDecimal) {
            AnySimpleType result = new AnySimpleType();
            result.setText("" + value);
            result.getOtherAttributes().put(new QName(XSI_NS, "type"), "xsd:decimal");
            return result;
        } else if (value instanceof XMLGregorianCalendar) {
            AnySimpleType result = new AnySimpleType();
            result.setText(value.toString());
            QName type = ((XMLGregorianCalendar) value).getXMLSchemaType();
            if (type == DatatypeConstants.DATE) {
                result.getOtherAttributes().put(new QName(XSI_NS, "type"), "xsd:date");
            } else if (type == DatatypeConstants.TIME) {
                result.getOtherAttributes().put(new QName(XSI_NS, "type"), "xsd:time");
            } else if (type == DatatypeConstants.DATETIME) {
                result.getOtherAttributes().put(new QName(XSI_NS, "type"), "xsd:dateTime");
            }
            return result;
        } else if (value instanceof Duration) {
            AnySimpleType result = new AnySimpleType();
            result.setText(value.toString());
            result.getOtherAttributes().put(new QName(XSI_NS, "type"), "xsd:duration");
            return result;
        }
        throw new IllegalArgumentException(String.format("Not supported value '%s' yet", value.getClass().getSimpleName()));
    }

    static final DatatypeFactory DATATYPE_FACTORY;
    static {
        try {
            DATATYPE_FACTORY = DatatypeFactory.newInstance();
        } catch (Exception e) {
            throw new IllegalArgumentException("Cannot create xml factory");
        }
    }

    protected Map<QName, String> otherAttributes;
    protected String text;

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Map<QName, String> getOtherAttributes() {
        if (this.otherAttributes == null) {
            this.otherAttributes = new LinkedHashMap<>();
        }
        return this.otherAttributes;
    }

    public Object getValue() {
        String type = getType();
        if (type == null) {
            return this;
        } else if (type.endsWith("nil")) {
            return null;
        } else if (type.equals("string") || type.endsWith(":string")) {
            return this.text;
        } else if (type.equals("boolean") || type.endsWith(":boolean")) {
            return Boolean.valueOf(this.text);
        } else if (type.equals("float") || type.endsWith(":float")) {
            return Float.valueOf(this.text);
        } else if (type.equals("double") || type.endsWith(":double")) {
            return Double.valueOf(this.text);
        } else if (type.equals("decimal") || type.endsWith(":decimal")) {
            return new BigDecimal(this.text);
        } else if (type.equals("date") || type.endsWith(":date")) {
            return DATATYPE_FACTORY.newXMLGregorianCalendar(this.text);
        } else if (type.equals("time") || type.endsWith(":time")) {
            return DATATYPE_FACTORY.newXMLGregorianCalendar(this.text);
        } else if (type.equals("dateTime") || type.endsWith(":dateTime")) {
            return DATATYPE_FACTORY.newXMLGregorianCalendar(this.text);
        } else if (type.equals("duration") || type.endsWith(":duration")) {
            return DATATYPE_FACTORY.newDuration(this.text);
        } else {
            throw new IllegalArgumentException(String.format("XML type '%s' is not supported yet", type));
        }
    }

    public boolean isNil() {
        return "nil".equals(getType());
    }

    private String getType() {
        for (Map.Entry<QName, String> entry : this.otherAttributes.entrySet()) {
            QName qName = entry.getKey();
            if (XSI_NS.equals(qName.getNamespaceURI())) {
                if ("type".equals(qName.getLocalPart())) {
                    return entry.getValue();
                } else if ("nil".equals(qName.getLocalPart())) {
                    return "nil";
                }
            }
        }
        return null;
    }

    @Override
    public <C> Object accept(Visitor visitor, C context) {
        return visitor.visit(this, context);
    }
}
