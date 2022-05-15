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
package com.gs.dmn.tck.serialization;

import com.gs.dmn.serialization.AbstractUnmarshalMarshalTest;
import com.gs.dmn.serialization.diff.XMLDifferenceEvaluator;
import com.gs.dmn.tck.ast.TestCases;
import com.gs.dmn.tck.serialization.xstream.TCKMarshallerFactory;
import org.xmlunit.diff.DifferenceEvaluator;
import org.xmlunit.diff.DifferenceEvaluators;

import javax.xml.transform.stream.StreamSource;
import java.io.*;

public abstract class AbstractTCKUnmarshalMarshalTest extends AbstractUnmarshalMarshalTest<TestCases, TCKMarshaller> {
    @Override
    protected TCKMarshaller getMarshaller() {
        return TCKMarshallerFactory.newDefaultMarshaller();
    }

    @Override
    protected TestCases readModel(TCKMarshaller marshaller, FileInputStream fis) {
        TestCases definitions = marshaller.unmarshal(new InputStreamReader(fis), true);
        return definitions;
    }

    @Override
    protected void writeModel(TCKMarshaller marshaller, TestCases testCases, File outputDMNFile) throws IOException {
        try (FileWriter targetFos = new FileWriter(outputDMNFile)) {
            marshaller.marshal(testCases, targetFos);
        }
    }

    @Override
    protected StreamSource getSchemaSource() {
        return new StreamSource(AbstractTCKUnmarshalMarshalTest.class.getResource("/tck/testCases.xsd").getFile());
    }

    @Override
    protected DifferenceEvaluator makeTCKDifferenceEvaluator() {
        return DifferenceEvaluators.chain(DifferenceEvaluators.Default, XMLDifferenceEvaluator.tck1DiffEvaluator());
    }
}
