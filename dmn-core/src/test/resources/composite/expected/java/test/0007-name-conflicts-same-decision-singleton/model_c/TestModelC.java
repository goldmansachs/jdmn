package model_c;

import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "Model C"})
public class TestModelC extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    @org.junit.Test
    public void testCase001() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        com.gs.dmn.runtime.cache.Cache cache_ = new com.gs.dmn.runtime.cache.DefaultCache();
        // Initialize input data
        String model_a_a = "B.A.John";
        String model_b_a = "B.A.John2";

        // Check c
        checkValues("A: B.A.John; B: B.A.John2", new model_c.C().apply(model_a_a, model_b_a, annotationSet_, eventListener_, externalExecutor_, cache_));
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
