
class ModelElementRegistry : com.gs.dmn.runtime.discovery.ModelElementRegistry {
    constructor() {
        register("Loan", "Loan")
        register("MonthlyPayment", "MonthlyPayment")
        register("PMT", "PMT")
        register("fee", "Fee")
    }
}
