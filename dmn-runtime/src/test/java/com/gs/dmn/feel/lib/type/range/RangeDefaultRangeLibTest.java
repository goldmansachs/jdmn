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