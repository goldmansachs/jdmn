package com.gs.dmn.serialization.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.gs.dmn.serialization.xstream.dom.NSElement;
import org.w3c.dom.Element;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringWriter;

public class NSElementSerializer extends JsonSerializer<NSElement> {
    @Override
    public void serialize(NSElement element, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("namespace", element.getNamespaceURI());
        gen.writeStringField("prefix", element.getPrefix());
        gen.writeStringField("element", toXml(element.getElement()));
        gen.writeEndObject();
    }

    private static String toXml(Element element) {
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(element), new StreamResult(writer));
            String xmlString = writer.toString();
            return xmlString;
//            gen.writeString(xmlString);
        } catch (TransformerException e) {
            throw new RuntimeException("Error serializing DOM Element", e);
        }
    }
}
