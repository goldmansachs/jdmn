
public class ModelElementRegistry extends com.gs.dmn.runtime.discovery.ModelElementRegistry {
    public ModelElementRegistry() {
        // Register elements from model 'Null Safe Tests'
        register("allFalseAggregation", "AllFalseAggregation");
        register("allTogether", "AllTogether");
        register("allTrueAggregation", "AllTrueAggregation");
        register("anyTrueAggregation", "AnyTrueAggregation");
        register("arithmetic", "Arithmetic");
        register("comparator", "Comparator");
        register("formattingAndCoercing", "FormattingAndCoercing");
        register("keepInput_allFalse", "KeepInputallFalse");
        register("keepInput_allTrue", "KeepInputallTrue");
        register("keepInput_anyTrue", "KeepInputanyTrue");
        register("listHandling", "ListHandling");
        register("logical", "Logical");
        register("partA", "PartA");
        register("partB", "PartB");
        register("partC", "PartC");
        register("statistical", "Statistical");
        register("stringHandling", "StringHandling");
        register("stringHandlingComparator", "StringHandlingComparator");
        register("temporal", "Temporal");
        register("temporalComparator", "TemporalComparator");
        register("temporalDiff", "TemporalDiff");
    }
}
