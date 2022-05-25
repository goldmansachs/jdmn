
public class ModelElementRegistry extends com.gs.dmn.runtime.discovery.ModelElementRegistry {
    public ModelElementRegistry() {
        // Register elements from model 'literal - arithmetic'
        register("loan", "Loan");
        register("payment", "Payment");
    }
}
