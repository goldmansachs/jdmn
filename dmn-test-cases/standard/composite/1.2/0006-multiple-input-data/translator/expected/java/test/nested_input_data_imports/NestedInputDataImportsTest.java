package nested_input_data_imports;

import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "Nested Input Data Imports"})
public class NestedInputDataImportsTest extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    @org.junit.Test
    public void testCase001() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();
        // Initialize input data
        String model_b_modela_personName = "B.A.John";
        String model_b2_modela_personName = "B2.A.John2";

        // Check ''Model C Decision based on Bs''
        checkValues("B: Evaluating Say Hello to: Hello, B.A.John; B2: Evaluating Say Hello to: Hello, B2.A.John2", new nested_input_data_imports.ModelCDecisionBasedOnBs().apply(model_b2_modela_personName, model_b_modela_personName, context_));
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
