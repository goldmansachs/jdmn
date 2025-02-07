
public class ModelElementRegistry extends com.gs.dmn.runtime.discovery.ModelElementRegistry {
    public ModelElementRegistry() {
        // Register elements from model 'date-time-proto'
        register("CompositeDateTime", "CompositeDateTime");
        register("Date", "Date");
        register("DateTime", "DateTime");
        register("Time", "Time");
    }
}
