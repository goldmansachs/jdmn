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

import com.gs.dmn.serialization.DMNSerializer;
import com.gs.dmn.signavio.dialect.SignavioDMNDialectDefinition;
import com.gs.dmn.signavio.testlab.TestLabSerializer;
import com.gs.dmn.transformation.AbstractFileTransformerTest;

import java.util.Map;

import static com.gs.dmn.signavio.SignavioTestConstants.SIG_EXT_NAMESPACE;

public abstract class AbstractSignavioFileTransformerTest extends AbstractFileTransformerTest {
    protected final SignavioDMNDialectDefinition dialectDefinition = new SignavioDMNDialectDefinition();
    protected final DMNSerializer dmnSerializer = this.dialectDefinition.createDMNSerializer(LOGGER, makeInputParameters());
    protected final TestLabSerializer testReader = new TestLabSerializer();

    @Override
    protected Map<String, String> makeInputParametersMap() {
        Map<String, String> inputParams = super.makeInputParametersMap();
        inputParams.put("signavioSchemaNamespace", SIG_EXT_NAMESPACE);
        return inputParams;
    }
}
