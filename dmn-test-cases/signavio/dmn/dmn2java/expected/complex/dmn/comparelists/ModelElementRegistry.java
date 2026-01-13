
public class ModelElementRegistry extends com.gs.dmn.runtime.discovery.ModelElementRegistry {
    public ModelElementRegistry() {
        // Register elements from model 'CompareLists'
        register("http://www.provider.com/dmn/1.1/diagram/b01e263f2b324caab26b2040a56f8ed1.xml#compareLists", "CompareLists");
        register("http://www.provider.com/dmn/1.1/diagram/b01e263f2b324caab26b2040a56f8ed1.xml#processL1", "ProcessL1");
        register("http://www.provider.com/dmn/1.1/diagram/b01e263f2b324caab26b2040a56f8ed1.xml#processL2", "ProcessL2");
    }
}
