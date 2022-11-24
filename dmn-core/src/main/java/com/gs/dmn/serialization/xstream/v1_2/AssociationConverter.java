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
import com.gs.dmn.ast.TAssociation;
import com.gs.dmn.ast.TAssociationDirection;
import com.gs.dmn.ast.TDMNElementReference;
import com.gs.dmn.serialization.DMNVersion;
import com.gs.dmn.serialization.xstream.v1_1.ArtifactConverter;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class AssociationConverter extends ArtifactConverter {
    public static final String TARGET_REF = "targetRef";
    public static final String SOURCE_REF = "sourceRef";
    public static final String ASSOCIATION_DIRECTION = "associationDirection";

    public AssociationConverter(XStream xstream, DMNVersion version) {
        super(xstream, version);
    }

    @Override
    public boolean canConvert(Class clazz) {
        return clazz.equals(TAssociation.class);
    }

    @Override
    protected DMNBaseElement createModelObject() {
        return new TAssociation();
    }

    @Override
    protected void assignChildElement(Object parent, String nodeName, Object child) {
        TAssociation a = (TAssociation) parent;

        if (SOURCE_REF.equals(nodeName)) {
            a.setSourceRef((TDMNElementReference) child);
        } else if (TARGET_REF.equals(nodeName)) {
            a.setTargetRef((TDMNElementReference) child);
        } else {
            super.assignChildElement(parent, nodeName, child);
        }
    }

    @Override
    protected void assignAttributes(HierarchicalStreamReader reader, Object parent) {
        super.assignAttributes(reader, parent);
        TAssociation a = (TAssociation) parent;

        String associationDirectionValue = reader.getAttribute(ASSOCIATION_DIRECTION);

        if (associationDirectionValue != null) {
            a.setAssociationDirection(TAssociationDirection.fromValue(associationDirectionValue));
        }
    }

    @Override
    protected void writeChildren(HierarchicalStreamWriter writer, MarshallingContext context, Object parent) {
        super.writeChildren(writer, context, parent);
        TAssociation a = (TAssociation) parent;

        writeChildrenNode(writer, context, a.getSourceRef(), SOURCE_REF);
        writeChildrenNode(writer, context, a.getTargetRef(), TARGET_REF);
    }

    @Override
    protected void writeAttributes(HierarchicalStreamWriter writer, Object parent) {
        super.writeAttributes(writer, parent);
        TAssociation a = (TAssociation) parent;

        if (a.getAssociationDirectionField() != null) {
            writer.addAttribute(ASSOCIATION_DIRECTION, a.getAssociationDirectionField().value());
        }
    }
}
