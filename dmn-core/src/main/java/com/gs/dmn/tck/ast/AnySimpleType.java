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

import javax.xml.datatype.DatatypeFactory;
import javax.xml.namespace.QName;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.gs.dmn.serialization.DMNConstants.XSI_NS;

@JsonPropertyOrder({
        "test"
})
public class AnySimpleType extends DMNBaseElement {
    private static final DatatypeFactory DATATYPE_FACTORY;
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
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Map<QName, String> getOtherAttributes() {
        if (otherAttributes == null) {
            this.otherAttributes = new LinkedHashMap<>();
        }
        return this.otherAttributes;
    }

    public Object getValue() {
        String type = getType();
        String text = getText();
        if (type == null) {
            return this;
        } else if (type.endsWith("nil")) {
            return null;
        } else if (type.endsWith(":string")) {
            return text;
        } else if (type.endsWith(":boolean")) {
            return Boolean.valueOf(text);
        } else if (type.endsWith(":decimal")) {
            return new BigDecimal(text);
        } else if (type.endsWith(":double")) {
            return Double.valueOf(text);
        } else if (type.endsWith(":date")) {
            return DATATYPE_FACTORY.newXMLGregorianCalendar(text);
        } else if (type.endsWith(":time")) {
            return DATATYPE_FACTORY.newXMLGregorianCalendar(text);
        } else if (type.endsWith(":dateTime")) {
            return DATATYPE_FACTORY.newXMLGregorianCalendar(text);
        } else if (type.endsWith(":duration")) {
            return DATATYPE_FACTORY.newDuration(text);
        } else {
            throw new IllegalArgumentException(String.format("XML type '%s' is not supported yet", type));
        }
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
