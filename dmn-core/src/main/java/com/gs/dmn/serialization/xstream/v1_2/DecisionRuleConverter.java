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
package com.gs.dmn.serialization.xstream.v1_2;

import com.gs.dmn.ast.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class DecisionRuleConverter extends DMNElementConverter {
    public static final String OUTPUT_ENTRY = "outputEntry";
    public static final String INPUT_ENTRY = "inputEntry";
    public static final String ANNOTATION_ENTRY = "annotationEntry";

    public DecisionRuleConverter(XStream xstream) {
        super(xstream);
    }

    @Override
    public boolean canConvert(Class clazz) {
        return clazz.equals(TDecisionRule.class);
    }

    @Override
    protected DMNBaseElement createModelObject() {
        return new TDecisionRule();
    }

    @Override
    protected void assignChildElement(Object parent, String nodeName, Object child) {
        TDecisionRule dr = (TDecisionRule) parent;

        if (INPUT_ENTRY.equals(nodeName)) {
            dr.getInputEntry().add((TUnaryTests) child);
        } else if (OUTPUT_ENTRY.equals(nodeName)) {
            dr.getOutputEntry().add((TLiteralExpression) child);
        } else if (ANNOTATION_ENTRY.equals(nodeName)) {
            dr.getAnnotationEntry().add((TRuleAnnotation) child);
        } else {
            super.assignChildElement(parent, nodeName, child);
        }
    }

    @Override
    protected void assignAttributes(HierarchicalStreamReader reader, Object parent) {
        super.assignAttributes(reader, parent);

        // no attributes.
    }

    @Override
    protected void writeChildren(HierarchicalStreamWriter writer, MarshallingContext context, Object parent) {
        super.writeChildren(writer, context, parent);
        TDecisionRule dr = (TDecisionRule) parent;

        for (TUnaryTests ie : dr.getInputEntry()) {
            writeChildrenNode(writer, context, ie, INPUT_ENTRY);
        }
        for (TLiteralExpression oe : dr.getOutputEntry()) {
            writeChildrenNode(writer, context, oe, OUTPUT_ENTRY);
        }
        for (TRuleAnnotation a : dr.getAnnotationEntry()) {
            writeChildrenNode(writer, context, a, ANNOTATION_ENTRY);
        }
    }

    @Override
    protected void writeAttributes(HierarchicalStreamWriter writer, Object parent) {
        super.writeAttributes(writer, parent);

        // no attributes.
    }
}
