
public class ModelElementRegistry extends com.gs.dmn.runtime.discovery.ModelElementRegistry {
    public ModelElementRegistry() {
        register("d", "D");
        register("date", "Date");
        register("date formula", "DateFormula");
        register("date input", "DateInput");
        register("date operators", "DateOperators");
        register("datetime", "Datetime");
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
        register("t", "T");
        register("time", "Time");
        register("time 2", "Time2");
        register("time formula", "TimeFormula");
        register("time input", "TimeInput");
        register("time operators", "TimeOperators");
    }
}
