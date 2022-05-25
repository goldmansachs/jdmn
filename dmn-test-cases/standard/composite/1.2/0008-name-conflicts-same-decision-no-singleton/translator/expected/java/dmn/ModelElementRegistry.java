
public class ModelElementRegistry extends com.gs.dmn.runtime.discovery.ModelElementRegistry {
    public ModelElementRegistry() {
        // Register elements from model 'Model A'
        register("http://www.provider.com/definitions/model-a#A", "model_a.A");
        // Register elements from model 'Model B'
        register("http://www.provider.com/definitions/model-b#A", "model_b.A");
        // Register elements from model 'Model C'
        register("http://www.provider.com/definitions/model-c#A", "model_c.A");
        register("http://www.provider.com/definitions/model-c#C", "model_c.C");
    }
}
