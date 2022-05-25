
public class ModelElementRegistry extends com.gs.dmn.runtime.discovery.ModelElementRegistry {
    public ModelElementRegistry() {
        // Register elements from model 'some-every'
        register("everyGtTen1", "EveryGtTen1");
        register("everyGtTen2", "EveryGtTen2");
        register("everyGtTen3", "EveryGtTen3");
        register("gtTen", "GtTen");
        register("priceTable1", "PriceTable1");
        register("priceTable2", "PriceTable2");
        register("someGtTen1", "SomeGtTen1");
        register("someGtTen2", "SomeGtTen2");
    }
}
