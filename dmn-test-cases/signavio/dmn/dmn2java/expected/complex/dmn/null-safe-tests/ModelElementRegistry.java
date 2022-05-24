
public class ModelElementRegistry extends com.gs.dmn.runtime.discovery.ModelElementRegistry {
    public ModelElementRegistry() {
        register("allFalseAggregation", "AllFalseAggregation");
        register("allTogether", "AllTogether");
        register("allTrueAggregation", "AllTrueAggregation");
        register("anyTrueAggregation", "AnyTrueAggregation");
        register("arithmetic", "Arithmetic");
        register("booleanA", "BooleanA");
        register("booleanAllFalse", "BooleanAllFalse_iterator");
        register("booleanAllTrue", "BooleanAllTrue_iterator");
        register("booleanAnyTrue", "BooleanAnyTrue_iterator");
        register("booleanB", "BooleanB");
        register("booleanList", "BooleanList");
        register("comparator", "Comparator");
        register("date", "Date");
        register("dateTime", "DateTime");
        register("formattingAndCoercing", "FormattingAndCoercing");
        register("keepInput_allFalse", "KeepInputallFalse");
        register("keepInput_allTrue", "KeepInputallTrue");
        register("keepInput_anyTrue", "KeepInputanyTrue");
        register("listHandling", "ListHandling");
        register("logical", "Logical");
        register("numberA", "NumberA");
        register("numberB", "NumberB");
        register("numberList", "NumberList");
        register("partA", "PartA");
        register("partB", "PartB");
        register("partC", "PartC");
        register("statistical", "Statistical");
        register("string", "String");
        register("stringHandling", "StringHandling");
        register("stringHandlingComparator", "StringHandlingComparator");
        register("stringList", "StringList");
        register("temporal", "Temporal");
        register("temporalComparator", "TemporalComparator");
        register("temporalDiff", "TemporalDiff");
        register("time", "Time");
    }
}
