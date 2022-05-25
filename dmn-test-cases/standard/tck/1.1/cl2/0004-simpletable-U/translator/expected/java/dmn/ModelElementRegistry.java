
public class ModelElementRegistry extends com.gs.dmn.runtime.discovery.ModelElementRegistry {
    public ModelElementRegistry() {
        // Register elements from model 'simple U table'
        register("Age", "Age");
        register("'Approval Status'", "ApprovalStatus");
        register("RiskCategory", "RiskCategory");
        register("isAffordable", "IsAffordable");
    }
}
