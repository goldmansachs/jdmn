
public class ModelElementRegistry extends com.gs.dmn.runtime.discovery.ModelElementRegistry {
    public ModelElementRegistry() {
        // Register elements from model 'ExternalFunctions'
        register("DerivativeType", "DerivativeType");
        register("FetchForexRate", "FetchForexRate");
        register("IsForexRateRequired", "IsForexRateRequired");
        register("TaxChargeType", "TaxChargeType");
        register("Transaction", "Transaction");
        register("TransactionTaxMetaData", "TransactionTaxMetaData");
    }
}
