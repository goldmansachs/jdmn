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
package com.gs.dmn.serialization.xstream.v1_3;

import com.gs.dmn.ast.DMNBaseElement;
import com.gs.dmn.ast.TFunctionItem;
import com.gs.dmn.ast.TInformationItem;
import com.gs.dmn.serialization.DMNVersion;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class FunctionItemConverter extends DMNElementConverter {
    private static final String OUTPUT_TYPE_REF = "outputTypeRef";
    private static final String PARAMETERS = "parameters";

    public FunctionItemConverter(XStream xstream, DMNVersion version) {
        super(xstream, version);
    }

    @Override
    public boolean canConvert(Class clazz) {
        return clazz.equals(TFunctionItem.class);
    }

    @Override
    protected DMNBaseElement createModelObject() {
        return new TFunctionItem();
    }

    @Override
    protected void assignChildElement(Object parent, String nodeName, Object child) {
        TFunctionItem ii = (TFunctionItem) parent;

        if (PARAMETERS.equals(nodeName)) {
            ii.getParameters().add((TInformationItem) child);
        } else {
            super.assignChildElement(parent, nodeName, child);
        }
    }

    @Override
    protected void assignAttributes(HierarchicalStreamReader reader, Object parent) {
        super.assignAttributes(reader, parent);
        TFunctionItem ii = (TFunctionItem) parent;

        String typeRef = reader.getAttribute(OUTPUT_TYPE_REF);
        ii.setOutputTypeRef(DMNBaseConverter.parseQNameString(typeRef));
    }

    @Override
    protected void writeChildren(HierarchicalStreamWriter writer, MarshallingContext context, Object parent) {
        super.writeChildren(writer, context, parent);

        TFunctionItem ii = (TFunctionItem) parent;

        for (TInformationItem ic : ii.getParameters()) {
            writeChildrenNode(writer, context, ic, PARAMETERS);
        }
    }

    @Override
    protected void writeAttributes(HierarchicalStreamWriter writer, Object parent) {
        super.writeAttributes(writer, parent);
        TFunctionItem ii = (TFunctionItem) parent;

        if (ii.getOutputTypeRef() != null) {
            writer.addAttribute(OUTPUT_TYPE_REF, DMNBaseConverter.formatQName(ii.getOutputTypeRef(), ii));
        }
    }

}
