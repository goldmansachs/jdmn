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

import com.gs.dmn.runtime.DMNRuntimeException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.net.URI;

import static com.gs.dmn.serialization.DMNConstants.DMN_11_PACKAGE;
import static com.gs.dmn.serialization.DMNConstants.TCK_PACKAGE;

public class SchemaValidator {
    private final File schemaLocation;
    private final String context;

    public SchemaValidator(File schemaLocation, String context) {
        this.schemaLocation = schemaLocation;
        this.context = context;
    }

    public void validateFile(File file, String extension) {
        if (file.isDirectory()) {
            for(File child: file.listFiles()) {
                validateFile(child, extension);
            }
        } else {
            if (file.getName().endsWith(extension)) {
                validateSchema(file);
            }
        }
    }

    private void validateSchema(File file) {
        try {
            JAXBContext jc = JAXBContext.newInstance(context);
            Unmarshaller u = jc.createUnmarshaller();
            setSchema(u, schemaLocation);

            u.unmarshal(file.toURI().toURL());
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Invalid schema for '%s'", file.getName()), e);
        }
    }

    private void setSchema(Unmarshaller u, File schemaLocation) throws Exception {
        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        URI schemaURI = schemaLocation.toURI();
        File schemaFile = new File(schemaURI.getPath());
        Schema schema = sf.newSchema(schemaFile);
        u.setSchema(schema);
    }

    public static void main(String[] args) {
        File rootFolder = new File("dmn-core/src/test/resources/tck");
        File dmnSchemaLocation = new File("dmn-core/src/main/resources/dmn/dmn.xsd");
        File testCasesSchemaLocation = new File("dmn-core/src/test/resources/tck/testCases.xsd");

        new SchemaValidator(dmnSchemaLocation, DMN_11_PACKAGE).validateFile(rootFolder, ".dmn");
        new SchemaValidator(testCasesSchemaLocation, TCK_PACKAGE).validateFile(rootFolder, ".xml");
    }
}
