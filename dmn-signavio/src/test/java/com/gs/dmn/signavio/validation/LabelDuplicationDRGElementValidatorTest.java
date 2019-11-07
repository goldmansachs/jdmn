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
package com.gs.dmn.signavio.validation;

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.serialization.DMNReader;
import com.gs.dmn.serialization.PrefixNamespaceMappings;
import com.gs.dmn.signavio.SignavioDMNModelRepository;
import com.gs.dmn.transformation.AbstractFileTransformerTest;
import com.gs.dmn.validation.DMNValidator;
import org.junit.Ignore;
import org.junit.Test;
import org.omg.spec.dmn._20180521.model.TDefinitions;

import java.net.URL;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@Ignore
public class LabelDuplicationDRGElementValidatorTest extends AbstractFileTransformerTest {
    private final DMNReader dmnReader = new DMNReader(LOGGER, false);
    private static final ClassLoader CLASS_LOADER = LabelDuplicationDRGElementValidatorTest.class.getClassLoader();

    private DMNValidator validator = new LabelDuplicationDRGElementValidator();

    @Test
    public void testValidate() throws Exception {
        String path = "dmn2java/exported/complex/input/";
        String diagramName = "Linked Decision Test.dmn";
        URL url = CLASS_LOADER.getResource(path + diagramName).toURI().toURL();
        Pair<TDefinitions, PrefixNamespaceMappings> pair = dmnReader.read(url);
        DMNModelRepository repository = new SignavioDMNModelRepository(pair);


        List<String> actualErrors = validator.validate(repository);
        List<String> expectedErrors = Arrays.asList(
                "Found 2 Decision with duplicated label 'Assess applicant age'",
                "Label = 'Assess applicant age' DiagramId = '9acf44f2b05343d79fc35140c493c1e0' shapeId = 'sid-0C363FE4-468D-4273-9416-D1BCACB6248A'",
                "Label = 'Assess applicant age' DiagramId = 'b4fc99dd0b044cf1b31b6e60d01c50fa' shapeId = 'sid-CB90CCF4-4F53-458D-9574-692EC86300A9'",
                "Found 3 Decision with duplicated label 'Make credit decision'",
                "Label = 'Make credit decision' DiagramId = '9acf44f2b05343d79fc35140c493c1e0' shapeId = 'sid-31214799-6743-4B69-98A5-8C4D9C1BE010'",
                "Label = 'Make credit decision' DiagramId = 'b4fc99dd0b044cf1b31b6e60d01c50fa' shapeId = 'sid-B2BA5182-2675-4804-BB0B-F09B7B2F062E'",
                "Label = 'Make credit decision' DiagramId = 'b4fc99dd0b044cf1b31b6e60d01c50fa' shapeId = 'sid-F398BC97-C058-495D-A3E7-DE6637825589'");
        assertEquals(expectedErrors, actualErrors);
    }

}