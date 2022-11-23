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

import com.gs.dmn.serialization.xstream.CustomStaxReader;
import com.gs.dmn.serialization.xstream.CustomStaxWriter;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import javax.xml.XMLConstants;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import java.util.Map;

public class QNameConverter implements Converter {
    @Override
    public boolean canConvert(Class clazz) {
        return clazz.equals(QName.class);
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        QName qname = DMNBaseConverter.parseQNameString(reader.getValue());
        CustomStaxReader customStaxReader = (CustomStaxReader) reader.underlyingReader();
        Map<String, String> currentNSCtx = customStaxReader.getElementInfo().getNsContext();
        String qnameURI = currentNSCtx.get(qname.getPrefix());
        if (qnameURI != null) {
            return new QName(qnameURI, qname.getLocalPart(), qname.getPrefix());
        }
        return qname;
    }

    @Override
    public void marshal(Object object, HierarchicalStreamWriter writer, MarshallingContext context) {
        QName qname = (QName) object;
        if (!XMLConstants.NULL_NS_URI.equals(qname.getNamespaceURI()) && !XMLConstants.DEFAULT_NS_PREFIX.equals(qname.getPrefix())) {
            CustomStaxWriter staxWriter = ((CustomStaxWriter) writer.underlyingWriter());
            try {
                staxWriter.writeNamespace(qname.getPrefix(), qname.getNamespaceURI());
            } catch (XMLStreamException e) {
                // TODO what to do?
                e.printStackTrace();
            }
        }
        writer.setValue(DMNBaseConverter.formatQName(qname));
    }
}