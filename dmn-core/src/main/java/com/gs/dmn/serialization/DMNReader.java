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

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.runtime.DMNRuntimeException;
import org.omg.spec.dmn._20180521.model.TDefinitions;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.net.URI;
import java.net.URL;

import static com.gs.dmn.serialization.DMNConstants.DMN_11_PACKAGE;
import static com.gs.dmn.serialization.DMNConstants.DMN_12_PACKAGE;

public class DMNReader extends DMNSerializer {
    protected static final JAXBContext JAXB_DMN_CONTEXT;

    static {
        try {
            JAXB_DMN_CONTEXT = JAXBContext.newInstance(String.format("%s:%s", DMN_12_PACKAGE, DMN_11_PACKAGE));
        } catch (JAXBException e) {
            throw new DMNRuntimeException("Cannot create JAXB Context", e);
        }
    }

    private final boolean validateSchema;
    private DMNDialectTransformer transformer = new DMNDialectTransformer(logger);

    public DMNReader(BuildLogger logger, boolean validateSchema) {
        super(logger);
        this.validateSchema = validateSchema;
    }

    public DMNModelRepository read(File input) {
        try {
            logger.info(String.format("Reading DMN '%s' ...", input.getAbsolutePath()));

            DMNModelRepository repository = transform(readObject(input));

            logger.info("DMN read.");
            return repository;
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot read DMN from '%s'", input.getAbsolutePath()), e);
        }
    }

    public DMNModelRepository read(InputStream input) {
        try {
            logger.info(String.format("Reading DMN '%s' ...", input.toString()));

            DMNModelRepository repository = transform(readObject(input));

            logger.info("DMN read.");
            return repository;
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot read DMN from '%s'", input.toString()), e);
        }
    }

    public DMNModelRepository read(URL input) {
        try {
            logger.info(String.format("Reading DMN '%s' ...", input.toString()));

            DMNModelRepository repository = transform(readObject(input));

            logger.info("DMN read.");
            return repository;
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot read DMN from '%s'", input.toString()), e);
        }
    }

    public DMNModelRepository read(Reader input) {
        try {
            logger.info(String.format("Reading DMN '%s' ...", input.toString()));

            DMNModelRepository repository = transform(readObject(input));

            logger.info("DMN read.");
            return repository;
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot read DMN from '%s'", input.toString()), e);
        }
    }

    private Unmarshaller makeUnmarshaller() throws Exception {
        Unmarshaller u = JAXB_DMN_CONTEXT.createUnmarshaller();
        if (validateSchema) {
            setSchema(u);
        }
        return u;
    }

    Object readObject(File input) throws Exception {
        Unmarshaller unmarshaller = makeUnmarshaller();
        JAXBElement<?> jaxbElement = (JAXBElement<?>) unmarshaller.unmarshal(input);
        return jaxbElement.getValue();
    }

    Object readObject(URL input) throws Exception {
        Unmarshaller unmarshaller = makeUnmarshaller();
        JAXBElement<?> jaxbElement = (JAXBElement<?>) unmarshaller.unmarshal(input);
        return jaxbElement.getValue();
    }

    Object readObject(InputStream input) throws Exception {
        Unmarshaller unmarshaller = makeUnmarshaller();
        JAXBElement<?> jaxbElement = (JAXBElement<?>) unmarshaller.unmarshal(input);
        return jaxbElement.getValue();
    }

    Object readObject(Reader input) throws Exception {
        Unmarshaller unmarshaller = makeUnmarshaller();
        JAXBElement<?> jaxbElement = (JAXBElement<?>) unmarshaller.unmarshal(input);
        return jaxbElement.getValue();
    }

    private DMNModelRepository transform(Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof org.omg.spec.dmn._20151101.model.TDefinitions) {
            return transformer.transformRepository((org.omg.spec.dmn._20151101.model.TDefinitions) value);
        } else if (value instanceof TDefinitions) {
            return new DMNModelRepository((TDefinitions) value, new PrefixNamespaceMappings());
        } else {
            throw new DMNRuntimeException(String.format("'%s' is not supported", value.getClass()));
        }
    }

    private void setSchema(Unmarshaller u) throws Exception {
        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        URI schemaURI = getClass().getClassLoader().getResource("dmn/dmn.xsd").toURI();
        Schema schema = sf.newSchema(schemaURI.toURL());
        u.setSchema(schema);
    }
}
