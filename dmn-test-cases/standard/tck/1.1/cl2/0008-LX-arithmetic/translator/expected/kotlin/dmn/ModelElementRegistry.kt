
class ModelElementRegistry : com.gs.dmn.runtime.discovery.ModelElementRegistry {
    constructor() {
        // Register elements from model 'literal - arithmetic'
        register("loan", "Loan")
        register("payment", "Payment")
    }
}
