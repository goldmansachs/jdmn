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
package com.gs.dmn.serialization;

import com.gs.dmn.ast.TDefinitions;
import com.gs.dmn.serialization.jackson.JsonDMNSerializer;
import com.gs.dmn.serialization.xstream.XMLDMNSerializer;
import com.gs.dmn.transformation.AbstractFileTransformerTest;
import org.junit.jupiter.api.Test;

import javax.xml.namespace.QName;
import java.io.File;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class DMNSerializerConversionTest extends AbstractFileTransformerTest {
    private final DMNSerializer xmlSerializer = new XMLDMNSerializer(LOGGER, this.inputParameters);
    private final DMNSerializer jsonSerializer = new JsonDMNSerializer(LOGGER, this.inputParameters);

    @Test
    public void testRoundTrip() throws Exception {
        // Read in XML format
        File inputXmlFile = new File(resource("dmn/input/1.1/0004-lending.dmn"));
        TDefinitions definitions = this.xmlSerializer.readModel(inputXmlFile);
        checkModel(definitions);

        // Write & read in JSON format
        File outputJsonFile = new File("target", "test-0004-lending.json");
        this.jsonSerializer.writeModel(definitions, outputJsonFile);
        definitions = this.jsonSerializer.readModel(outputJsonFile);
        checkModel(definitions);

        // Write in XML format
        File outputXmlFile = new File("target", "test-0004-lending.dmn");
        this.xmlSerializer.writeModel(definitions, outputXmlFile);

        // Check files
        File latestInputXmlFile = new File(resource("dmn/expected/1.1/1.4/0004-lending.dmn"));
        compareFile(latestInputXmlFile, outputXmlFile);
    }

    private void checkModel(TDefinitions definitions) {
        assertEquals(24, definitions.getDrgElement().size());
        assertEquals(9, definitions.getItemDefinition().size());

        Map<QName, String> otherAttributes = definitions.getOtherAttributes();
        Set<QName> qNames = otherAttributes.keySet();
        assertEquals(2, qNames.size());
        QName first = qNames.iterator().next();
        assertInstanceOf(QName.class, first);
        assertEquals("logoChoice", first.getLocalPart());
        assertEquals("http://www.trisotech.com/2015/triso/modeling", first.getNamespaceURI());
        assertEquals("triso", first.getPrefix());
    }
}
