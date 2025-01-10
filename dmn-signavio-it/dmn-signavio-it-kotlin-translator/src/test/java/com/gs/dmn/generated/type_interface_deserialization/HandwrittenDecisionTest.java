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
package com.gs.dmn.generated.type_interface_deserialization;

import com.gs.dmn.generated.AbstractHandwrittenDecisionTest;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class HandwrittenDecisionTest extends AbstractHandwrittenDecisionTest {
    private final EsmaEquityOptionIndicator decision = new EsmaEquityOptionIndicator();

    @Test
    public void testDeserializedDecisionInput() {
        com.gs.dmn.generated.type_interface_deserialization.type.MarginEquityOptionTradableProfiles marginEquityOptionTradableProfiles = new com.gs.dmn.generated.type_interface_deserialization.type.MarginEquityOptionTradableProfilesImpl(decision.asList(new com.gs.dmn.generated.type_interface_deserialization.type.MarginEquityOptionTradableProfileImpl(Boolean.TRUE, "abc")));

        String esmaEquityOptionIndicator = decision.apply(marginEquityOptionTradableProfiles, context);

        checkValues("Yes", esmaEquityOptionIndicator);
    }

    @Test
    public void testSerializedDecisionInput() throws Exception {
        com.gs.dmn.generated.type_interface_deserialization.type.MarginEquityOptionTradableProfiles marginEquityOptionTradableProfiles = new com.gs.dmn.generated.type_interface_deserialization.type.MarginEquityOptionTradableProfilesImpl(decision.asList(new com.gs.dmn.generated.type_interface_deserialization.type.MarginEquityOptionTradableProfileImpl(Boolean.TRUE, "abc")));
        String serializedInput = com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.writeValueAsString(marginEquityOptionTradableProfiles);

        String esmaEquityOptionIndicator = applyDecision(serializedInput);

        checkValues("Yes", esmaEquityOptionIndicator);
    }


    @Override
    protected void applyDecision() {
        com.gs.dmn.generated.type_interface_deserialization.type.MarginEquityOptionTradableProfiles marginEquityOptionTradableProfiles = new com.gs.dmn.generated.type_interface_deserialization.type.MarginEquityOptionTradableProfilesImpl(decision.asList(new com.gs.dmn.generated.type_interface_deserialization.type.MarginEquityOptionTradableProfileImpl(Boolean.TRUE, "abc")));

        decision.apply(marginEquityOptionTradableProfiles, context);
    }

    private String applyDecision(String marginEquityOptionTradableProfiles) {
        Map<String, String> input = new LinkedHashMap<>();
        input.put("Margin Equity Option Tradable Profiles", marginEquityOptionTradableProfiles);
        return decision.applyMap(input, context);
    }

    private void checkValues(Object expected, Object actual) {
        assertEquals(expected == null ? null : expected.toString(), actual == null ? null : actual.toString());
    }
}
