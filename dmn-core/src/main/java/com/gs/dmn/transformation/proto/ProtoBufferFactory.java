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

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.feel.analysis.semantics.type.*;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.transformation.DMNToJavaTransformer;
import com.gs.dmn.transformation.basic.BasicDMNToJavaTransformer;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;
import org.apache.commons.lang3.StringUtils;
import org.omg.spec.dmn._20191111.model.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
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

public abstract class ProtoBufferFactory {
    private static final Map<String, String> FEEL_TYPE_TO_PROTO_TYPE = new LinkedHashMap<>();
    public static final String OPTIONAL = "optional";
    public static final String REPEATED = "repeated";

    private static final String REQUEST_VARIABLE_SUFFIX = "Request_";
    private static final String RESPONSE_VARIABLE_SUFFIX = "Response_";
    private static final String PROTO_VARIABLE_SUFFIX = "Proto_";

    static {
        FEEL_TYPE_TO_PROTO_TYPE.put(ENUMERATION.getName(), "string");
        FEEL_TYPE_TO_PROTO_TYPE.put(YEARS_AND_MONTHS_DURATION.getName(), "string");
        FEEL_TYPE_TO_PROTO_TYPE.put(DAYS_AND_TIME_DURATION.getName(), "string");
        FEEL_TYPE_TO_PROTO_TYPE.put(DATE_AND_TIME.getName(), "string");
        FEEL_TYPE_TO_PROTO_TYPE.put(TIME.getName(), "string");
        FEEL_TYPE_TO_PROTO_TYPE.put(DATE.getName(), "string");
        FEEL_TYPE_TO_PROTO_TYPE.put(STRING.getName(), "string");
        FEEL_TYPE_TO_PROTO_TYPE.put(BOOLEAN.getName(), "bool");
        FEEL_TYPE_TO_PROTO_TYPE.put(NUMBER.getName(), "double");
        FEEL_TYPE_TO_PROTO_TYPE.put(ANY.getName(), null);
    }

    private final BasicDMNToNativeTransformer transformer;
    private final DMNModelRepository repository;

    protected ProtoBufferFactory(BasicDMNToJavaTransformer transformer) {
        this.transformer = transformer;
        this.repository = transformer.getDMNModelRepository();
    }

    public Pair<Pair<List<MessageType>, List<MessageType>>, List<Service>> dmnToProto(TDefinitions definitions) {
        if (!(this.transformer.isGenerateProtoMessages() || this.transformer.isGenerateProtoServices())) {
            return null;
        }

        // Make messages for complex types
        List<MessageType> dataTypes = itemDefinitionsToMessageTypes(definitions);

        // Make Request and Response messages for every DRG Element
        List<MessageType> requestResponseTypes = drgElementsToMessageTypes(definitions);

        // Make services
        List<Service> services = new ArrayList<>();
        if (this.transformer.isGenerateProtoServices()) {
            services = drgElementsToServices(definitions);
        }

        return new Pair<>(new Pair<>(dataTypes, requestResponseTypes), services);
    }

    private List<MessageType> itemDefinitionsToMessageTypes(TDefinitions definitions) {
        List<MessageType> messageTypes = new ArrayList<>();
        List<TItemDefinition> itemDefinitions = this.repository.compositeItemDefinitions(definitions);
        for (TItemDefinition itemDefinition: itemDefinitions) {
            String messageName = protoElementName(itemDefinition);
            List<Field> fields = new ArrayList<>();
            for (TItemDefinition child: itemDefinition.getItemComponent()) {
                String fieldName = protoFieldName(child);
                FieldType fieldType = protoType(child);
                fields.add(new Field(fieldName, fieldType));
            }
            messageTypes.add(new MessageType(messageName, fields));
        }
        return messageTypes;
    }

    public List<MessageType> drgElementsToMessageTypes(TDefinitions definitions) {
        List<MessageType> messageTypes = new ArrayList<>();
        List<TDRGElement> drgElements = this.repository.findDRGElements(definitions);
        for (TDRGElement element: drgElements) {
            if (element instanceof TInputData) {
                continue;
            }

            // Request
            String requestMessageName = requestMessageName(element);
            List<Field> requestFields = new ArrayList<>();
            List<Pair<String, Type>> parameters = this.transformer.drgElementTypeSignature(element, this.transformer::nativeName);
            for (Pair<String, Type> parameter: parameters) {
                String fieldName = protoFieldName(parameter.getLeft());
                FieldType fieldType = toProtoFieldType(parameter.getRight());
                requestFields.add(new Field(fieldName, fieldType));
            }
            messageTypes.add(new MessageType(requestMessageName, requestFields));

            // Response
            String responseMessageName = responseMessageName(element);
            List<Field> responseFields = new ArrayList<>();
            Type type = this.transformer.drgElementOutputFEELType(element);
            responseFields.add(new Field(protoFieldName(element), toProtoFieldType(type)));
            messageTypes.add(new MessageType(responseMessageName, responseFields));
        }
        return messageTypes;
    }

    public List<Service> drgElementsToServices(TDefinitions definitions) {
        List<Service> services = new ArrayList<>();
        List<TDRGElement> drgElements = this.repository.findDRGElements(definitions);
        for (TDRGElement element: drgElements) {
            if (element instanceof TInputData) {
                continue;
            }

            // Add Service
            String serviceName = protoServiceName(element);
            services.add(new Service(serviceName, requestMessageName(element), responseMessageName(element)));
        }
        return services;
    }

    //
    // Types
    //
    public String toNativeProtoType(Type type) {
        if (type instanceof AnyType) {
            return "Object";
        } else if (type instanceof NamedType) {
            String typeName = ((NamedType) type).getName();
            if (StringUtils.isBlank(typeName)) {
                throw new DMNRuntimeException(String.format("Missing type name in '%s'", type));
            }
            String primitiveType = this.toNativeProtoType(typeName);
            if (!StringUtils.isBlank(primitiveType)) {
                return primitiveType;
            } else {
                if (type instanceof ItemDefinitionType) {
                    return qualifiedProtoMessageName((ItemDefinitionType) type);
                } else {
                    throw new DMNRuntimeException(String.format("Cannot infer platform type for '%s'", type));
                }
            }
        }  else if (type instanceof ListType) {
            Type elementType = ((ListType) type).getElementType();
            if (elementType instanceof AnyType) {
                return this.transformer.makeListType(DMNToJavaTransformer.LIST_TYPE);
            } else {
                String fieldType = toNativeProtoType(elementType);
                return this.transformer.makeListType(DMNToJavaTransformer.LIST_TYPE, fieldType);
            }
        }
        throw new IllegalArgumentException(String.format("Type '%s' is not supported yet", type));
    }

    private FieldType protoType(TItemDefinition itemDefinition) {
        Type type = this.transformer.toFEELType(itemDefinition);
        return toProtoFieldType(type);
    }

    private FieldType toProtoFieldType(Type type) {
        String modifier = OPTIONAL;
        if (type instanceof AnyType) {
            return new FieldType(modifier, "Any");
        } else if (type instanceof NamedType) {
            String typeName = ((NamedType) type).getName();
            if (StringUtils.isBlank(typeName)) {
                throw new DMNRuntimeException(String.format("Missing type name in '%s'", type));
            }
            String primitiveType = toProtoType(typeName);
            if (!StringUtils.isBlank(primitiveType)) {
                return new FieldType(modifier, primitiveType);
            } else {
                if (type instanceof ItemDefinitionType) {
                    String qType = qualifiedProtoMessageName((ItemDefinitionType) type);
                    return new FieldType(modifier, qType);
                } else {
                    throw new DMNRuntimeException(String.format("Cannot infer platform type for '%s'", type));
                }
            }
        }  else if (type instanceof ListType) {
            Type elementType = ((ListType) type).getElementType();
            if (elementType instanceof AnyType) {
                return new FieldType(REPEATED, "Any");
            } else {
                FieldType fieldType = toProtoFieldType(elementType);
                return new FieldType(REPEATED, fieldType.getType());
            }
        }
        throw new IllegalArgumentException(String.format("Type '%s' is not supported yet", type));
    }

    private String toProtoType(String feelType) {
        return FEEL_TYPE_TO_PROTO_TYPE.get(feelType);
    }

    protected abstract String toNativeProtoType(String feelType);

    //
    // Proto accessors
    //
    public String protoGetter(String name, Type type) {
        String protoName = protoName(name);
        if (type instanceof ListType) {
            return String.format("%sList()", this.transformer.getterName(protoName));
        } else {
            return this.transformer.getter(protoName);
        }
    }

    public String protoSetter(String name, Type type) {
        String protoName = protoName(name);
        if (type instanceof ListType) {
            return String.format("addAll%s", StringUtils.capitalize(protoName));
        } else {
            return this.transformer.setter(protoName);
        }
    }

    //
    // Simple Names
    //
    private String protoServiceName(TDRGElement element) {
        return protoElementName(element) +  "Service";
    }

    private String requestMessageName(TDRGElement element) {
        return protoElementName(element) + "Request";
    }

    public String requestVariableName(TDRGElement element) {
        return this.transformer.namedElementVariableName(element) + ProtoBufferFactory.REQUEST_VARIABLE_SUFFIX;
    }

    public String responseVariableName(TDRGElement element) {
        return this.transformer.namedElementVariableName(element) + ProtoBufferFactory.RESPONSE_VARIABLE_SUFFIX;
    }

    private String responseMessageName(TDRGElement element) {
        return protoElementName(element) + "Response";
    }

    public String namedElementVariableNameProto(TNamedElement element) {
        return this.transformer.namedElementVariableName(element) + ProtoBufferFactory.PROTO_VARIABLE_SUFFIX;
    }

    private String protoElementName(TNamedElement element) {
        return this.transformer.upperCaseFirst(protoName(element.getName()));
    }

    public String protoFieldName(TNamedElement namedElement) {
        return this.protoFieldName(namedElement.getName());
    }

    private String protoFieldName(String name) {
        return this.transformer.lowerCaseFirst(protoName(name));
    }

    public String protoName(String name) {
        return name.replace('_', ' ');
    }

    //
    // Qualified Names
    //
    private String qualifiedProtoServiceName(TDRGElement element) {
        return qualifiedProtoName(protoServiceName(element), element);
    }

    public String qualifiedRequestMessageName(TDRGElement element) {
        return qualifiedProtoName(requestMessageName(element), element);
    }

    public String qualifiedResponseMessageName(TDRGElement element) {
        return qualifiedProtoName(responseMessageName(element), element);
    }

    private String qualifiedProtoName(String protoName, TNamedElement element) {
        TDefinitions model = this.repository.getModel(element);
        return qualifiedProtoName(protoName, model);
    }

    public String qualifiedProtoMessageName(TItemDefinition itemDefinition) {
        String protoName = this.transformer.upperCaseFirst(itemDefinition.getName());
        TDefinitions model = this.repository.getModel(itemDefinition);
        return qualifiedProtoName(protoName, model);
    }

    private String qualifiedProtoMessageName(ItemDefinitionType type) {
        String protoName = this.transformer.upperCaseFirst(type.getName());
        String modelName = type.getModelName();
        return qualifiedProtoName(protoName, modelName);
    }

    private String qualifiedProtoName(String protoName, TDefinitions model) {
        return qualifiedProtoName(protoName, model == null ? null : model.getName());
    }

    private String qualifiedProtoName(String protoName, String modelName) {
        String nativePackage = this.transformer.nativeModelPackageName(modelName);
        String protoPackage = this.transformer.protoPackage(nativePackage);
        return this.transformer.qualifiedName(protoPackage, protoName);
    }

    public abstract String drgElementSignatureProto(TDRGElement element);

    public String drgElementArgumentListProto(TDRGElement element) {
        return requestVariableName(element);
    }
}
