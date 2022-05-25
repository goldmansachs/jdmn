
public class ModelElementRegistry extends com.gs.dmn.runtime.discovery.ModelElementRegistry {
    public ModelElementRegistry() {
        // Register elements from model 'Model A'
        register("http://www.provider.com/definitions/model-a#bkm", "model_a.Bkm");
        // Register elements from model 'Model B'
        register("http://www.provider.com/definitions/model-b#bkm", "model_b.Bkm");
        // Register elements from model 'Model C'
        register("http://www.provider.com/definitions/model-c#AA", "model_c.Aa");
        register("http://www.provider.com/definitions/model-c#BA", "model_c.Ba");
        register("http://www.provider.com/definitions/model-c#C", "model_c.C");
    }
}
