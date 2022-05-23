

public class ModelElementRegistry extends com.gs.dmn.runtime.discovery.ModelElementRegistry {
    public ModelElementRegistry() {
        register("BooleanInput", "BooleanInput");
        register("Compound Output Compound Decision", "CompoundOutputCompoundDecision");
        register("DD1 Text Input", "DD1TextInput");
        register("DD2 Number Input", "DD2NumberInput");
        register("Dependent Decision 1", "DependentDecision1");
        register("Dependent Decision 2", "DependentDecision2");
        register("EnumerationInput", "EnumerationInput");
    }
}