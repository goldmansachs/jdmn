package com.gs.dmn.feel.lib.type.numeric;

import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

public enum NumericRoundingMode {
    UP("up", RoundingMode.UP),
    DOWN("down", RoundingMode.DOWN),
    HALF_UP("half up", RoundingMode.HALF_UP),
    HALF_DOWN("half down", RoundingMode.HALF_DOWN),
    HALF_EVEN("half even", RoundingMode.HALF_EVEN),
    UNKNOWN("", null);

    private final String value;
    private final RoundingMode mathMode;

    NumericRoundingMode(String value, RoundingMode mathMode) {
        this.value = value;
        this.mathMode = mathMode;
    }

    public static RoundingMode fromValue(String v) {
        for (NumericRoundingMode roundingMode : NumericRoundingMode.values()) {
            if (roundingMode.value.equals(v)) {
                return roundingMode.mathMode;
            }
        }
        return UNKNOWN.mathMode;
    }

    public static List<String> ALLOWED_VALUES = Arrays.asList(
        UP.value, DOWN.value, HALF_UP.value, HALF_DOWN.value, HALF_EVEN.value
    );
}
