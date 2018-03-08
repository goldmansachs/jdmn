/**
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

import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.runtime.DMNRuntimeException;
import org.omg.spec.dmn._20151101.dmn.TDefinitions;

import javax.xml.XMLConstants;
import javax.xml.bind.*;
import javax.xml.namespace.QName;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.Reader;
import java.net.URI;
import java.net.URL;

public class DMNReader {
    public static final String DMN_FILE_EXTENSION = ".dmn";
    static final String CONTEXT_PATH = "org.omg.spec.dmn._20151101.dmn";
    static final String schemaVersion = "1.1";
    private static final JAXBContext JAXB_CONTEXT;

    static {
        try {
            JAXB_CONTEXT = JAXBContext.newInstance(CONTEXT_PATH);
        } catch (JAXBException e) {
            throw new DMNRuntimeException("Cannot create JAXB Context", e);
        }
    }

    private final BuildLogger logger;
    private final boolean validateSchema;

    public DMNReader(BuildLogger logger, boolean validateSchema) {
        this.logger = logger;
        this.validateSchema = validateSchema;
    }

    public TDefinitions read(File file) {
        try {
            return read(file.toURI().toURL());
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot read DMN from '%s'", file.getPath()), e);
        }
    }

    public TDefinitions read(URL url) {
        try {
            logger.info(String.format("Reading DMN '%s' ...", url.toString()));

            Unmarshaller u = JAXB_CONTEXT.createUnmarshaller();
            if (validateSchema) {
                setSchema(u);
            }

            JAXBElement<?> jaxbElement = (JAXBElement<?>) u.unmarshal(url);
            TDefinitions definitions = (TDefinitions) jaxbElement.getValue();

            logger.info("DMN read.");
            return definitions;
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot read DMN from '%s'", url.toString()), e);
        }
    }

    public TDefinitions read(Reader reader) {
        try {
            logger.info(String.format("Reading DMN '%s' ...", reader.toString()));

            Unmarshaller u = JAXB_CONTEXT.createUnmarshaller();
            if (validateSchema) {
                setSchema(u);
            }

            JAXBElement<?> jaxbElement = (JAXBElement<?>) u.unmarshal(reader);
            TDefinitions definitions = (TDefinitions) jaxbElement.getValue();

            logger.info("DMN read.");
            return definitions;
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot read DMN from '%s'", reader.toString()), e);
        }
    }

    public void write(TDefinitions definitions, File file, DMNNamespacePrefixMapper namespacePrefixMapper) {
        try {
            Marshaller marshaller = JAXB_CONTEXT.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            if (namespacePrefixMapper != null) {
                marshaller.setProperty("com.sun.xml.internal.bind.namespacePrefixMapper", namespacePrefixMapper);
            }

            QName qName = new QName(DMNNamespacePrefixMapper.DMN_NS, "definitions");
            JAXBElement<TDefinitions> root = new JAXBElement<TDefinitions>(qName, TDefinitions.class, definitions);

            marshaller.marshal(root, file);
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot write DMN to '%s'", file.getPath()), e);
        }
    }

    private void setSchema(Unmarshaller u) throws Exception {
        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        URI schemaURI = getClass().getClassLoader().getResource("dmn/" + schemaVersion + "/dmn.xsd").toURI();
        Schema schema = sf.newSchema(schemaURI.toURL());
        u.setSchema(schema);
    }
}
