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
package com.gs.dmn.tck;

import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.serialization.DMNNamespacePrefixMapper;
import org.omg.dmn.tck.marshaller._20160719.TestCases;

import javax.xml.bind.*;
import javax.xml.namespace.QName;
import java.io.File;
import java.net.URL;

import static com.gs.dmn.serialization.DMNConstants.TCK_NS;
import static com.gs.dmn.serialization.DMNConstants.TCK_PACKAGE;

public class TestCasesReader {
    public static final String TEST_FILE_EXTENSION = ".xml";
    private static final JAXBContext JAXB_CONTEXT;

    static {
        try {
            JAXB_CONTEXT = JAXBContext.newInstance(TCK_PACKAGE);
        } catch (JAXBException e) {
            throw new DMNRuntimeException("Cannot create JAXB Context", e);
        }
    }

    private final BuildLogger logger;

    public TestCasesReader(BuildLogger logger) {
        this.logger = logger;
    }

    public TestCases read(File file) {
        try {
            return read(file.toURI().toURL());
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot read DMN from '%s'", file.getPath()), e);
        }
    }

    public TestCases read(URL url) {
        try {
            logger.info(String.format("Reading TCK '%s' ...", url.toString()));

            Unmarshaller u = JAXB_CONTEXT.createUnmarshaller();

            TestCases testCases;
            Object obj = u.unmarshal(url);
            if (obj instanceof JAXBElement<?> ) {
                testCases = ((JAXBElement<TestCases>) obj).getValue();
            } else {
                testCases = (TestCases) obj;
            }

            logger.info("TCK read.");
            return testCases;
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot read TCK from '%s'", url.toString()), e);
        }
    }

    public void write(TestCases testCases, File file, DMNNamespacePrefixMapper namespacePrefixMapper) {
        try {
            Marshaller marshaller = JAXB_CONTEXT.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            if (namespacePrefixMapper != null) {
                marshaller.setProperty("com.sun.xml.internal.bind.namespacePrefixMapper", namespacePrefixMapper);
            }

            QName qName = new QName(TCK_NS, "testCases");
            JAXBElement<TestCases> root = new JAXBElement<TestCases>(qName, TestCases.class, testCases);

            marshaller.marshal(root, file);
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot write DMN to '%s'", file.getPath()), e);
        }
    }
}
