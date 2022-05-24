
public class ModelElementRegistry extends com.gs.dmn.runtime.discovery.ModelElementRegistry {
    public ModelElementRegistry() {
        register("http://www.provider.com/definitions/model-a#A", "model_a.A");
        register("http://www.provider.com/definitions/model-b#A", "model_b.A");
        register("http://www.provider.com/definitions/model-c#A", "model_c.A");
        register("http://www.provider.com/definitions/model-c#C", "model_c.C");
    }
}
