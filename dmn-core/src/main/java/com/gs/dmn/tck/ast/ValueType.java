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
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXTERNAL_PROPERTY, property = "@kind")
@JsonSubTypes({
        @JsonSubTypes.Type(name = "value", value = ValueType.class),
        @JsonSubTypes.Type(name = "component", value = Component.class),
        @JsonSubTypes.Type(name = "inputNode", value = InputNode.class)
})
@JsonPropertyOrder({
        "otherAttributes",
        "value",
        "component",
        "list",
        "extensionElements"
})
public class ValueType extends TCKBaseElement {
    public static boolean isSimpleValue(ValueType valueType) {
        return valueType != null && valueType.getValue() != null;
    }

    public static boolean isListValue(ValueType valueType) {
        return valueType != null && valueType.getList() != null;
    }

    public static boolean isComplexValue(ValueType valueType) {
        return valueType != null && valueType.getComponent() != null;
    }

    protected AnySimpleType value;
    protected java.util.List<Component> component;
    protected List list;
    protected ExtensionElements extensionElements;
    protected Map<QName, String> otherAttributes;

    public AnySimpleType getValue() {
        return value;
    }

    public void setValue(AnySimpleType value) {
        this.value = value;
    }

    public java.util.List<Component> getComponent() {
        if (component == null) {
            component = new ArrayList<>();
        }
        return this.component;
    }

    public List getList() {
        return list;
    }

    public void setList(List value) {
        this.list = value;
    }

    public ExtensionElements getExtensionElements() {
        return extensionElements;
    }

    public void setExtensionElements(ExtensionElements value) {
        this.extensionElements = value;
    }

    /**
     * Gets a map that contains attributes that aren't bound to any typed property on this class.
     *
     * <p>
     * the map is keyed by the name of the attribute and
     * the value is the string value of the attribute.
     *
     * the map returned by this method is live, and you can add new attribute
     * by updating the map directly. Because of this design, there's no setter.
     *
     *
     * @return
     *     always non-null
     */
    public Map<QName, String> getOtherAttributes() {
        if (otherAttributes == null) {
            this.otherAttributes = new LinkedHashMap<>();
        }
        return this.otherAttributes;
    }

    @Override
    public <C, R> R accept(Visitor<C, R> visitor, C context) {
        return visitor.visit(this, context);
    }
}
