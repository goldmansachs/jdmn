
class ModelElementRegistry : com.gs.dmn.runtime.discovery.ModelElementRegistry {
    constructor() {
        // Register elements from model '0007-simpletable-P2'
        register("Age", "Age")
        register("Approval Status", "ApprovalStatus")
        register("RiskCategory", "RiskCategory")
        register("isAffordable", "IsAffordable")
    }
}
