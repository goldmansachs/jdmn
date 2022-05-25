
public class ModelElementRegistry extends com.gs.dmn.runtime.discovery.ModelElementRegistry {
    public ModelElementRegistry() {
        // Register elements from model 'tableTest'
        register("dateCompare1", "DateCompare1");
        register("dateCompare2", "DateCompare2");
        register("dateD", "DateD");
        register("dateE", "DateE");
        register("numB", "NumB");
        register("numC", "NumC");
        register("priceGt10", "PriceGt10");
        register("priceInRange", "PriceInRange");
        register("structA", "StructA");
    }
}
