
public class ModelElementRegistry extends com.gs.dmn.runtime.discovery.ModelElementRegistry {
    public ModelElementRegistry() {
        register("http://www.provider.com/definitions/decisions#dateCompare1", "decisiontables.DateCompare1");
        register("http://www.provider.com/definitions/decisions#dateCompare2", "decisiontables.DateCompare2");
        register("http://www.provider.com/definitions/decisions#priceGt10", "decisiontables.PriceGt10");
        register("http://www.provider.com/definitions/decisions#priceInRange", "decisiontables.PriceInRange");
        register("http://www.provider.com/definitions/decision-inputs#dateD", "decisioninputs.DateD");
        register("http://www.provider.com/definitions/decision-inputs#dateE", "decisioninputs.DateE");
        register("http://www.provider.com/definitions/decision-inputs#numB", "decisioninputs.NumB");
        register("http://www.provider.com/definitions/decision-inputs#numC", "decisioninputs.NumC");
        register("http://www.provider.com/definitions/decision-inputs#structA", "decisioninputs.StructA");
    }
}
