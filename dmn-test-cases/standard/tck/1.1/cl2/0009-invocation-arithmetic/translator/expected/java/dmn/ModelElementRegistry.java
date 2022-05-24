
public class ModelElementRegistry extends com.gs.dmn.runtime.discovery.ModelElementRegistry {
    public ModelElementRegistry() {
        register("Loan", "Loan");
        register("MonthlyPayment", "MonthlyPayment");
        register("PMT", "PMT");
        register("fee", "Fee");
    }
}
