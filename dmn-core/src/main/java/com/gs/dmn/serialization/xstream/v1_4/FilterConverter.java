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

import com.gs.dmn.ast.DMNBaseElement;
import com.gs.dmn.ast.TChildExpression;
import com.gs.dmn.ast.TExpression;
import com.gs.dmn.ast.TFilter;
import com.gs.dmn.serialization.DMNVersion;
import com.gs.dmn.serialization.xstream.v1_1.ExpressionConverter;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class FilterConverter extends ExpressionConverter {
    public static final String IN = "in";
    public static final String MATCH = "match";

    public FilterConverter(XStream xstream, DMNVersion version) {
        super(xstream, version);
    }

    @Override
    public String defineExpressionNodeName(TExpression e) {
        return "filter";
    }

    @Override
    public boolean canConvert(Class clazz) {
        return clazz.equals(TFilter.class);
    }

    @Override
    protected DMNBaseElement createModelObject() {
        return new TFilter();
    }

    @Override
    protected void assignChildElement(Object parent, String nodeName, Object child) {
        TFilter filter = (TFilter) parent;

        if (IN.equals(nodeName)) {
            filter.setIn((TChildExpression) child);
        } else if (MATCH.equals(nodeName)) {
            filter.setMatch((TChildExpression) child);
        } else {
            super.assignChildElement(parent, nodeName, child);
        }
    }

    @Override
    protected void writeChildren(HierarchicalStreamWriter writer, MarshallingContext context, Object parent) {
        super.writeChildren(writer, context, parent);
        TFilter filter = (TFilter) parent;
        writeChildrenNode(writer, context, filter.getIn(), IN);
        writeChildrenNode(writer, context, filter.getMatch(), MATCH);
    }

    protected void parseElements(HierarchicalStreamReader reader, UnmarshallingContext context, Object parent) {
        mvDownConvertAnotherMvUpAssignChildElement(reader, context, parent, IN, TChildExpression.class);
        mvDownConvertAnotherMvUpAssignChildElement(reader, context, parent, MATCH, TChildExpression.class);
    }
}
