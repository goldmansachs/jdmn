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
package com.gs.dmn.serialization;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gs.dmn.ast.TDefinitions;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.serialization.xstream.DMNExtensionRegister;
import com.gs.dmn.serialization.xstream.DMNMarshallerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import static com.gs.dmn.serialization.DMNVersion.*;

public class DMNWriter extends DMNSerializer {
    public static final ObjectMapper JSON_MAPPER = makeJsonMapper();

    private final DMNMarshaller dmnMarshaller;

    private static ObjectMapper makeJsonMapper() {
        ObjectMapper objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .visibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
                .visibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE)
                .visibility(PropertyAccessor.SETTER, JsonAutoDetect.Visibility.NONE)
                .visibility(PropertyAccessor.CREATOR, JsonAutoDetect.Visibility.NONE)
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .enable(SerializationFeature.INDENT_OUTPUT)
                .build();

        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        return objectMapper;
    }

    public DMNWriter(BuildLogger logger) {
        this(logger, new ArrayList<>());
    }

    public DMNWriter(BuildLogger logger, List<DMNExtensionRegister> registers) {
        super(logger);
        this.dmnMarshaller = DMNMarshallerFactory.newMarshallerWithExtensions(registers);
    }

    public void writeAST(TDefinitions definitions, File output) {
        this.dmnMarshaller.marshal(definitions, output);
    }

    public void writeASTAsJson(TDefinitions definitions, File output, DMNNamespacePrefixMapper namespacePrefixMapper) {
        try (FileOutputStream fos = new FileOutputStream(output)) {
            JSON_MAPPER.writeValue(fos, definitions);
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot write DMN to '%s'", output.getPath()), e);
        }
    }

    public void write(Object definitions, File output, DMNNamespacePrefixMapper namespacePrefixMapper) {
        try (FileOutputStream fos = new FileOutputStream(output)) {
            write(definitions, fos, namespacePrefixMapper);
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot write DMN to '%s'", output.getPath()), e);
        }
    }

    public void write(Object definitions, OutputStream output, DMNNamespacePrefixMapper namespacePrefixMapper) {
        try {
            Marshaller marshaller = makeMarshaller(namespacePrefixMapper);
            write(marshaller, definitions, output);
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot write DMN to '%s'", output.toString()), e);
        }
    }

    private Marshaller makeMarshaller(DMNNamespacePrefixMapper namespacePrefixMapper) throws JAXBException {
        if (namespacePrefixMapper == null) {
            throw new DMNRuntimeException("Missing namespace prefix mapper");
        }

        DMNVersion version = namespacePrefixMapper.getVersion();
        JAXBContext context = getJAXBContext(version);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", namespacePrefixMapper);
        return marshaller;
    }

    private void write(Marshaller marshaller, Object definitions, OutputStream output) throws JAXBException {
        if (definitions instanceof org.omg.spec.dmn._20151101.model.TDefinitions) {
            QName qName = new QName(DMN_11.getNamespace(), "definitions");
            JAXBElement<org.omg.spec.dmn._20151101.model.TDefinitions> root = new JAXBElement<>(qName, org.omg.spec.dmn._20151101.model.TDefinitions.class, (org.omg.spec.dmn._20151101.model.TDefinitions) definitions);
            marshaller.marshal(root, output);
        } else if (definitions instanceof org.omg.spec.dmn._20180521.model.TDefinitions) {
            QName qName = new QName(DMN_12.getNamespace(), "definitions");
            JAXBElement<org.omg.spec.dmn._20180521.model.TDefinitions> root = new JAXBElement<>(qName, org.omg.spec.dmn._20180521.model.TDefinitions.class, (org.omg.spec.dmn._20180521.model.TDefinitions) definitions);
            marshaller.marshal(root, output);
        } else if (definitions instanceof org.omg.spec.dmn._20191111.model.TDefinitions) {
            QName qName = new QName(DMN_13.getNamespace(), "definitions");
            JAXBElement<org.omg.spec.dmn._20191111.model.TDefinitions> root = new JAXBElement<>(qName, org.omg.spec.dmn._20191111.model.TDefinitions.class, (org.omg.spec.dmn._20191111.model.TDefinitions) definitions);
            marshaller.marshal(root, output);
        } else {
            throw new DMNRuntimeException(String.format("'%s' is not supported", definitions.getClass()));
        }
    }
}
