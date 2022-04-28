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
package com.gs.dmn.ast;

import com.gs.dmn.serialization.DMNMarshaller;
import com.gs.dmn.serialization.xstream.DMNMarshallerFactory;
import org.junit.Test;

import java.io.File;
import java.io.FileReader;

import static org.junit.Assert.assertNotNull;

public class DefaultDMNVisitorTest {
    private final DMNMarshaller marshaller = DMNMarshallerFactory.newDefaultMarshaller();
    private final Visitor visitor = new DefaultDMNVisitor();

    @Test
    public void visit() throws Exception {
        testVisit("xstream/v1_1/", "0001-input-data-string.dmn");
        testVisit("xstream/v1_2/", "ch11example.dmn");
        testVisit("xstream/v1_3/", "Chapter 11 Example.dmn");
    }

    private void testVisit(String subDir, String xmlFile) throws Exception {
        File baseInputDir = new File("target/test-classes/");

        File inputXMLFile = new File(baseInputDir, subDir + xmlFile);
        TDefinitions definitions = marshaller.unmarshal(new FileReader(inputXMLFile), true);
        definitions.accept(visitor, null);
        assertNotNull(definitions);
    }
}