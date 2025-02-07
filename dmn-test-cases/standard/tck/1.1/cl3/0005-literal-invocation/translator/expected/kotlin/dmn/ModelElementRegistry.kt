
class ModelElementRegistry : com.gs.dmn.runtime.discovery.ModelElementRegistry {
    constructor() {
        // Register elements from model '0005-literal-invocation'
        register("Loan", "Loan")
        register("MonthlyPayment", "MonthlyPayment")
        register("PMT", "PMT")
        register("fee", "Fee")
    }
}
