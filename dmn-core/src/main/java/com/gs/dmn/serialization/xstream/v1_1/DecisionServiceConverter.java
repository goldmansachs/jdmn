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
package com.gs.dmn.serialization.xstream.v1_1;

import com.gs.dmn.ast.DMNBaseElement;
import com.gs.dmn.ast.TDMNElementReference;
import com.gs.dmn.ast.TDecisionService;
import com.gs.dmn.serialization.DMNVersion;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class DecisionServiceConverter extends NamedElementConverter {
    public static final String OUTPUT_DECISION = "outputDecision";
    public static final String ENCAPSULATED_DECISION = "encapsulatedDecision";
    public static final String INPUT_DECISION = "inputDecision";
    public static final String INPUT_DATA = "inputData";

    public DecisionServiceConverter(XStream xstream, DMNVersion version) {
        super(xstream, version);
    }

    @Override
    public boolean canConvert(Class clazz) {
        return clazz.equals(TDecisionService.class);
    }

    @Override
    protected DMNBaseElement createModelObject() {
        return new TDecisionService();
    }

    @Override
    protected void parseElements(HierarchicalStreamReader reader, UnmarshallingContext context, Object parent) {
        while (reader.hasMoreChildren()) {
            reader.moveDown();
            Object object;
            String nodeName = reader.getNodeName();
            if (nodeName.equals(INPUT_DATA)) {
                // Patch because the tag name inputData is used in both decision services and as a DRG Element
                TDMNElementReference ref = new TDMNElementReference();
                ref.setHref(reader.getAttribute("href"));
                object = ref;
            } else {
                // Default behaviour
                object = readBareItem(reader, context, null);
            }
            if (object instanceof DMNBaseElement element) {
                element.setParent((DMNBaseElement) parent);
                ((DMNBaseElement) parent).addChildren(element);
            }
            reader.moveUp();
            assignChildElement(parent, nodeName, object);
        }
    }

    @Override
    protected void assignChildElement(Object parent, String nodeName, Object child) {
        TDecisionService decisionService = (TDecisionService) parent;
        switch (nodeName) {
            case OUTPUT_DECISION:
                decisionService.getOutputDecision().add((TDMNElementReference) child);
                break;
            case ENCAPSULATED_DECISION:
                decisionService.getEncapsulatedDecision().add((TDMNElementReference) child);
                break;
            case INPUT_DECISION:
                decisionService.getInputDecision().add((TDMNElementReference) child);
                break;
            case INPUT_DATA:
                decisionService.getInputData().add((TDMNElementReference) child);
                break;
            default:
                super.assignChildElement(parent, nodeName, child);
        }
    }

    @Override
    protected void writeChildren(HierarchicalStreamWriter writer, MarshallingContext context, Object parent) {
        super.writeChildren(writer, context, parent);
        TDecisionService decisionService = (TDecisionService) parent;

        for (TDMNElementReference ref : decisionService.getOutputDecision()) {
            writeChildrenNode(writer, context, ref, OUTPUT_DECISION);
        }
        for (TDMNElementReference ref : decisionService.getEncapsulatedDecision()) {
            writeChildrenNode(writer, context, ref, ENCAPSULATED_DECISION);
        }
        for (TDMNElementReference ref : decisionService.getInputDecision()) {
            writeChildrenNode(writer, context, ref, INPUT_DECISION);
        }
        for (TDMNElementReference ref : decisionService.getInputData()) {
            writeChildrenNode(writer, context, ref, INPUT_DATA);
        }
    }
}
