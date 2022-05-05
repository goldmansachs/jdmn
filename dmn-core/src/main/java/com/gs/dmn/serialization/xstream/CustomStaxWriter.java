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

import com.thoughtworks.xstream.io.StreamException;
import com.thoughtworks.xstream.io.naming.NameCoder;
import com.thoughtworks.xstream.io.xml.QNameMap;
import com.thoughtworks.xstream.io.xml.StaxWriter;

import javax.xml.XMLConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;


public class CustomStaxWriter extends StaxWriter implements AutoCloseable {
    private final XMLStreamWriter out;
    private int tagDepth = 0;
    private Op lastOp = null;

    public CustomStaxWriter(QNameMap qnameMap, XMLStreamWriter out, boolean writeStartEndDocument, boolean repairingNamespace, NameCoder nameCoder) throws XMLStreamException {
        super(qnameMap, out, writeStartEndDocument, repairingNamespace, nameCoder);
        this.out = out;
    }

    public void writeNamespace(String prefix, String uri) throws XMLStreamException {
        // Avoid default ns
        if (prefix == null
                || prefix.equals(XMLConstants.DEFAULT_NS_PREFIX)
                || prefix.equals(XMLConstants.XMLNS_ATTRIBUTE)) {
            return;
        }
        // Avoid duplicates
        if (prefix.equals(out.getPrefix(uri))) {
            return;
        }

        out.writeNamespace(prefix, uri);
    }

    public void setDefaultNamespace(String uri) throws XMLStreamException {
        out.setDefaultNamespace(uri);
    }

    @Override
    public void endNode() {
        if (this.lastOp == Op.END_NODE) {
            try {
                out.writeCharacters(System.lineSeparator());
                for (int i = 0; i < (tagDepth - 1); i++) {
                    out.writeCharacters("  ");
                }
            } catch (XMLStreamException e) {
                throw new StreamException(e);
            }
        }
        super.endNode();
        --this.tagDepth;

        this.lastOp = Op.END_NODE;

        if (this.tagDepth == 0) {
            if (out.getClass().getPackage().getName().startsWith("com.ctc.wstx")) {
                // problem with Woodstox trying to insert trailing newline before EOF
                // java.lang.NullPointerException
                //   at java.lang.System.arraycopy(Native Method)
                //   at java.lang.String.getChars(String.java:826)
                //   at com.ctc.wstx.sw.BufferingXmlWriter.writeRaw(BufferingXmlWriter.java:314)
                //   at com.ctc.wstx.sw.BaseStreamWriter.writeCharacters(BaseStreamWriter.java:436)
                //   at org.kie.dmn.backend.marshalling.CustomStaxWriter.endNode(CustomStaxWriter.java:55)
                // where CustomStaxWriter.java:55 was inserting of trailing newline before EOF, but Woodstox seems to close early it internal write buffer
                // before having the opportunity to append the trailing line.
                // Therefore, in case of WoodStx, do not insert trailing newline before EOF.
            } else {
                // closed last element before EOF
                try {
                    out.writeCharacters(System.lineSeparator());
                } catch (XMLStreamException e) {
                    throw new StreamException(e);
                }
            }
        }
    }

    @Override
    public void startNode(String arg0) {
        try {
            out.writeCharacters(System.lineSeparator());
            for (int i = 0; i < tagDepth; i++) {
                out.writeCharacters("  ");
            }
        } catch (XMLStreamException e) {
            throw new StreamException(e);
        }
        super.startNode(arg0);
        ++this.tagDepth;

        this.lastOp = Op.START_NODE;
    }

    @Override
    public void setValue(String text) {
        super.setValue(text);

        this.lastOp = Op.VALUE;
    }

    @Override
    public QNameMap getQNameMap() {
        return super.getQNameMap();
    }

    private enum Op {
        START_NODE, END_NODE, VALUE
    }
}
