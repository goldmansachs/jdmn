
class ModelElementRegistry : com.gs.dmn.runtime.discovery.ModelElementRegistry {
    constructor() {
        register("loan", "Loan")
        register("payment", "Payment")
    }
}
