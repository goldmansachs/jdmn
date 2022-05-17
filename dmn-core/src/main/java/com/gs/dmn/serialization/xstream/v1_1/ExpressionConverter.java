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

import com.gs.dmn.ast.TExpression;
import com.gs.dmn.ast.TUnaryTests;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public abstract class ExpressionConverter extends DMNElementConverter {
    public static final String TYPE_REF = "typeRef";

    public ExpressionConverter(XStream xstream) {
        super(xstream);
    }

    @Override
    protected void assignAttributes(HierarchicalStreamReader reader, Object parent) {
        super.assignAttributes(reader, parent);
        String typeRef = reader.getAttribute(TYPE_REF);

        if (typeRef != null) {
            ((TExpression) parent).setTypeRef(MarshallingUtils.parseQNameString(typeRef));
        }
    }

    @Override
    protected void writeAttributes(HierarchicalStreamWriter writer, Object parent) {
        super.writeAttributes(writer, parent);
        TExpression e = (TExpression) parent;

        if (!(e instanceof TUnaryTests) && e.getTypeRef() != null) {
            writer.addAttribute(TYPE_REF, MarshallingUtils.formatQName(e.getTypeRef()));
        }
    }
}
