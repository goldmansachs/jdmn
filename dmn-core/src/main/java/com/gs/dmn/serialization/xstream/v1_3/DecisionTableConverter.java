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

import com.gs.dmn.ast.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class DecisionTableConverter extends ExpressionConverter {
    public static final String RULE = "rule";
    public static final String OUTPUT = "output";
    public static final String INPUT = "input";
    public static final String HIT_POLICY = "hitPolicy";
    public static final String AGGREGATION = "aggregation";
    public static final String PREFERRED_ORIENTATION = "preferredOrientation";
    public static final String OUTPUT_LABEL = "outputLabel";
    public static final String ANNOTATION = "annotation";

    public DecisionTableConverter(XStream xstream) {
        super(xstream);
    }

    @Override
    public boolean canConvert(Class clazz) {
        return clazz.equals(TDecisionTable.class);
    }

    @Override
    protected DMNBaseElement createModelObject() {
        return new TDecisionTable();
    }

    @Override
    protected void assignChildElement(Object parent, String nodeName, Object child) {
        TDecisionTable dt = (TDecisionTable) parent;

        if (INPUT.equals(nodeName)) {
            dt.getInput().add((TInputClause) child);
        } else if (OUTPUT.equals(nodeName)) {
            dt.getOutput().add((TOutputClause) child);
        } else if (ANNOTATION.equals(nodeName)) {
            dt.getAnnotation().add((TRuleAnnotationClause) child);
        } else if (RULE.equals(nodeName)) {
            dt.getRule().add((TDecisionRule) child);
        } else {
            super.assignChildElement(parent, nodeName, child);
        }
    }

    @Override
    protected void assignAttributes(HierarchicalStreamReader reader, Object parent) {
        super.assignAttributes(reader, parent);
        TDecisionTable dt = (TDecisionTable) parent;

        String hitPolicyValue = reader.getAttribute(HIT_POLICY);
        String aggregationValue = reader.getAttribute(AGGREGATION);
        String preferredOrientationValue = reader.getAttribute(PREFERRED_ORIENTATION);
        String outputLabel = reader.getAttribute(OUTPUT_LABEL);

        if (hitPolicyValue != null) dt.setHitPolicy(THitPolicy.fromValue(hitPolicyValue));
        if (aggregationValue != null) dt.setAggregation(TBuiltinAggregator.fromValue(aggregationValue));
        if (preferredOrientationValue != null)
            dt.setPreferredOrientation(TDecisionTableOrientation.fromValue(preferredOrientationValue));
        dt.setOutputLabel(outputLabel);
    }

    @Override
    protected void writeChildren(HierarchicalStreamWriter writer, MarshallingContext context, Object parent) {
        super.writeChildren(writer, context, parent);
        TDecisionTable dt = (TDecisionTable) parent;

        for (TInputClause i : dt.getInput()) {
            writeChildrenNode(writer, context, i, INPUT);
        }
        for (TOutputClause o : dt.getOutput()) {
            writeChildrenNode(writer, context, o, OUTPUT);
        }
        for (TRuleAnnotationClause a : dt.getAnnotation()) {
            writeChildrenNode(writer, context, a, ANNOTATION);
        }
        for (TDecisionRule r : dt.getRule()) {
            writeChildrenNode(writer, context, r, RULE);
        }
    }

    @Override
    protected void writeAttributes(HierarchicalStreamWriter writer, Object parent) {
        super.writeAttributes(writer, parent);
        TDecisionTable dt = (TDecisionTable) parent;

        if (dt.getHitPolicy() != null) {
            writer.addAttribute(HIT_POLICY, dt.getHitPolicy().value());
        }
        if (dt.getAggregation() != null) {
            writer.addAttribute(AGGREGATION, dt.getAggregation().value());
        }
        if (dt.getPreferredOrientation() != null) {
            writer.addAttribute(PREFERRED_ORIENTATION, dt.getPreferredOrientation().value());
        }
        if (dt.getOutputLabel() != null) {
            writer.addAttribute(OUTPUT_LABEL, dt.getOutputLabel());
        }
    }
}
