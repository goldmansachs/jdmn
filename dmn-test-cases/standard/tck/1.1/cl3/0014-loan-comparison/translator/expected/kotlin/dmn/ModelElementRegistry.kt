
class ModelElementRegistry : com.gs.dmn.runtime.discovery.ModelElementRegistry {
    constructor() {
        register("Bankrates", "Bankrates")
        register("FinancialMetrics", "FinancialMetrics")
        register("RankedProducts", "RankedProducts")
        register("RequestedAmt", "RequestedAmt")
        register("equity36Mo", "Equity36Mo")
        register("monthlyPayment", "MonthlyPayment")
    }
}
