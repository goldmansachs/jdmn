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
import com.gs.dmn.ast.TAuthorityRequirement;
import com.gs.dmn.ast.TDMNElementReference;
import com.gs.dmn.ast.TKnowledgeSource;
import com.gs.dmn.serialization.DMNVersion;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class KnowledgeSourceConverter extends DRGElementConverter {
    public static final String OWNER = "owner";
    public static final String TYPE = "type";
    public static final String AUTHORITY_REQUIREMENT = "authorityRequirement";
    public static final String LOCATION_URI = "locationURI";

    public KnowledgeSourceConverter(XStream xstream, DMNVersion version) {
        super(xstream, version);
    }

    @Override
    public boolean canConvert(Class clazz) {
        return clazz.equals(TKnowledgeSource.class);
    }

    @Override
    protected DMNBaseElement createModelObject() {
        return new TKnowledgeSource();
    }

    @Override
    protected void assignChildElement(Object parent, String nodeName, Object child) {
        TKnowledgeSource ks = (TKnowledgeSource) parent;

        if (AUTHORITY_REQUIREMENT.equals(nodeName)) {
            ks.getAuthorityRequirement().add((TAuthorityRequirement) child);
        } else if (TYPE.equals(nodeName)) {
            ks.setType((String) child);
        } else if (OWNER.equals(nodeName)) {
            ks.setOwner((TDMNElementReference) child);
        } else {
            super.assignChildElement(parent, nodeName, child);
        }
    }

    @Override
    protected void assignAttributes(HierarchicalStreamReader reader, Object parent) {
        super.assignAttributes(reader, parent);
        TKnowledgeSource ks = (TKnowledgeSource) parent;

        String locationUri = reader.getAttribute(LOCATION_URI);

        ks.setLocationURI(locationUri);
    }

    @Override
    protected void writeChildren(HierarchicalStreamWriter writer, MarshallingContext context, Object parent) {
        super.writeChildren(writer, context, parent);
        TKnowledgeSource ks = (TKnowledgeSource) parent;

        for (TAuthorityRequirement ar : ks.getAuthorityRequirement()) {
            writeChildrenNode(writer, context, ar, AUTHORITY_REQUIREMENT);
        }
        if (ks.getType() != null) {
            writeChildrenNode(writer, context, ks.getType(), TYPE);
        }
        if (ks.getOwner() != null) {
            writeChildrenNode(writer, context, ks.getOwner(), OWNER);
        }
    }

    @Override
    protected void writeAttributes(HierarchicalStreamWriter writer, Object parent) {
        super.writeAttributes(writer, parent);
        TKnowledgeSource ks = (TKnowledgeSource) parent;

        if (ks.getLocationURI() != null) {
            writer.addAttribute(LOCATION_URI, ks.getLocationURI());
        }
    }
}
