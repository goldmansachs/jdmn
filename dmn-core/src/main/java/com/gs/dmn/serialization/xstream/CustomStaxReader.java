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
package com.gs.dmn.serialization.xstream;

import com.gs.dmn.serialization.xstream.dom.ElementInfo;
import com.thoughtworks.xstream.io.StreamException;
import com.thoughtworks.xstream.io.xml.QNameMap;
import com.thoughtworks.xstream.io.xml.StaxReader;

import javax.xml.XMLConstants;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


public class CustomStaxReader extends StaxReader {
    private final XMLStreamReader in;

    public CustomStaxReader(QNameMap qnameMap, XMLStreamReader in) {
        super(qnameMap, in);
        this.in = in;
        // Needed because this class overrides pullNextEvent, moveDown.
        // Please note that the super() internally calls moveDown().
        moveDown();
    }

    public ElementInfo getElementInfo() {
        Map<String, String> nsContext = new LinkedHashMap<>();
        for (int nsIndex = 0; nsIndex < in.getNamespaceCount(); nsIndex++) {
            String nsPrefix = in.getNamespacePrefix(nsIndex);
            String nsId = in.getNamespaceURI(nsIndex);
            nsContext.put(nsPrefix != null ? nsPrefix : XMLConstants.DEFAULT_NS_PREFIX, nsId);
        }
        return new ElementInfo(in.getLocation(), in.getPrefix(), in.getNamespaceURI(), nsContext);
    }

    @Override
    public String getAttribute(String name) {
         // REDEFINES default XStream behavior, by expliciting the namespaceURI to use, instead of generic `null`
        // which is problematic in case of:
        //  - multiple attribute with the same name and different namespace, not supported by XStream
        //  - if using IBM JDK, because the XML infra is not respecting the JDK API javadoc contract.

        // Also note.
        // To avoid semantic ambiguities as per example in W3C https://www.w3.org/TR/REC-xml-names/#uniqAttrs
        // <!-- http://www.w3.org is bound to n1 and is the default -->
        // <x xmlns:n1="http://www.w3.org" 
        //    xmlns="http://www.w3.org" >
        //   <good a="1"     b="2" />
        //   <good a="1"     n1:a="2" />     <<-- this has semantic ambiguity in our case because if `a` was `id` which value to bind?
        // </x>
        // hence we do not support for attributes an "explicit" prefix for the namespace.
        // In other words, a similar example:
        // <dmn:inputData dmn:id="_3d560678-a126-4654-a686-bc6d941fe40b" dmn:name="MyInput">
        // is not supported, and is expected as standard XML:
        // <dmn:inputData id="_3d560678-a126-4654-a686-bc6d941fe40b" name="MyInput">
        return getAttribute(XMLConstants.DEFAULT_NS_PREFIX, this.encodeAttribute(name));
    }

    public String getAttribute(String namespaceURI, String name) {
        return this.in.getAttributeValue(namespaceURI, this.encodeAttribute(name));
    }

    public Map<QName, String> getAdditionalAttributes() {
        Map<QName, String> result = new HashMap<>();
        for (int aIndex = 0; aIndex < in.getAttributeCount(); aIndex++) {
            String attributePrefix = in.getAttributePrefix(aIndex);

            // IBM JDK would return a null instead of respecting JDK contract of returning XMLConstants.DEFAULT_NS_PREFIX (an empty String)
            if (attributePrefix == null) {
                attributePrefix = XMLConstants.DEFAULT_NS_PREFIX;
            }

            if (!XMLConstants.DEFAULT_NS_PREFIX.equals(attributePrefix)) {
                result.put(new QName(in.getAttributeNamespace(aIndex), in.getAttributeLocalName(aIndex), attributePrefix), in.getAttributeValue(aIndex));
            }
        }
        return result;
    }

    @Override
    public void moveDown() {
        if (in == null) {
            // defer the moveDown until this constructor is fully completed.
            return;
        }
        super.moveDown();
    }

    @Override
    protected int pullNextEvent() {
        try {
            switch (in.next()) {
                case XMLStreamConstants.START_DOCUMENT:
                case XMLStreamConstants.START_ELEMENT:
                    return START_NODE;
                case XMLStreamConstants.END_DOCUMENT:
                case XMLStreamConstants.END_ELEMENT:
                    return END_NODE;
                case XMLStreamConstants.CHARACTERS:
                case XMLStreamConstants.CDATA:
                    // the StAX api when on IBM JDK reports event as CDATA explicitly.
                    return TEXT;
                case XMLStreamConstants.COMMENT:
                    return COMMENT;
                default:
                    return OTHER;
            }
        } catch (XMLStreamException e) {
            throw new StreamException(e);
        }
    }
}
