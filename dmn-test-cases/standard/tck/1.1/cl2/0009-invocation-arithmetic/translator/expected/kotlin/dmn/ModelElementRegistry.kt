
class ModelElementRegistry : com.gs.dmn.runtime.discovery.ModelElementRegistry {
    constructor() {
        // Register elements from model 'literal invocation1'
        register("Loan", "Loan")
        register("MonthlyPayment", "MonthlyPayment")
        register("PMT", "PMT")
        register("fee", "Fee")
    }
}
