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

import com.gs.dmn.ast.*;
import com.gs.dmn.serialization.DMNVersion;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class BusinessKnowledgeModelConverter extends DRGElementConverter {
    public static final String ENCAPSULATED_LOGIC = "encapsulatedLogic";
    public static final String VARIABLE = "variable";
    public static final String KNOWLEDGE_REQUIREMENT = "knowledgeRequirement";
    public static final String AUTHORITY_REQUIREMENT = "authorityRequirement";

    public BusinessKnowledgeModelConverter(XStream xstream, DMNVersion version) {
        super(xstream, version);
    }

    @Override
    public boolean canConvert(Class clazz) {
        return clazz.equals(TBusinessKnowledgeModel.class);
    }

    @Override
    protected DMNBaseElement createModelObject() {
        return new TBusinessKnowledgeModel();
    }

    @Override
    protected void assignChildElement(Object parent, String nodeName, Object child) {
        TBusinessKnowledgeModel bkm = (TBusinessKnowledgeModel) parent;

        if (ENCAPSULATED_LOGIC.equals(nodeName)) {
            bkm.setEncapsulatedLogic((TFunctionDefinition) child);
        } else if (VARIABLE.equals(nodeName)) {
            bkm.setVariable((TInformationItem) child);
        } else if (KNOWLEDGE_REQUIREMENT.equals(nodeName)) {
            bkm.getKnowledgeRequirement().add((TKnowledgeRequirement) child);
        } else if (AUTHORITY_REQUIREMENT.equals(nodeName)) {
            bkm.getAuthorityRequirement().add((TAuthorityRequirement) child);
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
        TBusinessKnowledgeModel bkm = (TBusinessKnowledgeModel) parent;

        if (bkm.getEncapsulatedLogic() != null) {
            writeChildrenNode(writer, context, bkm.getEncapsulatedLogic(), ENCAPSULATED_LOGIC);
        }
        if (bkm.getVariable() != null) {
            writeChildrenNode(writer, context, bkm.getVariable(), VARIABLE);
        }
        for (TKnowledgeRequirement i : bkm.getKnowledgeRequirement()) {
            writeChildrenNode(writer, context, i, KNOWLEDGE_REQUIREMENT);
        }
        for (TAuthorityRequirement a : bkm.getAuthorityRequirement()) {
            writeChildrenNode(writer, context, a, AUTHORITY_REQUIREMENT);
        }
    }

    @Override
    protected void writeAttributes(HierarchicalStreamWriter writer, Object parent) {
        super.writeAttributes(writer, parent);

        // no attributes.
    }
}
