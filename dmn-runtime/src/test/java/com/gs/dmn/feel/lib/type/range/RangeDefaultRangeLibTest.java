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
package com.gs.dmn.feel.lib.type.range;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RangeDefaultRangeLibTest {
    private final List<String> yearMonthLiterals = Arrays.asList(
            "@\"P2Y2M\"",
            "@\"P2Y\"",
            "@\"P2M\"",
            "duration(\"P2Y2M\")",
            "duration(\"P2Y\")",
            "duration(\"P2M\")"
    );
    private final List<String> dayTimeLiterals = Arrays.asList(
            "@\"P2DT2H\"",
            "@\"P2D\"",
            "@\"PT2H\"",
            "duration(\"P2DT2H\")",
            "duration(\"P2D\")",
            "duration(\"PT2H\")"
    );

    @Test
    void testIsYearsMonthsRange() {
        for (String duration: yearMonthLiterals) {
            assertTrue(DefaultRangeLib.isYearsMonthsRange(duration), "Failed for " + duration);
        }

        for (String duration: dayTimeLiterals) {
            assertFalse(DefaultRangeLib.isYearsMonthsRange(duration), "Failed for " + duration);
        }
    }

    @Test
    void testIsDaysTimeRange() {
        for (String duration: yearMonthLiterals) {
            assertFalse(DefaultRangeLib.isDateTimeRange(duration), "Failed for " + duration);
        }

        for (String duration: dayTimeLiterals) {
            assertTrue(DefaultRangeLib.isDaysTimeRange(duration), "Failed for " + duration);
        }
    }
}