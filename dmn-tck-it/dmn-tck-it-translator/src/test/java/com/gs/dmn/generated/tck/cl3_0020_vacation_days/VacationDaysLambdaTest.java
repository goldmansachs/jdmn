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
package com.gs.dmn.generated.tck.cl3_0020_vacation_days;

import com.gs.dmn.runtime.ExecutableDRGElement;
import com.gs.dmn.runtime.ExecutionContextBuilder;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.gs.dmn.runtime.Assert.assertEquals;

/**
 * Handler for requests to Lambda function for DRG elements in model 0020-vacation-days.
 */
public class VacationDaysLambdaTest {
    private final ModelElementRegistry registry = new ModelElementRegistry();

    @Test
    public void testExecute() {
        doTest(new BigDecimal("27"), "Total Vacation Days", new LinkedHashMap<String, String>(){{ put("Age", "16"); put("Years of Service", "1");}});
        doTest(new BigDecimal("22"), "Total Vacation Days", new LinkedHashMap<String, String>(){{ put("Age", "25"); put("Years of Service", "5");}});
        doTest(new BigDecimal("24"), "Total Vacation Days", new LinkedHashMap<String, String>(){{ put("Age", "25"); put("Years of Service", "20");}});
        doTest(new BigDecimal("30"), "Total Vacation Days", new LinkedHashMap<String, String>(){{ put("Age", "44"); put("Years of Service", "30");}});
        doTest(new BigDecimal("24"), "Total Vacation Days", new LinkedHashMap<String, String>(){{ put("Age", "50"); put("Years of Service", "20");}});
        doTest(new BigDecimal("30"), "Total Vacation Days", new LinkedHashMap<String, String>(){{ put("Age", "50"); put("Years of Service", "30");}});
        doTest(new BigDecimal("30"), "Total Vacation Days", new LinkedHashMap<String, String>(){{ put("Age", "60"); put("Years of Service", "20");}});
    }

    protected void doTest(Object expectedResult, String qName, Map<String, String> input) {
        ExecutableDRGElement element = registry.discover(qName);
        Object actualResult = element.applyMap(input, ExecutionContextBuilder.executionContext().build());
        assertEquals(expectedResult, actualResult);
    }
}
