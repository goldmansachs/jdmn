
class ModelElementRegistry : com.gs.dmn.runtime.discovery.ModelElementRegistry {
    constructor() {
        // Register elements from model '0009-invocation-arithmetic'
        register("Loan", "Loan")
        register("MonthlyPayment", "MonthlyPayment")
        register("PMT", "PMT")
        register("fee", "Fee")
    }
}
