
public class ModelElementRegistry extends com.gs.dmn.runtime.discovery.ModelElementRegistry {
    public ModelElementRegistry() {
        // Register elements from model 'ExternalFunctions'
        register("http://www.provider.com/dmn/1.1/diagram/1b49e2cbacaf470fb5d093be73afd27e.xml#fetchForexRate", "FetchForexRate");
        register("http://www.provider.com/dmn/1.1/diagram/1b49e2cbacaf470fb5d093be73afd27e.xml#isForexRateRequired", "IsForexRateRequired");
    }
}
