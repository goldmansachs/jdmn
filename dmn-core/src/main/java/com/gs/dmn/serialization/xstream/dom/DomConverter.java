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
package com.gs.dmn.serialization.xstream.dom;

import com.gs.dmn.serialization.xstream.CustomStaxReader;
import com.gs.dmn.serialization.xstream.CustomStaxWriter;
import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.DomReader;
import com.thoughtworks.xstream.io.xml.DomWriter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.util.LinkedHashSet;
import java.util.Set;

public class DomConverter implements Converter {
    public boolean canConvert(Class clazz) {
        return Document.class.isAssignableFrom(clazz) || Element.class.isAssignableFrom(clazz);
    }

    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext unmarshallingContext) {
        // Read DOM
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setNamespaceAware(false);
        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new ConversionException("Cannot instantiate " + Document.class.getName(), e);
        }
        Document document = documentBuilder.newDocument();
        DomWriter writer = new DomWriter(document);
        copy(reader, writer);

        // Return root element and (namespace, prefix)
        Element documentElement = document.getDocumentElement();
        CustomStaxReader staxReader = (CustomStaxReader) reader.underlyingReader();
        String prefix = staxReader.getElementInfo().getPrefix();
        String namespace = staxReader.getElementInfo().getNamespaceURI();
        return new NSElement(documentElement, namespace, prefix);
    }

    public void marshal(Object object, HierarchicalStreamWriter writer, MarshallingContext marshallingContext) {
        // Calculate all element names
        NSElement element = (NSElement) object;
        NodeList children = element.getChildNodes();
        Set<String> allChildNames = new LinkedHashSet<>();
        allChildNames.add(element.getNodeName());
        closure(children, allChildNames);

        // Register root (prefix, namespace) for all names
        String uri = element.getNamespaceURI();
        String prefix = element.getPrefix();
        CustomStaxWriter staxWriter = (CustomStaxWriter) writer.underlyingWriter();
        for (String name : allChildNames) {
            staxWriter.getQNameMap().registerMapping(new QName(uri, name, prefix), name);
        }

        DomReader reader = new DomReader(element);
        copy(reader, writer);
    }

    private void closure(NodeList nodeList, Set<String> result) {
        for (int i=0; i<nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node instanceof Element) {
                result.add(node.getNodeName());
                closure(node.getChildNodes(), result);
            }
        }
    }

    private void copy(HierarchicalStreamReader reader, HierarchicalStreamWriter writer) {
        writer.startNode(reader.getNodeName());

        // write the attributes
        int attributeCount = reader.getAttributeCount();
        for (int i = 0; i < attributeCount; i++) {
            String attributeName = reader.getAttributeName(i);
            String attributeValue = reader.getAttribute(i);
            writer.addAttribute(attributeName, attributeValue);
        }

        // write the child nodes recursively
        while (reader.hasMoreChildren()) {
            reader.moveDown();
            copy(reader, writer);
            reader.moveUp();
        }

        // write the context if any
        String value = reader.getValue();
        if (value != null && value.trim().length() > 0) {
            writer.setValue(value);
        }

        writer.endNode();
    }
}