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

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import java.io.File;
import java.io.OutputStream;

import static com.gs.dmn.serialization.DMNConstants.*;

public class DMNWriter extends DMNSerializer {
    protected static final JAXBContext JAXB_DMN_11_CONTEXT;
    protected static final JAXBContext JAXB_DMN_12_CONTEXT;

    static {
        try {
            JAXB_DMN_11_CONTEXT = JAXBContext.newInstance(DMN_11_PACKAGE);
            JAXB_DMN_12_CONTEXT = JAXBContext.newInstance(DMN_12_PACKAGE);
        } catch (JAXBException e) {
            throw new DMNRuntimeException("Cannot create JAXB Context", e);
        }
    }

    public DMNWriter(BuildLogger logger) {
        super(logger);
    }

    public void write(Object definitions, File output, DMNNamespacePrefixMapper namespacePrefixMapper) {
        try {
            Marshaller marshaller = makeMarshaller(namespacePrefixMapper);
            write(marshaller, definitions, output);
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
        Marshaller marshaller;
        if (version == DMNVersion.DMN_11) {
            marshaller = JAXB_DMN_11_CONTEXT.createMarshaller();
        } else if (version == DMNVersion.DMN_12) {
            marshaller = JAXB_DMN_12_CONTEXT.createMarshaller();
        } else {
            throw new RuntimeException(String.format("DMN version '%s' is not supported", version));
        }
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty("com.sun.xml.internal.bind.namespacePrefixMapper", namespacePrefixMapper);
        return marshaller;
    }

    private void write(Marshaller marshaller, Object definitions, File output) throws JAXBException {
        if (definitions instanceof org.omg.spec.dmn._20151101.model.TDefinitions) {
            QName qName = new QName(DMN_11_NS, "definitions");
            JAXBElement<org.omg.spec.dmn._20151101.model.TDefinitions> root = new JAXBElement<org.omg.spec.dmn._20151101.model.TDefinitions>(qName, org.omg.spec.dmn._20151101.model.TDefinitions.class, (org.omg.spec.dmn._20151101.model.TDefinitions) definitions);
            marshaller.marshal(root, output);
        } else if (definitions instanceof org.omg.spec.dmn._20180521.model.TDefinitions) {
            QName qName = new QName(DMN_12_NS, "definitions");
            JAXBElement<org.omg.spec.dmn._20180521.model.TDefinitions> root = new JAXBElement<org.omg.spec.dmn._20180521.model.TDefinitions>(qName, org.omg.spec.dmn._20180521.model.TDefinitions.class, (org.omg.spec.dmn._20180521.model.TDefinitions) definitions);
            marshaller.marshal(root, output);
        } else {
            throw new DMNRuntimeException(String.format("'%s' is not supported", definitions.getClass()));
        }
    }

    private void write(Marshaller marshaller, Object definitions, OutputStream output) throws JAXBException {
        if (definitions instanceof org.omg.spec.dmn._20151101.model.TDefinitions) {
            QName qName = new QName(DMN_11_NS, "definitions");
            JAXBElement<org.omg.spec.dmn._20151101.model.TDefinitions> root = new JAXBElement<org.omg.spec.dmn._20151101.model.TDefinitions>(qName, org.omg.spec.dmn._20151101.model.TDefinitions.class, (org.omg.spec.dmn._20151101.model.TDefinitions) definitions);
            marshaller.marshal(root, output);
        } else if (definitions instanceof org.omg.spec.dmn._20180521.model.TDefinitions) {
            QName qName = new QName(DMN_12_NS, "definitions");
            JAXBElement<org.omg.spec.dmn._20180521.model.TDefinitions> root = new JAXBElement<org.omg.spec.dmn._20180521.model.TDefinitions>(qName, org.omg.spec.dmn._20180521.model.TDefinitions.class, (org.omg.spec.dmn._20180521.model.TDefinitions) definitions);
            marshaller.marshal(root, output);
        } else {
            throw new DMNRuntimeException(String.format("'%s' is not supported", definitions.getClass()));
        }
    }
}
