
public class ModelElementRegistry extends com.gs.dmn.runtime.discovery.ModelElementRegistry {
    public ModelElementRegistry() {
        // Register elements from model 'BKMfromBKM'
        register("date formula", "DateFormula");
        register("date operators", "DateOperators");
        register("datetime formula", "DatetimeFormula");
        register("datetime operators", "DatetimeOperators");
        register("decide", "Decide");
        register("decision", "Decision");
        register("decision date", "DecisionDate");
        register("decision time", "DecisionTime");
        register("final decision", "FinalDecision");
        register("importedLogicDates", "ImportedLogicDates");
        register("importedLogicTime", "ImportedLogicTime");
        register("logic", "Logic");
        register("pick", "Pick");
        register("sum", "Sum");
        register("time formula", "TimeFormula");
        register("time operators", "TimeOperators");
    }
}
