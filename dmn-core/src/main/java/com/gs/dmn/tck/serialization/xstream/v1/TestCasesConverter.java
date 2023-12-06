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
package com.gs.dmn.tck.serialization.xstream.v1;

import com.gs.dmn.tck.ast.TCKBaseElement;
import com.gs.dmn.tck.ast.Labels;
import com.gs.dmn.tck.ast.TestCase;
import com.gs.dmn.tck.ast.TestCases;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class TestCasesConverter extends DMNBaseElementConverter {
    // Attributes
    static final String NAMESPACE = "namespace";

    // Elements
    static final String MODEL_NAME = "modelName";
    static final String LABELS = "labels";
    static final String TEST_CASE = "testCase";

    public TestCasesConverter(XStream xstream) {
        super(xstream);
    }

    @Override
    public boolean canConvert(Class type) {
        return type.equals(TestCases.class);
    }

    @Override
    protected TCKBaseElement createModelObject() {
        return new TestCases();
    }

    @Override
    protected void assignChildElement(Object parent, String nodeName, Object child) {
        TestCases element = (TestCases) parent;
        if (MODEL_NAME.equals(nodeName)) {
            element.setModelName((String) child);
        } else if (child instanceof Labels) {
            element.setLabels((Labels) child);
        } else if (child instanceof TestCase) {
            element.getTestCase().add((TestCase) child);
        } else {
            super.assignChildElement(element, nodeName, child);
        }
    }

    @Override
    protected void assignAttributes(HierarchicalStreamReader reader, Object parent) {
        super.assignAttributes(reader, parent);

        TestCases element = (TestCases) parent;
        element.setNamespace(reader.getAttribute(NAMESPACE));
    }

    @Override
    protected void writeChildren(HierarchicalStreamWriter writer, MarshallingContext context, Object parent) {
        super.writeChildren(writer, context, parent);

        TestCases element = (TestCases) parent;
        String modelName = element.getModelName();
        if (modelName != null) {
            writeChildrenNode(writer, context, modelName, MODEL_NAME);
        }
        Labels labels = element.getLabels();
        if (labels != null) {
            writeChildrenNode(writer, context, labels, LABELS);
        }
        for (TestCase testCase : element.getTestCase()) {
            writeChildrenNode(writer, context, testCase, TEST_CASE);
        }
    }

    @Override
    protected void writeAttributes(HierarchicalStreamWriter writer, Object parent) {
        super.writeAttributes(writer, parent);

        TestCases element = (TestCases) parent;
        String namespace = element.getNamespace();
        if (namespace != null) {
            writer.addAttribute(NAMESPACE, namespace);
        }
    }
}
