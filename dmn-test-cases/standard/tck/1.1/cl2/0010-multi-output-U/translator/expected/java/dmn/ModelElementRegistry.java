
public class ModelElementRegistry extends com.gs.dmn.runtime.discovery.ModelElementRegistry {
    public ModelElementRegistry() {
        // Register elements from model 'multi-output-table'
        register("Age", "Age");
        register("Approval", "Approval");
        register("RiskCategory", "RiskCategory");
        register("isAffordable", "IsAffordable");
    }
}
