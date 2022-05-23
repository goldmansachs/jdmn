
package decisiontables;

public class ModelElementRegistry extends com.gs.dmn.runtime.discovery.ModelElementRegistry {
    public ModelElementRegistry() {
        register("dateCompare1", "decisiontables.DateCompare1");
        register("dateCompare2", "decisiontables.DateCompare2");
        register("priceGt10", "decisiontables.PriceGt10");
        register("priceInRange", "decisiontables.PriceInRange");
    }
}