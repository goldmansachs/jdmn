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

import com.gs.dmn.tck.ast.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import java.util.List;

public class TestCaseConverter extends DMNBaseElementConverter {
    // Attributes
    private static final String ID = "id";
    private static final String TYPE = "type";
    private static final String NAMESPACE = "namespace";
    private static final String NAME = "name";
    private static final String INVOCABLE_NAME = "invocableName";

    // Elements
    static final String DESCRIPTION = "description";
    static final String INPUT_NODE = "inputNode";
    static final String RESULT_NODE = "resultNode";
    static final String EXTENSION_ELEMENTS = "extensionElements";

    public TestCaseConverter(XStream xstream) {
        super(xstream);
    }

    @Override
    public boolean canConvert(Class type) {
        return type.equals(TestCase.class);
    }

    @Override
    protected TCKBaseElement createModelObject() {
        return new TestCase();
    }

    @Override
    protected void assignChildElement(Object parent, String nodeName, Object child) {
        TestCase element = (TestCase) parent;
        if (DESCRIPTION.equals(nodeName)) {
            element.setDescription((String) child);
        } else if (child instanceof InputNode) {
            element.getInputNode().add((InputNode) child);
        } else if (child instanceof ResultNode) {
            element.getResultNode().add((ResultNode) child);
        } else {
            super.assignChildElement(element, nodeName, child);
        }
    }

    @Override
    protected void assignAttributes(HierarchicalStreamReader reader, Object parent) {
        super.assignAttributes(reader, parent);

        TestCase element = (TestCase) parent;
        element.setId(reader.getAttribute(ID));
        String type = reader.getAttribute(TYPE);
        if (type != null) {
            element.setType(TestCaseType.fromValue(type));
        }
        element.setNamespace(reader.getAttribute(NAMESPACE));
        element.setName(reader.getAttribute(NAME));
        element.setInvocableName(reader.getAttribute(INVOCABLE_NAME));
    }

    @Override
    protected void writeChildren(HierarchicalStreamWriter writer, MarshallingContext context, Object parent) {
        super.writeChildren(writer, context, parent);

        TestCase element = (TestCase) parent;
        String description = element.getDescription();
        if (description != null) {
            writeChildrenNode(writer, context, description, DESCRIPTION);
        }
        List<InputNode> inputNodes = element.getInputNode();
        for (InputNode node: inputNodes) {
            writeChildrenNode(writer, context, node, INPUT_NODE);
        }
        List<ResultNode> resultNodes = element.getResultNode();
        for (ResultNode node: resultNodes) {
            writeChildrenNode(writer, context, node, RESULT_NODE);
        }
    }

    @Override
    protected void writeAttributes(HierarchicalStreamWriter writer, Object parent) {
        super.writeAttributes(writer, parent);

        TestCase element = (TestCase) parent;

        String id = element.getId();
        if (id != null) {
            writer.addAttribute(ID, id);
        }
        TestCaseType type = element.getTypeField();
        if (type != null) {
            writer.addAttribute(TYPE, type.value());
        }
        String namespace = element.getNamespace();
        if (namespace != null) {
            writer.addAttribute(NAMESPACE, namespace);
        }
        String name = element.getName();
        if (name != null) {
            writer.addAttribute(NAME, name);
        }
        String invocableName = element.getInvocableName();
        if (invocableName != null) {
            writer.addAttribute(INVOCABLE_NAME, invocableName);
        }
    }
}
