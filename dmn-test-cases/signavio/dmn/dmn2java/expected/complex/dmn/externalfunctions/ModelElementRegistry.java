
public class ModelElementRegistry extends com.gs.dmn.runtime.discovery.ModelElementRegistry {
    public ModelElementRegistry() {
        // Register elements from model 'ExternalFunctions'
        register("FetchForexRate", "FetchForexRate");
        register("IsForexRateRequired", "IsForexRateRequired");
    }
}
