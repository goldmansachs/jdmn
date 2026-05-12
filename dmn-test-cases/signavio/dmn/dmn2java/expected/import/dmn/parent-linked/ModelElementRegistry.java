
public class ModelElementRegistry extends com.gs.dmn.runtime.discovery.ModelElementRegistry {
    public ModelElementRegistry() {
        // Register elements from model 'Child'
        register("http://www.provider.com/dmn/1.1/diagram/c3d5f975281b4d2f829ee2c77b320f01.xml#abc", "child.Abc");
        // Register elements from model 'ParentLinked'
        register("http://www.provider.com/dmn/1.1/diagram/80afa9e878bb4885a8f5be36b6f16abc.xml#childObject", "parentlinked.ChildObject");
        register("http://www.provider.com/dmn/1.1/diagram/80afa9e878bb4885a8f5be36b6f16abc.xml#somethingElse", "parentlinked.SomethingElse");
    }
}
