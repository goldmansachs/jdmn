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

import com.gs.dmn.ast.DMNBaseElement;
import com.gs.dmn.ast.TDMNElementReference;
import com.gs.dmn.ast.TOrganizationUnit;
import com.gs.dmn.serialization.DMNVersion;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class OrganizationUnitConverter extends BusinessContextElementConverter {
    public static final String DECISION_OWNED = "decisionOwned";
    public static final String DECISION_MADE = "decisionMade";

    public OrganizationUnitConverter(XStream xstream, DMNVersion version) {
        super(xstream, version);
    }

    @Override
    public boolean canConvert(Class clazz) {
        return clazz.equals(TOrganizationUnit.class);
    }

    @Override
    protected DMNBaseElement createModelObject() {
        return new TOrganizationUnit();
    }

    @Override
    protected void assignChildElement(Object parent, String nodeName, Object child) {
        TOrganizationUnit ou = (TOrganizationUnit) parent;

        if (DECISION_MADE.equals(nodeName)) {
            ou.getDecisionMade().add((TDMNElementReference) child);
        } else if (DECISION_OWNED.equals(nodeName)) {
            ou.getDecisionOwned().add((TDMNElementReference) child);
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
        TOrganizationUnit ou = (TOrganizationUnit) parent;

        for (TDMNElementReference dm : ou.getDecisionMade()) {
            writeChildrenNode(writer, context, dm, DECISION_MADE);
        }
        for (TDMNElementReference downed : ou.getDecisionOwned()) {
            writeChildrenNode(writer, context, downed, DECISION_OWNED);
        }
    }

    @Override
    protected void writeAttributes(HierarchicalStreamWriter writer, Object parent) {
        super.writeAttributes(writer, parent);

        // no attributes.
    }
}
