
class ModelElementRegistry : com.gs.dmn.runtime.discovery.ModelElementRegistry {
    constructor() {
        // Register elements from model '0008-LX-arithmetic'
        register("loan", "Loan")
        register("payment", "Payment")
    }
}
