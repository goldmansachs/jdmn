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
package com.gs.dmn.signavio.serialization.xstream;

import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.serialization.DMNMarshaller;
import com.gs.dmn.serialization.xstream.DMN12ToLatestDialectTransformerTest;
import com.gs.dmn.serialization.xstream.DMNMarshallerFactory;
import com.gs.dmn.signavio.SignavioTestConstants;
import org.junit.Test;

import java.util.Collections;

public class SignavioDMN12ToLatestDialectTransformerTest extends DMN12ToLatestDialectTransformerTest {
    @Override
    @Test
    public void testTransform() throws Exception {
        doTest("Example credit decision.dmn");
    }

    @Test(expected = DMNRuntimeException.class)
    public void testTransformWithBKM() throws Exception {
        doTest("simple-decision-with-bkm.dmn");
    }

    @Test
    public void testTransformWithMID() throws Exception {
        doTest("simpleMID.dmn");
    }

    @Override
    protected DMNMarshaller getDMNMarshaller() {
        return DMNMarshallerFactory.newMarshallerWithExtensions(Collections.singletonList(new SignavioExtensionRegister(SignavioTestConstants.SIG_EXT_NAMESPACE)));
    }
}