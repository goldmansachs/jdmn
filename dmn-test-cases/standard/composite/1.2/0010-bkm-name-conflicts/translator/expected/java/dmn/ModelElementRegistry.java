
public class ModelElementRegistry extends com.gs.dmn.runtime.discovery.ModelElementRegistry {
    public ModelElementRegistry() {
        register("http://www.provider.com/definitions/model-a#bkm", "model_a.Bkm");
        register("http://www.provider.com/definitions/model-b#bkm", "model_b.Bkm");
        register("http://www.provider.com/definitions/model-c#AA", "model_c.Aa");
        register("http://www.provider.com/definitions/model-c#BA", "model_c.Ba");
        register("http://www.provider.com/definitions/model-c#C", "model_c.C");
    }
}
