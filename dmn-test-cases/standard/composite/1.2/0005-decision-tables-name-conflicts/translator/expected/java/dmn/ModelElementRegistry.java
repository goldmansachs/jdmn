
public class ModelElementRegistry extends com.gs.dmn.runtime.discovery.ModelElementRegistry {
    public ModelElementRegistry() {
        // Register elements from model 'decisionInputs1'
        register("http://www.provider.com/definitions/decision-inputs-1#dateD", "decisioninputs1.DateD");
        register("http://www.provider.com/definitions/decision-inputs-1#numB", "decisioninputs1.NumB");
        register("http://www.provider.com/definitions/decision-inputs-1#numC", "decisioninputs1.NumC");
        register("http://www.provider.com/definitions/decision-inputs-1#structA", "decisioninputs1.StructA");
        // Register elements from model 'decisionInputs2'
        register("http://www.provider.com/definitions/decision-inputs-2#dateD", "decisioninputs2.DateD");
        // Register elements from model 'decisionTables'
        register("http://www.provider.com/definitions/decisions#dateCompare1", "decisiontables.DateCompare1");
        register("http://www.provider.com/definitions/decisions#dateCompare2", "decisiontables.DateCompare2");
        register("http://www.provider.com/definitions/decisions#priceGt10", "decisiontables.PriceGt10");
        register("http://www.provider.com/definitions/decisions#priceInRange", "decisiontables.PriceInRange");
    }
}
