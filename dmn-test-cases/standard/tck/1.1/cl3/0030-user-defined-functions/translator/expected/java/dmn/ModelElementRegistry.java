
public class ModelElementRegistry extends com.gs.dmn.runtime.discovery.ModelElementRegistry {
    public ModelElementRegistry() {
        // Register elements from model '0030-user-defined-functions'
        register("http://www.actico.com/spec/DMN/0.1.0/0030-user-defined-functions#named function invocation", "NamedFunctionInvocation");
        register("http://www.actico.com/spec/DMN/0.1.0/0030-user-defined-functions#simple function invocation", "SimpleFunctionInvocation");
    }
}
