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
package com.gs.dmn.serialization.xstream.extensions.kie;

import com.gs.dmn.ast.DMNBaseElement;
import com.gs.dmn.ast.TDecisionService;
import com.gs.dmn.serialization.DMNVersion;
import com.gs.dmn.serialization.xstream.v1_1.DMNBaseElementConverter;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class DecisionServicesConverter extends DMNBaseElementConverter {

    public static final String DECISION_SERVICE = "decisionService";

    public DecisionServicesConverter(XStream xstream, DMNVersion version) {
        super(xstream, version);
    }

    @Override
    protected DMNBaseElement createModelObject() {
        return new DecisionServices();
    }

    @Override
    public boolean canConvert(Class clazz) {
        return clazz.equals(DecisionServices.class);
    }

    @Override
    protected void assignChildElement(Object parent, String nodeName, Object child) {
        DecisionServices decisionServices = (DecisionServices) parent;

        if (nodeName.equals(DECISION_SERVICE)) {
            decisionServices.getDecisionService().add((TDecisionService) child);
        }
    }

    @Override
    protected void writeChildren(HierarchicalStreamWriter writer, MarshallingContext context, Object parent) {
        super.writeChildren(writer, context, parent);
        DecisionServices decisionServices = (DecisionServices) parent;

        for (TDecisionService ds : decisionServices.getDecisionService()) {
            writeChildrenNode(writer, context, ds, DECISION_SERVICE);
        }
    }

}
