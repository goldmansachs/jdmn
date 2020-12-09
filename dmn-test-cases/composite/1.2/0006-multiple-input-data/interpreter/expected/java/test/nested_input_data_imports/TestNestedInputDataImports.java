package nested_input_data_imports;

import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "Nested Input Data Imports"})
public class TestNestedInputDataImports extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    @org.junit.Test
    public void testCase001() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        com.gs.dmn.runtime.cache.Cache cache_ = new com.gs.dmn.runtime.cache.DefaultCache();
        // Initialize input data
        String modelb_modela_personName = "B.A.John";
        String modelb2_modela_personName = "B2.A.John2";

        // Check ModelCDecisionBasedOnBs
        checkValues("B: Evaluating Say Hello to: Hello, B.A.John; B2: Evaluating Say Hello to: Hello, B2.A.John2", new nested_input_data_imports.ModelCDecisionBasedOnBs().apply(modelb2_modela_personName, modelb_modela_personName, annotationSet_, eventListener_, externalExecutor_, cache_));
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
