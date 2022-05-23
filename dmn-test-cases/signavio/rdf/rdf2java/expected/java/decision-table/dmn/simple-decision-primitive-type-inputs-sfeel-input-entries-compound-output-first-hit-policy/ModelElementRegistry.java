

public class ModelElementRegistry extends com.gs.dmn.runtime.discovery.ModelElementRegistry {
    public ModelElementRegistry() {
        register("BooleanInput", "BooleanInput");
        register("Compound Output Decision", "CompoundOutputDecision");
        register("DateAndTimeInput", "DateAndTimeInput");
        register("DateInput", "DateInput");
        register("EnumerationInput", "EnumerationInput");
        register("NumberInput", "NumberInput");
        register("TextInput", "TextInput");
        register("TimeInput", "TimeInput");
    }
}