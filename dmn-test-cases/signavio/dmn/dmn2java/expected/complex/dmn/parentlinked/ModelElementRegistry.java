
public class ModelElementRegistry extends com.gs.dmn.runtime.discovery.ModelElementRegistry {
    public ModelElementRegistry() {
        // Register elements from model 'ParentLinked'
        register("http://www.provider.com/dmn/1.1/diagram/80afa9e878bb4885a8f5be36b6f16abc.xml#abc", "Abc");
        register("http://www.provider.com/dmn/1.1/diagram/80afa9e878bb4885a8f5be36b6f16abc.xml#childObject", "ChildObject");
        register("http://www.provider.com/dmn/1.1/diagram/80afa9e878bb4885a8f5be36b6f16abc.xml#somethingElse", "SomethingElse");
    }
}
