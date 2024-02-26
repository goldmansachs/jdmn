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
package com.gs.dmn.serialization.xstream.v1_4;

import com.gs.dmn.ast.TIterator;
import com.gs.dmn.ast.TTypedChildExpression;
import com.gs.dmn.serialization.DMNVersion;
import com.gs.dmn.serialization.xstream.v1_1.ExpressionConverter;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public abstract class IteratorConverter extends ExpressionConverter {
    public static final String IN = "in";
    public static final String ITERATOR_VARIABLE = "iteratorVariable";

    public IteratorConverter(XStream xstream, DMNVersion version) {
        super(xstream, version);
    }

    @Override
    protected void assignChildElement(Object parent, String nodeName, Object child) {
        TIterator i = (TIterator) parent;

        if (IN.equals(nodeName) && child instanceof TTypedChildExpression expression) {
            i.setIn(expression);
        } else {
            super.assignChildElement(parent, nodeName, child);
        }
    }

    @Override
    protected void assignAttributes(HierarchicalStreamReader reader, Object parent) {
        super.assignAttributes(reader, parent);
        String iteratorVariable = reader.getAttribute(ITERATOR_VARIABLE);

        if (iteratorVariable != null) {
            ((TIterator) parent).setIteratorVariable(iteratorVariable);
        }
    }

    @Override
    protected void writeChildren(HierarchicalStreamWriter writer, MarshallingContext context, Object parent) {
        super.writeChildren(writer, context, parent);
        TIterator i = (TIterator) parent;

        writeChildrenNode(writer, context, i.getIn(), IN);
    }
    @Override
    protected void writeAttributes(HierarchicalStreamWriter writer, Object parent) {
        super.writeAttributes(writer, parent);
        TIterator e = (TIterator) parent;

        if (e.getId() != null) {
            writer.addAttribute(ITERATOR_VARIABLE, e.getIteratorVariable());
        }
    }
}
