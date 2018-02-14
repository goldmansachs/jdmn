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
package com.gs.dmn.transformation;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class InputParamUtilTest {
    @Test
    public void testGetRequiredParamWherePresent() {
        Map<String, String> params = new HashMap<String, String>(){{
            put("paramKey", "paramValue");
        }};
        assertEquals("paramValue", InputParamUtil.getRequiredParam(params, "paramKey"));
    }

    @Test
    public void testGetRequiredParamWhereNotPresent() {
        Map<String, String> params = new HashMap<String, String>(){{
        }};
        try {
            InputParamUtil.getRequiredParam(params, "paramKey");
            fail();
        } catch (RuntimeException e) {
            assertEquals("A 'paramKey' parameter is required.", e.getMessage());
        }
    }

    @Test
    public void testGetOptionalParamWherePresent() {
        Map<String, String> params = new HashMap<String, String>(){{
            put("paramKey", "paramValue");
        }};
        assertEquals("paramValue", InputParamUtil.getOptionalParam(params, "paramKey"));
    }

    @Test
    public void testGetOptionalParamWhereNotPresent() {
        Map<String, String> params = new HashMap<String, String>(){{
        }};
        assertNull(InputParamUtil.getOptionalParam(params, "paramKey"));
    }

    @Test
    public void testGetOptionalBooleanParamWherePresent() {
        Map<String, String> params = new HashMap<String, String>(){{
            put("paramKey", "true");
        }};
        assertTrue(InputParamUtil.getOptionalBooleanParam(params, "paramKey"));
    }

    @Test
    public void testGetOptionalBooleanParamWhereNotPresent() {
        Map<String, String> params = new HashMap<String, String>(){{
        }};
        assertFalse(InputParamUtil.getOptionalBooleanParam(params, "paramKey"));
    }
}