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
package com.gs.dmn.tck.ast;

import com.gs.dmn.error.NopErrorHandler;
import com.gs.dmn.tck.serialization.TCKMarshaller;
import com.gs.dmn.tck.serialization.xstream.TCKMarshallerFactory;
import org.junit.Test;

import java.io.File;
import java.io.FileReader;

import static org.junit.Assert.*;

public class TraversalVisitorTest {
    private final TraversalVisitor<?> visitor = new TraversalVisitor<>(new NopErrorHandler());
    private final TCKMarshaller marshaller = TCKMarshallerFactory.newDefaultMarshaller();

    @Test
    public void visit() throws Exception {
        testVisit("dmn/input/1.1/", "0004-lending-test-01.xml");
        testVisit("dmn/input/1.2/", "0087-chapter-11-example-test-01.xml");
    }

    private void testVisit(String subDir, String xmlFile) throws Exception {
        File baseInputDir = new File("target/test-classes/");

        File inputXMLFile = new File(baseInputDir, subDir + xmlFile);
        TestCases testCases = marshaller.unmarshal(new FileReader(inputXMLFile), true);
        testCases.accept(visitor, null);
        assertNotNull(testCases);
    }
}