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
package com.gs.dmn.signavio.transformation;

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.serialization.DMNReader;
import com.gs.dmn.serialization.PrefixNamespaceMappings;
import com.gs.dmn.signavio.SignavioDMNModelRepository;
import com.gs.dmn.signavio.testlab.TestLab;
import com.gs.dmn.transformation.AbstractFileTransformerTest;
import com.gs.dmn.transformation.DMNTransformer;
import org.junit.Test;
import org.omg.spec.dmn._20191111.model.*;

import javax.xml.bind.JAXBElement;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.fail;

public class UniqueInformationRequirementTransformerTest extends AbstractFileTransformerTest {
    private final DMNTransformer<TestLab> transformer = new UniqueInformationRequirementTransformer(LOGGER);
    private final DMNReader dmnReader = new DMNReader(LOGGER, false);

    @Test
    public void testTransform() {
        String path = "dmn/input/1.1/";

        // Transform DMN
        File dmnFile = new File(resource(path + "simpleMID-with-ir-duplicates.dmn"));
        Pair<TDefinitions, PrefixNamespaceMappings> pair = dmnReader.read(dmnFile);
        DMNModelRepository repository = new SignavioDMNModelRepository(pair);
        DMNModelRepository actualRepository = transformer.transform(repository);

        // Check output
        checkDefinitions(actualRepository.getRootDefinitions(), "simpleMID-with-ir-duplicates.dmn");
    }

    private void checkDefinitions(TDefinitions actualDefinitions, String fileName) {
        for(JAXBElement<? extends TDRGElement> jaxbElement: actualDefinitions.getDrgElement()) {
            TDRGElement drgElement = jaxbElement.getValue();
            if (drgElement instanceof TDecision) {
                List<String> hrefSet = new ArrayList<>();
                for(TInformationRequirement ir: ((TDecision) drgElement).getInformationRequirement()) {
                    TDMNElementReference requiredInput = ir.getRequiredInput();
                    TDMNElementReference requiredDecision = ir.getRequiredDecision();
                    if (requiredInput != null) {
                        checkIR(drgElement, hrefSet, requiredInput.getHref());
                    }
                    if (requiredDecision != null) {
                        checkIR(drgElement, hrefSet, requiredDecision.getHref());
                    }
                }
            }
        }
    }

    private void checkIR(TDRGElement drgElement, List<String> hrefSet, String href) {
        if (hrefSet.contains(href)) {
            fail(String.format("Duplicated informationRequirement '%s' in '%s'", href, drgElement.getName()));
        } else {
            hrefSet.add(href);
        }
    }
}