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

import com.gs.dmn.ast.TChildExpression;
import com.gs.dmn.ast.TQuantified;
import com.gs.dmn.ast.TTypedChildExpression;
import com.gs.dmn.serialization.DMNVersion;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public abstract class QuantifiedConverter extends IteratorConverter {
    public static final String SATISFIES = "satisfies";

    public QuantifiedConverter(XStream xstream, DMNVersion version) {
        super(xstream, version);
    }

    @Override
    protected void assignChildElement(Object parent, String nodeName, Object child) {
        TQuantified i = (TQuantified) parent;

        if (SATISFIES.equals(nodeName) && child instanceof TChildExpression) {
            i.setSatisfies((TChildExpression) child);
        } else {
            super.assignChildElement(parent, nodeName, child);
        }
    }

    @Override
    protected void writeChildren(HierarchicalStreamWriter writer, MarshallingContext context, Object parent) {
        super.writeChildren(writer, context, parent);
        TQuantified i = (TQuantified) parent;

        writeChildrenNode(writer, context, i.getSatisfies(), SATISFIES);
    }

    @Override
    protected void parseElements(HierarchicalStreamReader reader, UnmarshallingContext context, Object parent) {
        mvDownConvertAnotherMvUpAssignChildElement(reader, context, parent, IN, TTypedChildExpression.class);
        mvDownConvertAnotherMvUpAssignChildElement(reader, context, parent, SATISFIES, TChildExpression.class);
    }
}
