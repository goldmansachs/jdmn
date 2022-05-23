

public class ModelElementRegistry extends com.gs.dmn.runtime.discovery.ModelElementRegistry {
    public ModelElementRegistry() {
        register("BooleanInput", "BooleanInput");
        register("DateInput", "DateInput");
        register("Decision", "Decision");
        register("EnumerationInput", "EnumerationInput");
        register("NumberInput", "NumberInput");
        register("Person", "Person");
        register("StringInput", "StringInput");
    }
}