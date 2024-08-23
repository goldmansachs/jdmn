package com.gs.dmn.serialization.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.gs.dmn.serialization.xstream.dom.NSElement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.StringReader;

public class NSElementDeserializer extends JsonDeserializer<NSElement> {

    @Override
    public NSElement deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException {
        ObjectCodec codec = parser.getCodec();
        JsonNode node = codec.readTree(parser);

        // try catch block
        JsonNode namespaceNode = node.get("namespace");
        JsonNode prefixNode = node.get("prefix");
        JsonNode elementNode = node.get("element");

        String namespace = namespaceNode == null ? null : namespaceNode.asText();
        String prefix = prefixNode == null ? null : prefixNode.asText();
        Element element = elementNode == null ? null : toElement(elementNode.asText());

        return new NSElement(element, namespace, prefix);
    }

    private static Element toElement(String text) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(text)));
            return doc.getDocumentElement();
        } catch (Exception e) {
            throw new RuntimeException("Error deserializing DOM Element", e);
        }
    }
}
