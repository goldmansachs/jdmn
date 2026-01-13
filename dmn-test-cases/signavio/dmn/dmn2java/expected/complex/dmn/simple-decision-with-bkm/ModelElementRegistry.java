
public class ModelElementRegistry extends com.gs.dmn.runtime.discovery.ModelElementRegistry {
    public ModelElementRegistry() {
        // Register elements from model 'simple-decision-with-bkm'
        register("http://www.provider.com/dmn/1.1/diagram/2521256910f54d44b0a90fa88a1aa917.xml#bKM", "BKM");
        register("http://www.provider.com/dmn/1.1/diagram/2521256910f54d44b0a90fa88a1aa917.xml#sUM", "SUM");
        register("http://www.provider.com/dmn/1.1/diagram/2521256910f54d44b0a90fa88a1aa917.xml#sign", "Sign");
    }
}
