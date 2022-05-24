
public class ModelElementRegistry extends com.gs.dmn.runtime.discovery.ModelElementRegistry {
    public ModelElementRegistry() {
        register("Age", "Age");
        register("'Approval Status'", "ApprovalStatus");
        register("RiskCategory", "RiskCategory");
        register("isAffordable", "IsAffordable");
    }
}
