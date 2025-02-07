
class ModelElementRegistry : com.gs.dmn.runtime.discovery.ModelElementRegistry {
    constructor() {
        // Register elements from model '0004-simpletable-U'
        register("Age", "Age")
        register("Approval Status", "ApprovalStatus")
        register("RiskCategory", "RiskCategory")
        register("isAffordable", "IsAffordable")
    }
}
