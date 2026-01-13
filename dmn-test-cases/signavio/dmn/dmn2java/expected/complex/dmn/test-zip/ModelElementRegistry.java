
public class ModelElementRegistry extends com.gs.dmn.runtime.discovery.ModelElementRegistry {
    public ModelElementRegistry() {
        // Register elements from model 'Test ZIP'
        register("http://www.provider.com/dmn/1.1/diagram/2798610dcc0f4068861fcb0f4af25ac7.xml#body", "Body");
        register("http://www.provider.com/dmn/1.1/diagram/2798610dcc0f4068861fcb0f4af25ac7.xml#loop", "Loop");
        register("http://www.provider.com/dmn/1.1/diagram/2798610dcc0f4068861fcb0f4af25ac7.xml#zip1", "Zip1");
    }
}
