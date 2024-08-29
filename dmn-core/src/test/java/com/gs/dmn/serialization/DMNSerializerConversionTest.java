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

import com.gs.dmn.ast.DMNBaseElement;
import com.gs.dmn.ast.TDRGElement;
import com.gs.dmn.ast.TDefinitions;
import com.gs.dmn.ast.TItemDefinition;
import com.gs.dmn.serialization.jackson.JsonDMNSerializer;
import com.gs.dmn.serialization.xstream.XMLDMNSerializer;
import com.gs.dmn.serialization.xstream.extensions.test.TestRegister;
import com.gs.dmn.transformation.AbstractFileTransformerTest;
import org.junit.jupiter.api.Test;

import javax.xml.namespace.QName;
import java.io.File;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import static com.gs.dmn.serialization.AbstractDMNSerializationTest.EXTENSION_MAPPER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class DMNSerializerConversionTest extends AbstractFileTransformerTest {
    private final DMNSerializer xmlSerializer = new XMLDMNSerializer(LOGGER, Collections.singletonList(new TestRegister()), this.inputParameters);

    private final DMNSerializer jsonSerializer = new JsonDMNSerializer(LOGGER, EXTENSION_MAPPER, this.inputParameters);

    @Test
    public void testRoundTripNoExtensions() throws Exception {
        String inputXmlPath = "dmn/input/1.1/0004-lending.dmn";
        String expectedXmlPath = "dmn/expected/1.1/1.4/0004-lending.dmn";
        doRoundTripTest(inputXmlPath, expectedXmlPath, this::checkModel);
    }

    @Test
    public void testRoundTripWithExtensions() throws Exception {
        String inputPath = "xstream/v1_1/test20161014.dmn";
        String expectedPath = "dmn/expected/1.1/1.4/test20161014.dmn";

        doRoundTripTest(inputPath, expectedPath, this::checkElementInfo);
    }

    @Test
    public void testRoundTripWithExtensionsNoRegistration() throws Exception {
        String inputPath = "xstream/v1_1/test20161014.dmn";
        String expectedPath = "dmn/expected/1.1/1.4/test20161014.dmn";

        doRoundTripTest(inputPath, expectedPath, this::checkElementInfo);
    }


    private void doRoundTripTest(String inputXmlPath, String expectedXmlPath, Consumer<TDefinitions> checkModel) throws Exception {
        // Read in XML format
        File inputXmlFile = new File(resource(inputXmlPath));
        TDefinitions definitions = this.xmlSerializer.readModel(inputXmlFile);
        checkModel.accept(definitions);

        // Write & read in JSON format
        String outputJsonFileName = String.format("test-%s.json", inputXmlFile.getName());
        File outputJsonFile = new File("target", outputJsonFileName);
        this.jsonSerializer.writeModel(definitions, outputJsonFile);
        definitions = this.jsonSerializer.readModel(outputJsonFile);
        checkModel.accept(definitions);

        // Write in XML format
        String outputXmlFileName = String.format("test-%s", inputXmlFile.getName());
        File outputXmlFile = new File("target", outputXmlFileName);
        this.xmlSerializer.writeModel(definitions, outputXmlFile);

        // Check files
        File expectedXmlFile = new File(resource(expectedXmlPath));
        compareFile(expectedXmlFile, outputXmlFile);
    }

    private void checkModel(TDefinitions definitions) {
        assertEquals(24, definitions.getDrgElement().size());
        assertEquals(9, definitions.getItemDefinition().size());

        Map<QName, String> otherAttributes = definitions.getOtherAttributes();
        Set<QName> qNames = otherAttributes.keySet();
        assertEquals(1, qNames.size());
        QName first = qNames.iterator().next();
        assertInstanceOf(QName.class, first);
        assertEquals("logoChoice", first.getLocalPart());
        assertEquals("http://www.trisotech.com/2015/triso/modeling", first.getNamespaceURI());
        assertEquals("triso", first.getPrefix());
    }

    private void checkElementInfo(TDefinitions definitions) {
        checkNamespaceURI(definitions);
        checkNamespaceURI(definitions.getExtensionElements());

        TItemDefinition itemDefinition = definitions.getItemDefinition().get(0);
        checkNamespaceURI(itemDefinition);

        TDRGElement element = definitions.getDrgElement().get(0);
        checkNamespaceURI(element);
    }

    private static void checkNamespaceURI(DMNBaseElement element) {
        assertEquals(DMNVersion.LATEST.getNamespace(), element.getElementInfo().getNamespaceURI());
    }
}
