
public class ModelElementRegistry extends com.gs.dmn.runtime.discovery.ModelElementRegistry {
    public ModelElementRegistry() {
        // Register elements from model 'ParentLinked'
        register("abc", "Abc");
        register("ChildObject", "ChildObject");
        register("num", "Num");
        register("SomethingElse", "SomethingElse");
    }
}
