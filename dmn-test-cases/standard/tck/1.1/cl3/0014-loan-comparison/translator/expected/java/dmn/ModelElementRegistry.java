
public class ModelElementRegistry extends com.gs.dmn.runtime.discovery.ModelElementRegistry {
    public ModelElementRegistry() {
        // Register elements from model 'loanComparison'
        register("Bankrates", "Bankrates");
        register("FinancialMetrics", "FinancialMetrics");
        register("RankedProducts", "RankedProducts");
        register("equity36Mo", "Equity36Mo");
        register("monthlyPayment", "MonthlyPayment");
    }
}
