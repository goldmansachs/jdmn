
class ModelElementRegistry : com.gs.dmn.runtime.discovery.ModelElementRegistry {
    constructor() {
        register("Age", "Age")
        register("Approval", "Approval")
        register("RiskCategory", "RiskCategory")
        register("isAffordable", "IsAffordable")
    }
}
