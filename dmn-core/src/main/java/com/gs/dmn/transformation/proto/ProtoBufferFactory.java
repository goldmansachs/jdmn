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
import com.gs.dmn.transformation.basic.BasicDMN2JavaTransformer;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;
import org.apache.commons.lang3.StringUtils;
import org.omg.spec.dmn._20180521.model.*;

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

public class ProtoBufferFactory {
    private static final Map<String, String> TIME_FEEL_TO_PROTO_TYPE = new LinkedHashMap<>();
    public static final String OPTIONAL = "optional";
    public static final String REPEATED = "repeated";

    public static final String REQUEST_VARIABLE_NAME = "request_";

    static {
        TIME_FEEL_TO_PROTO_TYPE.put(ENUMERATION.getName(), "string");
        TIME_FEEL_TO_PROTO_TYPE.put(YEARS_AND_MONTHS_DURATION.getName(), null);
        TIME_FEEL_TO_PROTO_TYPE.put(DAYS_AND_TIME_DURATION.getName(), null);
        TIME_FEEL_TO_PROTO_TYPE.put(DATE_AND_TIME.getName(), null);
        TIME_FEEL_TO_PROTO_TYPE.put(TIME.getName(), null);
        TIME_FEEL_TO_PROTO_TYPE.put(DATE.getName(), null);
        TIME_FEEL_TO_PROTO_TYPE.put(STRING.getName(), "string");
        TIME_FEEL_TO_PROTO_TYPE.put(BOOLEAN.getName(), "bool");
        TIME_FEEL_TO_PROTO_TYPE.put(NUMBER.getName(), "double");
        TIME_FEEL_TO_PROTO_TYPE.put(ANY.getName(), null);
    }

    private final BasicDMNToNativeTransformer transformer;
    private final DMNModelRepository repository;

    public ProtoBufferFactory(BasicDMN2JavaTransformer transformer) {
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
                FieldType fieldType = toProtoType(parameter.getRight());
                requestFields.add(new Field(fieldName, fieldType));
            }
            messageTypes.add(new MessageType(requestMessageName, requestFields));

            // Response
            String responseMessageName = responseMessageName(element);
            List<Field> responseFields = new ArrayList<>();
            Type type = this.transformer.drgElementOutputFEELType(element);
            responseFields.add(new Field(protoFieldName(element), toProtoType(type)));
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
    private FieldType protoType(TItemDefinition itemDefinition) {
        Type type = this.transformer.toFEELType(itemDefinition);
        FieldType protoType = toProtoType(type);
        return protoType;
    }

    private FieldType toProtoType(Type type) {
        String modifier = OPTIONAL;
        if (type instanceof AnyType) {
            return new FieldType(modifier, "Any");
        } else if (type instanceof NamedType) {
            String typeName = ((NamedType) type).getName();
            if (StringUtils.isBlank(typeName)) {
                throw new DMNRuntimeException(String.format("Missing type name in '%s'", type));
            }
            String primitiveType = toNativeType(typeName);
            if (!StringUtils.isBlank(primitiveType)) {
                return new FieldType(modifier, primitiveType);
            } else {
                if (type instanceof ItemDefinitionType) {
                    String qType = qualifiedItemDefinitionProtoName((ItemDefinitionType) type);
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
                FieldType fieldType = toProtoType(elementType);
                return new FieldType(REPEATED, fieldType.getType());
            }
        }
        throw new IllegalArgumentException(String.format("Type '%s' is not supported yet", type));
    }

    private String toNativeType(String feelType) {
        return TIME_FEEL_TO_PROTO_TYPE.get(feelType);
    }

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

    private String responseMessageName(TDRGElement element) {
        return protoElementName(element) + "Response";
    }

    private String protoElementName(TNamedElement element) {
        return this.transformer.upperCaseFirst(protoName(element.getName()));
    }

    private String protoFieldName(TNamedElement namedElement) {
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

    public String qualifiedItemDefinitionProtoName(TItemDefinition itemDefinition) {
        String protoName = this.transformer.upperCaseFirst(itemDefinition.getName());
        TDefinitions model = this.repository.getModel(itemDefinition);
        return qualifiedProtoName(protoName, model);
    }

    private String qualifiedItemDefinitionProtoName(ItemDefinitionType type) {
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
}
