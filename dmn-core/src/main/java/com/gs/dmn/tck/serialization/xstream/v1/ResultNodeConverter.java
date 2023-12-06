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
import com.gs.dmn.tck.ast.ResultNode;
import com.gs.dmn.tck.ast.ValueType;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class ResultNodeConverter extends DMNBaseElementConverter {
    // Attributes
    private static final String TYPE = "type";
    private static final String NAMESPACE = "namespace";
    private static final String NAME = "name";
    private static final String CAST = "cast";
    private static final String ERROR_RESULT = "errorResult";

    // Elements
    static final String EXPECTED = "expected";
    static final String COMPUTED = "computed";

    public ResultNodeConverter(XStream xstream) {
        super(xstream);
    }

    @Override
    public boolean canConvert(Class type) {
        return type.equals(ResultNode.class);
    }

    @Override
    protected TCKBaseElement createModelObject() {
        return new ResultNode();
    }

    @Override
    protected void assignChildElement(Object parent, String nodeName, Object child) {
        ResultNode element = (ResultNode) parent;
        if (COMPUTED.equals(nodeName)) {
            element.setComputed((ValueType) child);
        } else if (EXPECTED.equals(nodeName)) {
            element.setExpected((ValueType) child);
        } else {
            super.assignChildElement(parent, nodeName, child);
        }
    }

    @Override
    protected void assignAttributes(HierarchicalStreamReader reader, Object parent) {
        super.assignAttributes(reader, parent);

        ResultNode element = (ResultNode) parent;
        element.setType(reader.getAttribute(TYPE));
        element.setNamespace(reader.getAttribute(NAMESPACE));
        element.setName(reader.getAttribute(NAME));
        element.setCast(reader.getAttribute(CAST));
        String errorResult = reader.getAttribute(ERROR_RESULT);
        if (errorResult != null) {
            element.setErrorResult(Boolean.valueOf(errorResult));
        }
    }

    @Override
    protected void writeChildren(HierarchicalStreamWriter writer, MarshallingContext context, Object parent) {
        super.writeChildren(writer, context, parent);

        ResultNode element = (ResultNode) parent;
        ValueType expected = element.getExpected();
        if (expected != null) {
            writeChildrenNode(writer, context, expected, EXPECTED);
        }
        ValueType computed = element.getComputed();
        if (computed != null) {
            writeChildrenNode(writer, context, computed, COMPUTED);
        }
    }

    @Override
    protected void writeAttributes(HierarchicalStreamWriter writer, Object parent) {
        super.writeAttributes(writer, parent);

        ResultNode element = (ResultNode) parent;
        String type = element.getType();
        if (type != null) {
            writer.addAttribute(TYPE, type);
        }
        String namespace = element.getNamespace();
        if (namespace != null) {
            writer.addAttribute(NAMESPACE, namespace);
        }
        String name = element.getName();
        if (name != null) {
            writer.addAttribute(NAME, name);
        }
        String cast = element.getCast();
        if (cast != null) {
            writer.addAttribute(CAST, cast);
        }
        Boolean errorResult = element.getErrorResultField();
        if (errorResult != null) {
            writer.addAttribute(ERROR_RESULT, errorResult.toString());
        }
    }
}
