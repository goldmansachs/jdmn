
class ModelElementRegistry : com.gs.dmn.runtime.discovery.ModelElementRegistry {
    constructor() {
        // Register elements from model 'multi-output-table'
        register("Age", "Age")
        register("Approval", "Approval")
        register("RiskCategory", "RiskCategory")
        register("isAffordable", "IsAffordable")
    }
}
