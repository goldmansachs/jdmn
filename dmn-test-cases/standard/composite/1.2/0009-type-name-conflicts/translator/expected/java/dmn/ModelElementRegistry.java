
public class ModelElementRegistry extends com.gs.dmn.runtime.discovery.ModelElementRegistry {
    public ModelElementRegistry() {
        // Register elements from model 'Model A'
        // Register elements from model 'Model B'
        // Register elements from model 'Model C'
        register("http://www.provider.com/definitions/model-c#AA", "Aa");
        register("http://www.provider.com/definitions/model-c#BA", "Ba");
        register("http://www.provider.com/definitions/model-c#C", "C");
    }
}
