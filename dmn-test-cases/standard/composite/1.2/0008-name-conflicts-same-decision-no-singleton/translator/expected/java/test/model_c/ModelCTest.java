package model_c;

import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "Model C"})
public class ModelCTest extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision {
    @org.junit.jupiter.api.Test
    public void testCase001() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();
        // Initialize input data
        String modela_a = "B.A.John";
        String modelb_a = "B.A.John2";

        // Check 'c'
        checkValues("A: B.A.John; B: B.A.John2", new model_c.C().apply(modela_a, modelb_a, context_));
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
