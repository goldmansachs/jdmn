package com.gs.dmn.serialization.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.gs.dmn.ast.TDMNElement;

import java.io.IOException;

public class ExtensionsSerializer extends JsonSerializer<TDMNElement.ExtensionElements> {
    @Override
    public void serialize(TDMNElement.ExtensionElements element, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeObjectField("elementInfo", element.getElementInfo());
        if (!element.getAny().isEmpty()) {
            gen.writeObjectField("any", element.getAny());
        }
        gen.writeEndObject();
    }
}
