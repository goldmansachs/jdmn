
class ModelElementRegistry : com.gs.dmn.runtime.discovery.ModelElementRegistry {
    constructor() {
        // Register elements from model '0010-multi-output-U'
        register("Age", "Age")
        register("Approval", "Approval")
        register("RiskCategory", "RiskCategory")
        register("isAffordable", "IsAffordable")
    }
}
