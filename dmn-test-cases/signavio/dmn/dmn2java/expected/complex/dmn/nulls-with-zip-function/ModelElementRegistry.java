
public class ModelElementRegistry extends com.gs.dmn.runtime.discovery.ModelElementRegistry {
    public ModelElementRegistry() {
        // Register elements from model 'Nulls with zip function'
        register("http://www.provider.com/dmn/1.1/diagram/7bf105649e8445b39cb4d936497fbc1c.xml#doSomething", "DoSomething");
        register("http://www.provider.com/dmn/1.1/diagram/7bf105649e8445b39cb4d936497fbc1c.xml#mid", "Mid");
        register("http://www.provider.com/dmn/1.1/diagram/7bf105649e8445b39cb4d936497fbc1c.xml#zip", "Zip");
    }
}
