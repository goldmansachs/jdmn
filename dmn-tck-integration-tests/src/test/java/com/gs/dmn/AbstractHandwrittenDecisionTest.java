/**
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
package com.gs.dmn;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.log.Slf4jBuildLogger;
import com.gs.dmn.serialization.DMNReader;
import com.gs.dmn.serialization.JsonSerializer;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import java.net.URL;

import static org.junit.Assert.assertTrue;

public abstract class AbstractHandwrittenDecisionTest {
    private static final BuildLogger LOGGER = new Slf4jBuildLogger(LoggerFactory.getLogger(AbstractHandwrittenDecisionTest.class));

    private final DMNReader dmnReader = new DMNReader(LOGGER, false);

    protected String toJson(Object object) {
        try {
            return JsonSerializer.OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    protected abstract void applyDecision();

    @Test
    public void testPerformance() {
        long before = System.currentTimeMillis();
        applyDecision();
        long after = System.currentTimeMillis();
        assertTrue("Takes longer than 500ms", after - before < 500);
    }

    protected DMNModelRepository readDMN(String pathName) throws Exception {
        URL url = this.getClass().getClassLoader().getResource(pathName).toURI().toURL();
        return dmnReader.read(url);
    }
}
