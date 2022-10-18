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

import com.gs.dmn.DRGElementReference;
import com.gs.dmn.ast.TDRGElement;
import com.gs.dmn.ast.TInformationItem;
import com.gs.dmn.ast.TInputData;
import com.gs.dmn.ast.TItemDefinition;
import com.gs.dmn.context.DMNContext;
import com.gs.dmn.el.analysis.semantics.type.AnyType;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.semantics.type.*;
import com.gs.dmn.feel.lib.StandardFEELLib;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.tck.ast.AnySimpleType;
import com.gs.dmn.tck.ast.Component;
import com.gs.dmn.tck.ast.ValueType;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;
import org.apache.commons.lang3.StringUtils;

import javax.xml.namespace.QName;
import java.util.*;
import java.util.stream.Collectors;

public class MockTCKValueTranslator<NUMBER, DATE, TIME, DATE_TIME, DURATION> extends TCKValueTranslator<NUMBER, DATE, TIME, DATE_TIME, DURATION> {
    public MockTCKValueTranslator(BasicDMNToNativeTransformer<Type, DMNContext> transformer, StandardFEELLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> feelLib) {
        super(transformer, feelLib);
    }

    //
    // Make java expressions from ValueType
    //
    public String toNativeExpression(ValueType valueType, Type type, TDRGElement element) {
        TItemDefinition itemDefinition = findItemDefinition(element);
        return toNativeExpression(valueType, type, element, itemDefinition);
    }

    private String toNativeExpression(ValueType valueType, Type type, TDRGElement element, TItemDefinition itemDefinition) {
        AnySimpleType value = valueType.getValue();
        if (value != null) {
            String text = value.getText();
            String cleanText = removeWhiteSpaces(text);
            String path = makePath(cleanText, element);
            if (path !=  null) {
                return path;
            } else {
                if ("null".equals(text.trim()) || value.isNil()) {
                    return this.nativeFactory.nullLiteral();
                } else if (type == null) {
                    return text;
                } else if (type == StringType.STRING) {
                    return text;
                } else if (type == BooleanType.BOOLEAN) {
                    return this.nativeFactory.booleanLiteral(text);
                } else if (type == NumberType.NUMBER) {
                    text = text.trim();
                    if (text.startsWith("\"") && text.endsWith("\"")) {
                        text = text.substring(1, text.length() - 1);
                    }
                    if (element instanceof TInputData) {
                        if (this.transformer.isInteger(itemDefinition)) {
                            return this.transformer.makeIntegerForInput(text);
                        } else {
                            return this.transformer.makeDecimalForInput(text);
                        }
                    } else {
                        return this.transformer.makeDecimalForDecision(text);
                    }
                } else if (type == DateType.DATE) {
                    return this.transformer.makeDate(text);
                } else if (type == TimeType.TIME) {
                    return this.transformer.makeTime(text);
                } else if (type == DateTimeType.DATE_AND_TIME) {
                    return this.transformer.makeDateTime(text);
                } else if (type instanceof DurationType) {
                    return this.transformer.makeDuration(text);
                } else if (type == AnyType.ANY) {
                    return text;
                }

                throw new DMNRuntimeException(String.format("Cannot make value for input '%s' with type '%s'", valueType, type));
            }
        } else if (valueType.getList() != null) {
            return toNativeExpression(valueType.getList(), (ListType) type, element, itemDefinition);
        } else if (valueType.getComponent() != null) {
            if (type instanceof ItemDefinitionType) {
                return toNativeExpression(valueType.getComponent(), (ItemDefinitionType) type, element, itemDefinition);
            } else if (type instanceof ContextType) {
                return toNativeExpression(valueType.getComponent(), (ContextType) type, element, itemDefinition);
            } else {
                throw new DMNRuntimeException(String.format("Cannot make value for input '%s' with type '%s'", valueType, type));
            }
        }
        throw new DMNRuntimeException(String.format("Cannot make value for input '%s' with type '%s'", valueType, type));
    }

    private String toNativeExpression(com.gs.dmn.tck.ast.List list, ListType listType, TDRGElement element, TItemDefinition itemDefinition) {
        Type elementType = listType.getElementType();
        TItemDefinition elementItemDefinition = elementItemDefinition(itemDefinition);
        List<String> javaList = new ArrayList<>();
        for (ValueType listValueType : list.getItem()) {
            String value = toNativeExpression(listValueType, elementType, element, elementItemDefinition);
            javaList.add(value);
        }
        String args = String.join(", ", javaList);
        return this.nativeFactory.makeBuiltinFunctionInvocation("asList", args);
    }

    private String toNativeExpression(List<Component> components, CompositeDataType type, TDRGElement element, TItemDefinition itemDefinition) {
        // Build list of members
        List<Pair<String, String>> memberList = new ArrayList<>();
        Set<String> present = new LinkedHashSet<>();
        for (Component c : components) {
            String member = c.getName();
            Type memberType = type.getMemberType(member);
            TItemDefinition memberItemDefinition = memberItemDefinition(itemDefinition, member);
            String value = toNativeExpression(c, memberType, element, memberItemDefinition);
            memberList.add(new Pair<>(member, value));
            present.add(member);
        }
        // Add the missing members
        Set<String> members = type.getMembers();
        for (String member: members) {
            if (!present.contains(member)) {
                Type memberType = type.getMemberType(member);
                TItemDefinition memberItemDefinition = memberItemDefinition(itemDefinition, member);
                String value = this.transformer.getDefaultValue(memberType, memberItemDefinition);
                Pair<String, String> pair = new Pair<>(member, value);
                memberList.add(pair);
            }
        }
        sortParameters(memberList);
        if (type instanceof ItemDefinitionType) {
            // Use constructor
            String interfaceName = this.transformer.toNativeType((ItemDefinitionType) type);
            String arguments = memberList.stream().map(Pair::getRight).collect(Collectors.joining(", "));
            return this.transformer.constructor(this.transformer.itemDefinitionNativeClassName(interfaceName), arguments);
        } else {
            // Use builder pattern in Context
            String builder = this.transformer.defaultConstructor(this.transformer.contextClassName());
            String parts = memberList.stream().map(a -> String.format("add(\"%s\", %s)", a.getLeft(), a.getRight())).collect(Collectors.joining("."));
            return String.format("%s.%s", builder, parts);
        }
    }

    private TItemDefinition findItemDefinition(TDRGElement element) {
        TInformationItem variable = this.repository.variable(element);
        return findItemDefinition(variable == null ? null : variable.getTypeRef());
    }

    private TItemDefinition findItemDefinition(QName typeRef) {
        if (typeRef == null) {
            return null;
        } else {
            return this.repository.lookupItemDefinition(typeRef.getLocalPart());
        }
    }

    private TItemDefinition elementItemDefinition(TItemDefinition itemDefinition) {
        if (itemDefinition == null) {
            return null;
        }
        if (itemDefinition.getTypeRef() != null) {
            return findItemDefinition(itemDefinition.getTypeRef());
        } else if (!itemDefinition.getItemComponent().isEmpty()) {
            return itemDefinition;
        }
        return null;
    }

    private TItemDefinition memberItemDefinition(TItemDefinition itemDefinition, String member) {
        if (itemDefinition == null) {
            return null;
        }
        for (TItemDefinition child : itemDefinition.getItemComponent()) {
            if (member.equals(child.getName())) {
                return child;
            }
        }
        return null;
    }

    private String makePath(String cleanText, TDRGElement element) {
        if (StringUtils.isBlank(cleanText)) {
            return null;
        }

        String[] parts = cleanText.split("\\.");
        String childName = parts[0];
        if (childName == null) {
            return null;
        }
        List<DRGElementReference<? extends TDRGElement>> childRefs = this.repository.directDRGElements(element);
        Optional<DRGElementReference<? extends TDRGElement>> childRef = childRefs.stream().filter(c -> childName.equals(repository.name(c.getElement()))).findFirst();
        if (childRef.isPresent()) {
            TDRGElement childElement = childRef.get().getElement();
            Type childType = this.transformer.drgElementOutputFEELType(childElement);
            String nativeType = this.transformer.toNativeType(childType);
            return makePath(cleanText, nativeType);
        }
        return null;
    }

    private String makePath(String text, String childType) {
        if (StringUtils.isBlank(text)) {
            return null;
        }

        String cleanText = removeWhiteSpaces(text);
        String[] parts = cleanText.split("\\.");
        StringBuilder result = new StringBuilder();
        String root = String.format("%s.%s", TCKUtil.mockContextVariable(), this.transformer.contextGetter(parts[0]));
        result.append(this.nativeFactory.makeNullCheck(root, childType));
        for (int i=1; i<parts.length; i++) {
            result.append(String.format(".%s", this.transformer.getter(parts[i])));
        }

        return result.toString();
    }

    private String removeWhiteSpaces(String text) {
        return text.replaceAll("[ \t\n]", "");
    }
}
