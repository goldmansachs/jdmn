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
package com.gs.dmn.ast.visitor;

import com.gs.dmn.ast.TDefinitions;
import com.gs.dmn.ast.Visitor;
import com.gs.dmn.error.NopErrorHandler;
import com.gs.dmn.log.NopBuildLogger;
import com.gs.dmn.serialization.DMNMarshaller;
import com.gs.dmn.serialization.xstream.DMNMarshallerFactory;
import com.gs.dmn.serialization.xstream.extensions.kie.DecisionServicesRegister;
import com.gs.dmn.serialization.xstream.extensions.kie.KieTestRegister;
import com.gs.dmn.transformation.AbstractFileTransformerTest;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Collections;
import java.util.Objects;

public class CloneVisitorTest extends AbstractFileTransformerTest {
    private final DMNMarshaller testMarshaller = DMNMarshallerFactory.newMarshallerWithExtensions(Collections.singletonList(new KieTestRegister()));
    private final DMNMarshaller decisionServicesMarshaller = DMNMarshallerFactory.newMarshallerWithExtensions(Collections.singletonList(new DecisionServicesRegister()));
    private final Visitor<?, Object> visitor = new CloneVisitor<>(new NopBuildLogger(), new NopErrorHandler());

    private final File baseInputDir = new File("target/test-classes/");

    @Test
    public void visit() throws Exception {
        testVisit("xstream/v1_1/");
        testVisit("xstream/v1_2/");
        testVisit("xstream/v1_3/");
        testVisit("xstream/v1_4/");
        testVisit("xstream/v1_5/");
    }

    private void testVisit(String subDir) throws Exception {
        File inputDir = new File(baseInputDir, subDir);
        for (File file : Objects.requireNonNull(inputDir.listFiles())) {
            if (file.getName().endsWith(".dmn")) {
                if (file.getName().startsWith("0004-decision-services")) {
                    testVisit(subDir, file.getName(), decisionServicesMarshaller);
                } else {
                    testVisit(subDir, file.getName(), testMarshaller);
                }
            }
        }
    }

    private void testVisit(String subDir, String xmlFile, DMNMarshaller marshaller) throws Exception {
        File baseInputDir = new File("target/test-classes/");

        // Clone model
        File inputXMLFile = new File(baseInputDir, subDir + xmlFile);
        TDefinitions definitions = marshaller.unmarshal(new FileReader(inputXMLFile), true);
        TDefinitions result = (TDefinitions) definitions.accept(visitor, null);

        // Save clone
        File parent = new File("target/clone/dmn/" + subDir);
        Files.createDirectories(parent.toPath());
        File outputXMLFile = new File(parent, inputXMLFile.getName());
        try (FileWriter writer = new FileWriter(outputXMLFile, StandardCharsets.UTF_8)) {
            marshaller.marshal(result, writer);
        } catch (Exception ignored) {
        }

        // Compare models
        compareDMNFile(inputXMLFile, outputXMLFile);
    }
}