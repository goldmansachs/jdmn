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

import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.Pair;
import org.omg.spec.dmn._20180521.model.TDefinitions;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.net.URI;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

public class DMNReader extends DMNSerializer {
    protected static final Map<DMNVersion, JAXBContext> JAXB_CONTEXTS = new LinkedHashMap<>();

    static {
        try {
            JAXB_CONTEXTS.put(DMNVersion.DMN_11, JAXBContext.newInstance(DMNVersion.DMN_11.getJavaPackage()));
            JAXB_CONTEXTS.put(DMNVersion.DMN_12, JAXBContext.newInstance(DMNVersion.DMN_12.getJavaPackage()));
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

    public Pair<TDefinitions, PrefixNamespaceMappings> read(File input) {
        try {
            logger.info(String.format("Reading DMN '%s' ...", input.getAbsolutePath()));

            Pair<TDefinitions, PrefixNamespaceMappings> result = transform(readObject(input));

            logger.info("DMN read.");
            return result;
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot read DMN from '%s'", input.getAbsolutePath()), e);
        }
    }

    public Pair<TDefinitions, PrefixNamespaceMappings> read(InputStream input) {
        try {
            logger.info(String.format("Reading DMN '%s' ...", input.toString()));

            Pair<TDefinitions, PrefixNamespaceMappings> result = transform(readObject(input));

            logger.info("DMN read.");
            return result;
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot read DMN from '%s'", input.toString()), e);
        }
    }

    public Pair<TDefinitions, PrefixNamespaceMappings> read(URL input) {
        try {
            logger.info(String.format("Reading DMN '%s' ...", input.toString()));

            Pair<TDefinitions, PrefixNamespaceMappings> result = transform(readObject(input));

            logger.info("DMN read.");
            return result;
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot read DMN from '%s'", input.toString()), e);
        }
    }

    public Pair<TDefinitions, PrefixNamespaceMappings> read(Reader input) {
        try {
            logger.info(String.format("Reading DMN '%s' ...", input.toString()));

            Pair<TDefinitions, PrefixNamespaceMappings> result = transform(readObject(input));

            logger.info("DMN read.");
            return result;
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot read DMN from '%s'", input.toString()), e);
        }
    }

    private Pair<TDefinitions, PrefixNamespaceMappings> transform(Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof org.omg.spec.dmn._20151101.model.TDefinitions) {
            return transformer.transformDefinitions((org.omg.spec.dmn._20151101.model.TDefinitions) value);
        } else if (value instanceof TDefinitions) {
            return new Pair<>((TDefinitions) value, new PrefixNamespaceMappings());
        } else {
            throw new DMNRuntimeException(String.format("'%s' is not supported", value.getClass()));
        }
    }

    public Object readObject(File input) throws Exception {
        DocumentBuilder builder = makeDocumentBuilder();
        Document doc = builder.parse(input);
        return readObject(doc);
    }

    public Object readObject(URL input) throws Exception {
        DocumentBuilder builder = makeDocumentBuilder();
        Document doc = builder.parse(input.openStream());
        return readObject(doc);
    }

    public Object readObject(InputStream input) throws Exception {
        DocumentBuilder builder = makeDocumentBuilder();
        Document doc = builder.parse(input);
        return readObject(doc);
    }

    public Object readObject(Reader input) throws Exception {
        DocumentBuilder builder = makeDocumentBuilder();
        Document doc = builder.parse(new InputSource(input));
        return readObject(doc);
    }

    public Object readObject(Document doc) throws Exception {
        DMNVersion dmnVersion = inferDMNVersion(doc);
        Unmarshaller unmarshaller = makeUnmarshaller(dmnVersion);
        JAXBElement<?> jaxbElement = (JAXBElement<?>) unmarshaller.unmarshal(doc);
        return jaxbElement.getValue();
    }

    private DocumentBuilder makeDocumentBuilder() throws Exception {
        DocumentBuilderFactory dbFactory = XMLUtil.makeDocumentBuilderFactory();
        dbFactory.setNamespaceAware(true);
        return dbFactory.newDocumentBuilder();
    }

    private DMNVersion inferDMNVersion(Document doc) {
        DMNVersion dmnVersion = null;
        Element definitions = doc.getDocumentElement();
        NamedNodeMap attributes = definitions.getAttributes();
        for (int i = 0; i < attributes.getLength(); i++) {
            Node item = attributes.item(i);
            String nodeValue = item.getNodeValue();
            for(DMNVersion version: DMNVersion.VALUES) {
                if (version.getNamespace().equals(nodeValue)) {
                    dmnVersion = version;
                    break;
                }
            }
        }
        if (dmnVersion == null) {
            throw new IllegalArgumentException(String.format("Cannot infer DMN version for input '%s'", doc.getDocumentURI()));
        }

        return dmnVersion;
    }

    private Unmarshaller makeUnmarshaller(DMNVersion dmnVersion) throws Exception {
        JAXBContext context = JAXB_CONTEXTS.get(dmnVersion);
        if (context == null) {
            throw new IllegalArgumentException(String.format("Cannot find context for '%s'", dmnVersion.getVersion()));
        }
        Unmarshaller u = context.createUnmarshaller();
        if (validateSchema) {
            setSchema(u, dmnVersion);
        }
        return u;
    }

    private void setSchema(Unmarshaller u, DMNVersion dmnVersion) throws Exception {
        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        for (DMNVersion version: DMNVersion.VALUES) {
            if (version == dmnVersion) {
                URI schemaURI = getClass().getClassLoader().getResource(version.getSchemaLocation()).toURI();
                Schema schema = sf.newSchema(schemaURI.toURL());
                u.setSchema(schema);
                break;
            }
        }
   }
}
