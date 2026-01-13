
public class ModelElementRegistry extends com.gs.dmn.runtime.discovery.ModelElementRegistry {
    public ModelElementRegistry() {
        // Register elements from model 'TestDecision'
        register("http://www.provider.com/dmn/1.1/diagram/85f2dd29e4774c2f84b883545afdd8cc.xml#test", "Test");
    }
}
