
public class ModelElementRegistry extends com.gs.dmn.runtime.discovery.ModelElementRegistry {
    public ModelElementRegistry() {
        // Register elements from model 'decisionInputs'
        // Register elements from model 'decisionTables'
        register("http://www.provider.com/definitions/decisions#dateCompare1", "decisiontables.DateCompare1");
        register("http://www.provider.com/definitions/decisions#dateCompare2", "decisiontables.DateCompare2");
        register("http://www.provider.com/definitions/decisions#priceGt10", "decisiontables.PriceGt10");
        register("http://www.provider.com/definitions/decisions#priceInRange", "decisiontables.PriceInRange");
    }
}
