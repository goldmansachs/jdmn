
public class ModelElementRegistry extends com.gs.dmn.runtime.discovery.ModelElementRegistry {
    public ModelElementRegistry() {
        // Register elements from model 'DotProduct'
        register("http://www.provider.com/dmn/1.1/diagram/8a7911e71e72444995f084b28688a37d.xml#calculateDotProduct", "CalculateDotProduct");
        register("http://www.provider.com/dmn/1.1/diagram/8a7911e71e72444995f084b28688a37d.xml#componentwise", "Componentwise");
        register("http://www.provider.com/dmn/1.1/diagram/8a7911e71e72444995f084b28688a37d.xml#dotProduct", "DotProduct");
        register("http://www.provider.com/dmn/1.1/diagram/8a7911e71e72444995f084b28688a37d.xml#product", "Product");
    }
}
