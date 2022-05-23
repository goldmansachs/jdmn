

public class ModelElementRegistry extends com.gs.dmn.runtime.discovery.ModelElementRegistry {
    public ModelElementRegistry() {
        register("Bankrates", "Bankrates");
        register("FinancialMetrics", "FinancialMetrics");
        register("RankedProducts", "RankedProducts");
        register("RequestedAmt", "RequestedAmt");
        register("equity36Mo", "Equity36Mo");
        register("monthlyPayment", "MonthlyPayment");
    }
}