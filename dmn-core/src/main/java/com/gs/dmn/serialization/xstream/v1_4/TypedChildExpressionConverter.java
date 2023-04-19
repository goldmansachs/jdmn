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
import com.gs.dmn.ast.TTypedChildExpression;
import com.gs.dmn.serialization.DMNVersion;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class TypedChildExpressionConverter extends ChildExpressionConverter {
   public static final String TYPE_REF = "typeRef";

    public TypedChildExpressionConverter(XStream xstream, DMNVersion version) {
        super(xstream, version);
    }

    @Override
    public boolean canConvert(Class type) {
        return type.equals(TTypedChildExpression.class);
    }

    @Override
    protected DMNBaseElement createModelObject() {
        return new TTypedChildExpression();
    }

    @Override
    protected void assignAttributes(HierarchicalStreamReader reader, Object parent) {
        super.assignAttributes(reader, parent);
        String typeRef = reader.getAttribute(TYPE_REF);

        if (typeRef != null) {
            ((TTypedChildExpression) parent).setTypeRef(typeRef);
        }
    }

    @Override
    protected void writeAttributes(HierarchicalStreamWriter writer, Object parent) {
        super.writeAttributes(writer, parent);
        TTypedChildExpression e = (TTypedChildExpression) parent;

        if (e.getTypeRef() != null) {
            writer.addAttribute(TYPE_REF, e.getTypeRef());
        }
    }
}
