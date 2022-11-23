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
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.collections.AbstractCollectionConverter;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;

import javax.xml.XMLConstants;
import javax.xml.namespace.QName;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class DMNBaseConverter extends AbstractCollectionConverter {
    private final static Pattern QNAME_PAT = Pattern.compile("(\\{([^\\}]*)\\})?(([^:]*):)?(.*)");

    public static QName parseQNameString(String qns) {
        if (qns != null) {
            Matcher m = QNAME_PAT.matcher(qns);
            if (m.matches()) {
                String namespaceURI = m.group(2);
                String prefix = m.group(4);
                String localPart = m.group(5);
                if (prefix != null) {
                    return new QName(namespaceURI, localPart, prefix);
                } else {
                    return new QName(namespaceURI, localPart);
                }
            } else {
                return new QName(qns);
            }
        } else {
            return null;
        }
    }

    public static String formatQName(QName qname) {
        if (!XMLConstants.DEFAULT_NS_PREFIX.equals(qname.getPrefix())) {
            return qname.getPrefix() + ":" + qname.getLocalPart();
        } else {
            return qname.toString();
        }
    }

    public static String defineExpressionNodeName(TExpression e) {
        String nodeName = "expression";
        if (e instanceof TContext) {
            nodeName = "context";
        } else if (e instanceof TDecisionTable) {
            nodeName = "decisionTable";
        } else if (e instanceof TFunctionDefinition) {
            nodeName = "functionDefinition";
        } else if (e instanceof TInvocation) {
            nodeName = "invocation";
        } else if (e instanceof TLiteralExpression) {
            nodeName = "literalExpression";
        } else if (e instanceof TRelation) {
            nodeName = "relation";
        } else if (e instanceof TList) {
            nodeName = "list";
        }
        return nodeName;
    }

    private final DMNVersion version;

    public DMNBaseConverter(Mapper mapper, DMNVersion version) {
        super(mapper);
        this.version = version;
    }

    @Override
    public void marshal(Object object, HierarchicalStreamWriter writer, MarshallingContext context) {
        writeAttributes(writer, object);
        writeChildren(writer, context, object);
    }

    protected void writeChildrenNode(HierarchicalStreamWriter writer, MarshallingContext context, Object node, String nodeAlias) {
        writer.startNode(nodeAlias);
        context.convertAnother(node);
        writer.endNode();
    }

    protected void writeChildrenNodeAsValue(HierarchicalStreamWriter writer, MarshallingContext context, String nodeValue, String nodeAlias) {
        writer.startNode(nodeAlias);
        writer.setValue(nodeValue);
        writer.endNode();
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        DMNBaseElement obj = createModelObject();
        assignAttributes(reader, obj);
        parseElements(reader, context, obj);
        return obj;
    }

    protected void parseElements(HierarchicalStreamReader reader, UnmarshallingContext context, Object parent) {
        while (reader.hasMoreChildren()) {
            reader.moveDown();
            String nodeName = reader.getNodeName();
            Object object = readBareItem(reader, context,null);
            if (object instanceof DMNBaseElement) {
                ((DMNBaseElement) object).setParent((DMNBaseElement) parent);
                ((DMNBaseElement) parent).addChildren((DMNBaseElement) object);
            }
            reader.moveUp();
            assignChildElement(parent, nodeName, object);
        }
    }

    protected abstract DMNBaseElement createModelObject();

    protected abstract void assignChildElement(Object parent, String nodeName, Object child);

    protected abstract void assignAttributes(HierarchicalStreamReader reader, Object parent);

    protected abstract void writeChildren(HierarchicalStreamWriter writer, MarshallingContext context, Object parent);

    protected abstract void writeAttributes(HierarchicalStreamWriter writer, Object parent);
}
