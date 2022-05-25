
public class ModelElementRegistry extends com.gs.dmn.runtime.discovery.ModelElementRegistry {
    public ModelElementRegistry() {
        // Register elements from model 'literal invocation1'
        register("Loan", "Loan");
        register("MonthlyPayment", "MonthlyPayment");
        register("PMT", "PMT");
        register("fee", "Fee");
    }
}
